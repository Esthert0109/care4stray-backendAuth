package com.helloIftekhar.springJwt.Controller;

import com.helloIftekhar.springJwt.DTO.*;
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
        if (userId == null || userId == 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(postService.getAdoptedPostList(userId));
    }

    @PostMapping("/create")
    public ResponseEntity<Response<CreatedPostDTO>> createNewPost(@RequestBody CreatePostDTO request) {
        if (request == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(postService.createNewPost(request));
    }

    @PostMapping("/{postId}")
    public ResponseEntity<Response<PostDTO>> getPostDetail(@PathVariable Long postId, @RequestBody Map<String, Integer> request) {
        if(postId == null || postId == 0 || request == null) {
            return ResponseEntity.badRequest().build();
        }
        Integer userId = request.get("userId");
        return ResponseEntity.ok(postService.getPostDetail(postId,userId));
    }

    @PostMapping("/create-list")
    public ResponseEntity<Response<List<CreatedPostDTO>>> createdPostList(@RequestBody Map<String, Integer> request) {
        Integer userId = request.get("userId");
        if (userId == null || userId == 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(postService.getCreatedPostList(userId));
    }

    @PostMapping("/like")
    public ResponseEntity<Response<LikedDTO>> likeOrUnlike(@RequestBody LikeDTO request) {
        if (request == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(postService.likeOrUnlikedPost(request));
    }

    @PostMapping("/comment")
    public ResponseEntity<Response<CreatedCommentDTO>> createComment(@RequestBody CreateCommentDTO request) {
        if (request == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(postService.createComment(request));
    }

    @GetMapping("/comment-list/{id}")
    public ResponseEntity<Response<List<CreatedCommentDTO>>> getCommentList(@PathVariable Long id) {
        if(id == null || id == 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(postService.getCommentListByPostId(id));
    }

    @PostMapping("/create-list-user")
    public ResponseEntity<Response<List<CreatedPostDTO>>> userCreatedPostList(@RequestBody Map<String, Integer> request) {
        Integer userId = request.get("userId");
        if (userId == null || userId == 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(postService.getUserCreatedPostList(userId));
    }
}
