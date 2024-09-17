package com.helloIftekhar.springJwt.DTO;

import com.helloIftekhar.springJwt.Bean.Like;
import com.helloIftekhar.springJwt.Utils.Enum.LikeStatus;
import lombok.Data;

@Data
public class LikeDTO {
    private Long id;
    private UserDTO user;
    private PostDTO post;

    public LikeDTO() {
    }

    public LikeDTO(Like like) {
        this.id = like.getId();
        this.user = new UserDTO(like.getUser());
        this.post = new PostDTO(like.getPost(), LikeStatus.LIKE, "");
    }
}
