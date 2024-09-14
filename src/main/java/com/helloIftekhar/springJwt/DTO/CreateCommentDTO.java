package com.helloIftekhar.springJwt.DTO;

import lombok.Data;

@Data
public class CreateCommentDTO {
    private Long commentId;
    private UserDTO user;
    private PostDTO post;
    private String comment;


    public CreateCommentDTO() {
    }
}
