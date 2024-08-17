package com.helloIftekhar.springJwt.Controller;

import com.helloIftekhar.springJwt.DTO.AdoptionPostDTO;
import com.helloIftekhar.springJwt.Service.PostService;
import com.helloIftekhar.springJwt.Utils.Responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/care4stray/post")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/adoption-list")
    public ResponseEntity<Response<List<AdoptionPostDTO>>> adoptionList(@RequestBody Map<String, Integer> request) {
        Integer userId = request.get("userId");
        if(userId == null || userId == 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(postService.getAdoptedPostList(userId));
    }
}
