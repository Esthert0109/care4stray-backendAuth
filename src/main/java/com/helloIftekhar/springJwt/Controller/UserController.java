package com.helloIftekhar.springJwt.Controller;

import com.helloIftekhar.springJwt.Bean.User;
import com.helloIftekhar.springJwt.DTO.UserDTO;
import com.helloIftekhar.springJwt.Service.Auth.AuthenticationService;
import com.helloIftekhar.springJwt.Utils.Responses.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/care4stray/user")
public class UserController {

    @Autowired
    private AuthenticationService authService;

    @PostMapping("/user")
    public ResponseEntity<Response<UserDTO>> getUserInfo (@RequestBody User request) {
        if(request.getUsername() == null ) {
            Response badResponse = new Response<>("unsuccess", null);
            return ResponseEntity.badRequest().body(badResponse);
        }

        String username = request.getUsername();
        return ResponseEntity.ok(authService.getUserInfoDetails(username));
    }

    @PatchMapping("/update")
    public ResponseEntity<Response<UserDTO>> updateUserInfo(@RequestBody User request, HttpServletRequest header) {
        if(request.getUsername() == null ) {
            Response badResponse = new Response<>("unsuccess", null);
            return ResponseEntity.badRequest().body(badResponse);
        }

        return ResponseEntity.ok(authService.updateUserInfo(header,request));
    }

}
