package com.helloIftekhar.springJwt.DTO;

import com.helloIftekhar.springJwt.Bean.Post;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PostDTO {
    private Long postId;
    private UserDTO author;
    private Boolean isAdoption;
    private StrayDTO strayPost;
    private String content;
    private List<String> picture;
    private int likeCount;
    private int commentCount;
    private Boolean isliked;
    private LocalDateTime createdDate;
    private String duration;

    public PostDTO() {
    }

    public PostDTO(Post post, Boolean isliked, String duration) {
        this.postId = post.getPostId();
        this.author = new UserDTO(post.getUser());
        this.isAdoption = post.getIsAdoption();
        if (post.getStray() == null) {
            this.strayPost = null;

        } else {
            this.strayPost = new StrayDTO(post.getStray());
        }
        this.content = post.getContent();
        this.picture = post.getPicture();
        this.isliked = isliked;
        this.likeCount = post.getLikeCount();
        this.commentCount = post.getCommentCount();
        this.createdDate = post.getCreatedDate();
        this.duration = duration;
    }
}
