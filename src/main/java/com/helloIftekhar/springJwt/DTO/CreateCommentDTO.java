package com.helloIftekhar.springJwt.DTO;

import com.helloIftekhar.springJwt.Bean.Comment;
import com.helloIftekhar.springJwt.Utils.Enum.LikeStatus;
import lombok.Data;

@Data
public class CreateCommentDTO {
    private Long commentId;
    private UserDTO user;
    private PostDTO post;
    private String comment;


    public CreateCommentDTO() {
    }

    public CreateCommentDTO(Comment comment) {
        this.commentId = comment.getCommentId();
        this.user = new UserDTO(comment.getUser());
        this.post = new PostDTO(comment.getPost(), LikeStatus.LIKE, "0");
        this.comment = comment.getComment();
    }
}
