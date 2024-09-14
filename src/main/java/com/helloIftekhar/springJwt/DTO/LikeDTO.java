package com.helloIftekhar.springJwt.DTO;

import com.helloIftekhar.springJwt.Bean.Like;
import lombok.Data;

@Data
public class LikeDTO {
    private Long id;
    private UserDTO user;
    private PostDTO post;

    public LikeDTO() {
    }

}
