package com.helloIftekhar.springJwt.Service.Auth;


import com.helloIftekhar.springJwt.Bean.News;
import com.helloIftekhar.springJwt.Bean.Token;
import com.helloIftekhar.springJwt.Bean.User;
import com.helloIftekhar.springJwt.DTO.NewsDTO;
import com.helloIftekhar.springJwt.DTO.PostStatisticsDTO;
import com.helloIftekhar.springJwt.DTO.UserDTO;
import com.helloIftekhar.springJwt.DTO.UserListDTO;
import com.helloIftekhar.springJwt.Repository.AdoptionRepository;
import com.helloIftekhar.springJwt.Repository.NewsRepository;
import com.helloIftekhar.springJwt.Repository.TokenRepository;
import com.helloIftekhar.springJwt.Repository.UserRepository;
import com.helloIftekhar.springJwt.Utils.Enum.NewsStatus;
import com.helloIftekhar.springJwt.Utils.Enum.Role;
import com.helloIftekhar.springJwt.Utils.Enum.UserStatus;
import com.helloIftekhar.springJwt.Utils.Responses.AuthenticationResponse;
import com.helloIftekhar.springJwt.Utils.Responses.LoginResponse;
import com.helloIftekhar.springJwt.Utils.Responses.Response;
import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;

    private final AdoptionRepository adoptionRepository;

    private final NewsRepository newsRepo;

    private final UserRepository userRepository;

    public AuthenticationService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, TokenRepository tokenRepository, AuthenticationManager authenticationManager, AdoptionRepository adoptionRepository, NewsRepository newsRepo, UserRepository userRepository) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
        this.adoptionRepository = adoptionRepository;
        this.newsRepo = newsRepo;
        this.userRepository = userRepository;
    }


    public Response<Boolean> isValidTokenForUser(Long userId, String token) {
        try {
            // Fetch the user by userId
            UserDetails user = userRepository.findById(Math.toIntExact(userId))
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Validate the token with the user
            boolean isValid = jwtService.isValid(token, user);

            // Return success response
            return new Response<>("success", isValid);
        } catch (RuntimeException e) {
            // Handle user not found or any other exceptions and return failure response
            return new Response<>("unsuccess", null);
        }
    }

    /******************** User Service**************************************/
    public ResponseEntity<AuthenticationResponse> register(User request) {

        // check if user already exist. if exist than authenticate the user
        if (repository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body(new AuthenticationResponse(null, null, "User already exist"));
        }

        try {
            User user = new User();
            user.setFirstName(request.getFirstName());
            user.setLastName(request.getLastName());
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setGender(request.getGender());
            user.setUserStatus(UserStatus.ACTIVE);
            user.setRole(request.getRole());
            user.setUserAvatar(request.getUserAvatar());
            user.setCreatedDate(LocalDateTime.now());

            user = repository.save(user);

            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            saveUserToken(accessToken, refreshToken, user);

            return ResponseEntity.ok(new AuthenticationResponse(accessToken, refreshToken, "User registration was successful"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new AuthenticationResponse(null, null, "Invalid registration"));
        }

    }

//    public ResponseEntity<LoginResponse> authenticate(User request) {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
//            User user = repository.findByUsername(request.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
//            String accessToken = jwtService.generateAccessToken(user);
//            String refreshToken = jwtService.generateRefreshToken(user);
//
//            revokeAllTokenByUser(user);
//            saveUserToken(accessToken, refreshToken, user);
//
//            UserDTO userLogin = new UserDTO(user);
//
//            return ResponseEntity.ok(new LoginResponse("Login successfully", accessToken, userLogin));
//        } catch (BadCredentialsException e) {
//            return ResponseEntity.badRequest().body(new LoginResponse("Invalid email or password", null, null));
//        }
//    }

    public ResponseEntity<LoginResponse> authenticate(User request) {
        try {
            // Authenticate the user based on username and password
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            // Fetch the user from the repository
            User user = repository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Check if the user's status is INACTIVE
            if (user.getUserStatus().equals(UserStatus.INACTIVE)) {
                return ResponseEntity.badRequest().body(new LoginResponse("Account is inactive. Please contact support.", null, null));
            }

            // Generate access and refresh tokens
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            // Revoke any previous tokens for the user and save the new ones
            revokeAllTokenByUser(user);
            saveUserToken(accessToken, refreshToken, user);

            // Create UserDTO for login response
            UserDTO userLogin = new UserDTO(user);

            // Return a successful response with the tokens and user details
            return ResponseEntity.ok(new LoginResponse("Login successfully", accessToken, userLogin));

        } catch (BadCredentialsException e) {
            // Return an error response for invalid credentials
            return ResponseEntity.badRequest().body(new LoginResponse("Invalid email or password", null, null));
        }
    }


    public Response<UserDTO> getUserInfoDetails(String username) {
        User user = null;
        try {
            user = repository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        } catch (RuntimeException e) {
            return new Response<>("unsuccess", null);
        }
        UserDTO userDetails = new UserDTO(user);
        return new Response<>("success", userDetails);
    }

    public Response<UserDTO> getUserInfoDetailsByID(Integer UserID) {
        User user = null;
        try {
            user = repository.findById(UserID).orElseThrow(() -> new RuntimeException("User not found"));
        } catch (RuntimeException e) {
            return new Response<>("unsuccess", null);
        }
        UserDTO userDetails = new UserDTO(user);
        return new Response<>("success", userDetails);
    }

    public Response<UserDTO> updateUserInfo(HttpServletRequest request, User user) {
        String token = extractTokenFromHeader(request);

        if (token == null || token == "") {
            return new Response<>("unsuccess", null);
        }

        Token userToken = tokenRepository.findByAccessTokenAndUserId(token, user.getId());
        if (userToken == null) {
            return new Response<>("unsuccess", null);
        }
        User updatedUser = repository.findByUsername(user.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(updatedUser.getPassword());
        user.setUserStatus(updatedUser.getUserStatus());
        repository.save(user);


        UserDTO updatedDto = new UserDTO(user);

        return new Response<>("success", updatedDto);

    }

    @Transactional
    public Response<UserDTO> updateUserStatus(Integer id, UserStatus status) {
        try {
            User selectedUser = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
            selectedUser.setUserStatus(status);
            repository.save(selectedUser);

            UserDTO updatedDto = new UserDTO(selectedUser);

            return new Response<UserDTO>("success", updatedDto);
        } catch (RuntimeException e) {
            return new Response<>("unsuccess", null);
        }
    }

    public Response<Boolean> checkUserDetails(String username) {
        try {
            User user = repository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
            if (user.getId() == null || user.getFirstName() == null || user.getLastName() == null || user.getUsername() == null || user.getPassword() == null || user.getRole() == null || user.getUserAvatar() == null || user.getPhoneNumber() == null || user.getGender() == null || user.getDateOfBirth() == null || user.getPostal() == null || user.getAddress() == null || user.getCity() == null || user.getOccupation() == null) {
                return new Response<>("success", false);
            }
            return new Response<>("success", true);
        } catch (RuntimeException e) {
            return new Response<>("unsuccess", null);
        }
    }

    public List<UserListDTO> getAllUserOrderByFirstName() {
        List<User> userList = repository.findAllByOrderByFirstNameAsc();

        // Apply the filters using the Stream API
        List<UserListDTO> userListDTOS = userList.stream()
                .filter(user -> !user.getUserStatus().equals(UserStatus.DEACTIVATED)) // Filter out deactivated users
                .filter(user -> user.getRole().equals(Role.USER)) // Only include users with the role 'USER'
                .map(user -> {
                    boolean isAdopted = adoptionRepository.existsByUserAndAdoptionStatus_ApplicationSuccess(user);
                    int numAdopted = adoptionRepository.countAdoptionsByUserAndAdoptionStatus_ApplicationSuccess(user);
                    return new UserListDTO(user, isAdopted, numAdopted);
                })
                .collect(Collectors.toList());

        return userListDTOS;

//        return userList.stream()
//                .filter(user -> !user.getUserStatus().equals(UserStatus.DEACTIVATED))
//                .filter(user -> user.getRole().equals(Role.USER))
//                .map(user -> new UserDTO(user))
//                .collect(Collectors.toList());
    }

    private void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllAccessTokensByUser(user.getId());
        if (validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t -> {
            t.setLoggedOut(true);
        });

        tokenRepository.saveAll(validTokens);
    }

    private void saveUserToken(String accessToken, String refreshToken, User user) {
        Token token = new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }

    public ResponseEntity refreshToken(HttpServletRequest request, HttpServletResponse response) {
        // extract the token from authorization header
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        String token = authHeader.substring(7);

        // extract username from token
        String username = jwtService.extractUsername(token);

        // check if the user exist in database
        User user = repository.findByUsername(username).orElseThrow(() -> new RuntimeException("No user found"));

        // check if the token is valid
        if (jwtService.isValidRefreshToken(token, user)) {
            // generate access token
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            revokeAllTokenByUser(user);
            saveUserToken(accessToken, refreshToken, user);

            return new ResponseEntity(new AuthenticationResponse(accessToken, refreshToken, "New token generated"), HttpStatus.OK);
        }

        return new ResponseEntity(HttpStatus.UNAUTHORIZED);

    }

    public String extractTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        throw new RuntimeException("JWT Token is missing");
    }

    /****************************** News Service ********************************/
    @Transactional
    public Response<NewsDTO> updateNewsStatus(Long id, NewsStatus status) {
        try {
            News selectedNews = newsRepo.findById(id).orElseThrow(() -> new RuntimeException("News not found"));
            selectedNews.setStatus(status);
            newsRepo.save(selectedNews);

            NewsDTO updatedNews = new NewsDTO(selectedNews);

            return new Response<NewsDTO>("success", updatedNews);
        } catch (RuntimeException e) {
            return new Response<>("unsuccess", null);
        }
    }

    /****************************** Statistics ********************************/
    public Response<PostStatisticsDTO> getUserStatistics() {
        try {
            // Get the current time and time one week ago
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime oneWeekAgo = now.minusWeeks(1);

            // Count the total posts
            long totalPosts = repository.count();

            // Count the posts created within the last week
            long postsThisWeek = repository.countByCreatedDateBetween(oneWeekAgo, now);

            // Count the posts created in the week before last
            LocalDateTime twoWeeksAgo = now.minusWeeks(2);
            long postsLastWeek = repository.countByCreatedDateBetween(twoWeeksAgo, oneWeekAgo);

            // Calculate the percentage increase
            double percentageIncrease = 0;
            if (postsLastWeek > 0) {
                percentageIncrease = ((double) (postsThisWeek - postsLastWeek) / postsLastWeek) * 100;
            } else if (postsThisWeek > 0) {
                percentageIncrease = 100;
            }

            // Create the DTO and return the response
            PostStatisticsDTO statisticsDTO = new PostStatisticsDTO(totalPosts, percentageIncrease);
            return new Response<>("success", statisticsDTO);
        } catch (Exception e) {
            return new Response<>("unsuccess", null);
        }
    }
}
