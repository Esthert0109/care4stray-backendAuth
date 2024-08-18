package com.helloIftekhar.springJwt.DTO;

import com.helloIftekhar.springJwt.Bean.Like;
import lombok.Data;

@Data
public class LikedDTO {
    private Long id;
    private Long postId;
    private Integer likedUserId;
    private Boolean isLiked;

    public LikedDTO() {
    }

    public LikedDTO(Like like, Boolean isLiked) {
        this.id = like.getId();
        this.postId = like.getPost().getPostId();
        this.likedUserId = like.getUser().getId();
        this.isLiked = isLiked;
    }
}
