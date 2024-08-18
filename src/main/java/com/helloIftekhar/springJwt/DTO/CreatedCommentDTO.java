package com.helloIftekhar.springJwt.DTO;

import com.helloIftekhar.springJwt.Bean.Comment;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreatedCommentDTO {
    private UserDTO user;
    private String comment;
    private LocalDateTime createdDate;

    public CreatedCommentDTO() {
    }

    public CreatedCommentDTO(Comment comment) {
        this.user = new UserDTO(comment.getUser());
        this.comment = comment.getComment();
        this.createdDate = comment.getCreatedTime();
    }
}
