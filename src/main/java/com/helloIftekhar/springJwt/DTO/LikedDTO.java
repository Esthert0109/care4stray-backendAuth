package com.helloIftekhar.springJwt.DTO;

import com.helloIftekhar.springJwt.Bean.Like;
import com.helloIftekhar.springJwt.Utils.Enum.LikeStatus;
import lombok.Data;

@Data
public class LikedDTO {
    private Long id;
    private Long postId;
    private Integer likedUserId;
    private LikeStatus isLiked;

    public LikedDTO() {
    }

    public LikedDTO(Like like) {
        this.id = like.getId();
        this.postId = like.getPost().getPostId();
        this.likedUserId = like.getUser().getId();
        this.isLiked = like.getLikeStatus();
    }
}
