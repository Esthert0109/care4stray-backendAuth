package com.helloIftekhar.springJwt.Controller;

import com.helloIftekhar.springJwt.DTO.UserDTO;
import com.helloIftekhar.springJwt.Service.Auth.JwtService;
import com.helloIftekhar.springJwt.Utils.Enum.UserStatus;
import com.helloIftekhar.springJwt.Utils.Responses.AuthenticationResponse;
import com.helloIftekhar.springJwt.Bean.User;
import com.helloIftekhar.springJwt.Service.Auth.AuthenticationService;
import com.helloIftekhar.springJwt.Utils.Responses.LoginResponse;
import com.helloIftekhar.springJwt.Utils.Responses.Response;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/care4stray/auth")
public class AuthenticationController {

    private final AuthenticationService authService;


    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody User request
            ) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody User request
    ) {
        return authService.authenticate(request);
    }

    @PostMapping("/refresh_token")
    public ResponseEntity refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        return authService.refreshToken(request, response);
    }

    @PostMapping("/validate-token")
    public ResponseEntity<Response<Boolean>> validateToken(@RequestBody Map<String, String> request) {
        String userId = request.get("userId");
        String token = request.get("token");

        // Check if userId and token are provided
        if (userId == null || token == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(authService.isValidTokenForUser(Long.parseLong(userId), token));
    }
}
