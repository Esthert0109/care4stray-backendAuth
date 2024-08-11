package com.helloIftekhar.springJwt.Controller;

import com.helloIftekhar.springJwt.Bean.News;
import com.helloIftekhar.springJwt.Bean.User;
import com.helloIftekhar.springJwt.DTO.DonationDTO;
import com.helloIftekhar.springJwt.DTO.NewsDTO;
import com.helloIftekhar.springJwt.DTO.StrayDTO;
import com.helloIftekhar.springJwt.DTO.UserDTO;
import com.helloIftekhar.springJwt.Service.Auth.AuthenticationService;
import com.helloIftekhar.springJwt.Service.DonationService;
import com.helloIftekhar.springJwt.Service.NewsService;
import com.helloIftekhar.springJwt.Service.StrayService;
import com.helloIftekhar.springJwt.Utils.Enum.NewsStatus;
import com.helloIftekhar.springJwt.Utils.Enum.StrayStatus;
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

    @Autowired
    DonationService donationService;
    @Autowired
    private StrayService strayService;

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

    @GetMapping("/user/all")
    public ResponseEntity<Response<List<UserDTO>>> getAllUsers() {
        try {
            List<UserDTO> userList = authService.getAllUserOrderByFirstName();
            return ResponseEntity.ok(new Response<List<UserDTO>>("success", userList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>("unsuccess", null));
        }
    }
    @PostMapping("/userid")
    public ResponseEntity<Response<UserDTO>> getUserInfoByUserID (@RequestBody User request) {
        if(request.getId() == null ) {
            Response badResponse = new Response<>("unsuccess", null);
            return ResponseEntity.badRequest().body(badResponse);
        }

        Integer UserID = request.getId();
        return ResponseEntity.ok(authService.getUserInfoDetailsByID(UserID));
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

    @GetMapping("/donation/all")
    public ResponseEntity<Response<List<DonationDTO>>> getAllDonation() {
        try {
            return ResponseEntity.ok(donationService.getAllDonationList());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>("unsuccess", null));
        }
    }

    @PostMapping("/adoption/create_stray")
    public ResponseEntity<Response<StrayDTO>> createStray(@RequestBody StrayDTO request) {
        if(request == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(strayService.createStray(request));
    }

    @GetMapping("/adoption/stray/all")
    public ResponseEntity<Response<List<StrayDTO>>> getAllStray() {
        try{
            return ResponseEntity.ok(strayService.getAllStrays());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>("unsuccess", null));
        }
    }

    @PutMapping("/adoption/stray/updateStatus/{id}")
    public ResponseEntity<Response<StrayDTO>> updateStrayStatus(@RequestBody Map<String, String> request, @PathVariable Long id) {
        String status = request.get("strayStatus");
        if (status == null || id == null) {
            return ResponseEntity.badRequest().build();
        }
        StrayStatus strayStatus = StrayStatus.valueOf(status.toUpperCase());
        return ResponseEntity.ok(strayService.updateStrayStatus(id, strayStatus));
    }
}
