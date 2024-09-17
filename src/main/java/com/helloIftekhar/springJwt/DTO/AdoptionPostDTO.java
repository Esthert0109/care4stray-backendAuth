package com.helloIftekhar.springJwt.DTO;

import com.helloIftekhar.springJwt.Bean.Post;
import com.helloIftekhar.springJwt.Utils.Enum.LikeStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdoptionPostDTO {
    private Long id;
    private UserDTO user;
    private StrayDTO stray;
    private LikeStatus isLike;
    private int likeCount;
    private int commentCount;
    private LocalDateTime createdDate;
    private String duration;

    public AdoptionPostDTO() {
    }

    public AdoptionPostDTO(Post post, LikeStatus isLike) {
        this.id = post.getPostId();
        this.user = new UserDTO(post.getUser());
        this.stray = new StrayDTO(post.getStray());
        this.likeCount = post.getLikeCount();
        this.isLike = isLike;
        this.commentCount = post.getCommentCount();
        this.createdDate = post.getCreatedDate();
    }
}
