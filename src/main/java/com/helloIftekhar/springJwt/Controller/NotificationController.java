package com.helloIftekhar.springJwt.Controller;

import com.helloIftekhar.springJwt.DTO.NotificationDTO;
import com.helloIftekhar.springJwt.Service.NotificationService;
import com.helloIftekhar.springJwt.Utils.Responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/care4stray/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    //    Admin side notification
//    @GetMapping("/admin")
//    public ResponseEntity<Response<List<NotificationDTO>>> getAdminNotifications() {
//        return ResponseEntity.ok(notificationService.getAdminNotifications());
//    }

    //    User side notification
    @GetMapping("/user/{userId}")
    public ResponseEntity<Response<List<NotificationDTO>>> getUserNotifications(@PathVariable Long userId) {
        if (userId == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(notificationService.getUserNotifications(userId));
    }
}
