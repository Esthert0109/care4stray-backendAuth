package com.helloIftekhar.springJwt.Controller;

import com.helloIftekhar.springJwt.Bean.News;
import com.helloIftekhar.springJwt.DTO.NewsDTO;
import com.helloIftekhar.springJwt.DTO.UserDTO;
import com.helloIftekhar.springJwt.Service.Auth.AuthenticationService;
import com.helloIftekhar.springJwt.Service.NewsService;
import com.helloIftekhar.springJwt.Utils.Enum.NewsStatus;
import com.helloIftekhar.springJwt.Utils.Enum.UserStatus;
import com.helloIftekhar.springJwt.Utils.Responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin_only")
public class AdminController {

    @Autowired
    AuthenticationService authService;

    @Autowired
    NewsService newsService;

    @GetMapping("/demo")
    public ResponseEntity<String> demo() {
        return ResponseEntity.ok("Hello from secured url");
    }

    @GetMapping("/admin_only")
    public ResponseEntity<String> adminOnly() {
        return ResponseEntity.ok("Hello from admin only url");
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<Response<UserDTO>> updateUserStatus(@RequestBody Map<String, String> request, @PathVariable Integer id) {
        String status = request.get("userStatus");
        if (status == null) {
            return ResponseEntity.badRequest().build();
        }

        UserStatus userStatus = UserStatus.valueOf(status.toUpperCase());
        return ResponseEntity.ok(authService.updateUserStatus(id, userStatus));
    }

    @GetMapping("/users/all")
    public ResponseEntity<Response<List<UserDTO>>> getAllUsers() {
        try {
            List<UserDTO> userList = authService.getAllUserOrderByFirstName();
            return ResponseEntity.ok(new Response<List<UserDTO>>("success", userList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>("unsuccess", null));
        }
    }

    @PostMapping("/news")
    public ResponseEntity<Response<NewsDTO>> createNews(@RequestBody News request) {
        if (request == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(newsService.createNews(request));
    }

    @PatchMapping("/news/update")
    public ResponseEntity<Response<NewsDTO>> updateNews(@RequestBody News request) {
        if (request == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(newsService.updateNews(request));
    }

    @PutMapping("/news/status/{id}")
    public ResponseEntity<Response<NewsDTO>> updateNewsStatus(@RequestBody Map<String, String> request, @PathVariable Long id) {
        String status = request.get("newsStatus");
        if (status == null) {
            return ResponseEntity.badRequest().build();
        }
        NewsStatus newsStatus = NewsStatus.valueOf(status.toUpperCase());
        return ResponseEntity.ok(authService.updateNewsStatus(id, newsStatus));
    }

    @GetMapping("/news/all")
    public ResponseEntity<Response<List<NewsDTO>>> getAllNews() {
        try {
            List<NewsDTO> newsList = newsService.getAllNewsOrderByUpdatedDate();
            return ResponseEntity.ok(new Response<>("success", newsList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>("unsuccess", null));
        }
    }
}
