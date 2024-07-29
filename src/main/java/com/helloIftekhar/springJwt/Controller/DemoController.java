package com.helloIftekhar.springJwt.Controller;

import com.helloIftekhar.springJwt.Bean.User;
import com.helloIftekhar.springJwt.DTO.UserDTO;
import com.helloIftekhar.springJwt.Service.Auth.AuthenticationService;
import com.helloIftekhar.springJwt.Utils.Enum.UserStatus;
import com.helloIftekhar.springJwt.Utils.Responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class DemoController {

    @Autowired
    AuthenticationService authService;

    @GetMapping("/demo")
    public ResponseEntity<String> demo() {
        return ResponseEntity.ok("Hello from secured url");
    }

    @GetMapping("/admin_only")
    public ResponseEntity<String> adminOnly() {
        return ResponseEntity.ok("Hello from admin only url");
    }

    @PutMapping("/admin_only/status/{id}")
    public ResponseEntity<Response<UserDTO>> updateUserStatus(@RequestBody Map<String, String> request, @PathVariable Integer id) {
        String status = request.get("userStatus");
        if(status == null) {
            return ResponseEntity.badRequest().build();
        }

        UserStatus userStatus = UserStatus.valueOf(status.toUpperCase());
        return ResponseEntity.ok(authService.updateUserStatus(id, userStatus));
    }
}
