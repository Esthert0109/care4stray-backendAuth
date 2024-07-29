package com.helloIftekhar.springJwt.Service.Auth;


import com.helloIftekhar.springJwt.Bean.Token;
import com.helloIftekhar.springJwt.Bean.User;
import com.helloIftekhar.springJwt.DTO.UserDTO;
import com.helloIftekhar.springJwt.Repository.TokenRepository;
import com.helloIftekhar.springJwt.Repository.UserRepository;
import com.helloIftekhar.springJwt.Utils.Enum.UserStatus;
import com.helloIftekhar.springJwt.Utils.Responses.AuthenticationResponse;
import com.helloIftekhar.springJwt.Utils.Responses.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final TokenRepository tokenRepository;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, TokenRepository tokenRepository, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
    }

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

            user = repository.save(user);

            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            saveUserToken(accessToken, refreshToken, user);

            return ResponseEntity.ok(new AuthenticationResponse(accessToken, refreshToken, "User registration was successful"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new AuthenticationResponse(null, null, "Invalid registration")
            );
        }

    }

    public ResponseEntity<AuthenticationResponse> authenticate(User request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            User user = repository.findByUsername(request.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);

            revokeAllTokenByUser(user);
            saveUserToken(accessToken, refreshToken, user);

            return ResponseEntity.ok(new AuthenticationResponse(accessToken, refreshToken, "User login was successful"));
        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body(new AuthenticationResponse(null, null, "Invalid username or password"));
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

    public Response<UserDTO> updateUserInfo(HttpServletRequest request, User user) {
        String token = extractTokenFromHeader(request);

        if (token == null || token == "") {
            return new Response<>("unsuccess", null);
        }

        Token userToken = tokenRepository.findByAccessTokenAndUserId(token, user.getId());
        if (userToken == null) {
            return new Response<>("unsuccess", null);
        }
        repository.save(user);
        User updatedUser = repository.findByUsername(user.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));

        UserDTO updatedDto = new UserDTO(updatedUser);

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

}
