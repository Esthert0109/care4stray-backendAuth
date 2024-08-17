package com.helloIftekhar.springJwt.DTO;

import com.helloIftekhar.springJwt.Bean.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdoptionPostDTO {
    private Long id;
    private UserDTO user;
    private StrayDTO stray;
    private Boolean isLike;
    private int likeCount;
    private int commentCount;
    private LocalDateTime createdDate;

    public AdoptionPostDTO() {
    }

    public AdoptionPostDTO(Post post, Boolean isLike) {
        this.id = post.getPostId();
        this.user = new UserDTO(post.getUser());
        this.stray = new StrayDTO(post.getStray());
        this.likeCount = post.getLikeCount();
        this.isLike = isLike;
        this.commentCount = post.getCommentCount();
        this.createdDate = post.getCreatedDate();
    }
}
