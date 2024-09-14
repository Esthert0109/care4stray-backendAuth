package com.helloIftekhar.springJwt.Bean;

import com.helloIftekhar.springJwt.DTO.CreateCommentDTO;
import com.helloIftekhar.springJwt.DTO.CreatedCommentDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    public Comment() {
    }

    public Comment(CreateCommentDTO comment) {
        this.commentId = comment.getCommentId();
        this.user = new User(comment.getUser());
        this.post = new Post(comment.getPost());
        this.comment = comment.getComment();
        this.createdTime = LocalDateTime.now();
    }
}
