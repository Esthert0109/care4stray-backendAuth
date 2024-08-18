package com.helloIftekhar.springJwt.DTO;

import com.helloIftekhar.springJwt.Bean.User;
import lombok.Data;

import java.util.List;

@Data
public class CreatePostDTO {
    private UserDTO author;
    private String content;
    private List<String> picture;
}
