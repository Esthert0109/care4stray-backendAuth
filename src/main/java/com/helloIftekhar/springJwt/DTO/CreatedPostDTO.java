package com.helloIftekhar.springJwt.DTO;

import com.helloIftekhar.springJwt.Bean.Post;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreatedPostDTO {
    private Long postId;
    private UserDTO author;
    private String content;
    private List<String> picture;
    private Boolean isLiked;
    private int likeCount;
    private int commentCount;
    private LocalDateTime createdDate;

    public CreatedPostDTO() {
    }

    public CreatedPostDTO(Post post, Boolean isLiked) {
        this.postId = post.getPostId();
        this.author = new UserDTO(post.getUser());
        this.content = post.getContent();
        this.picture = post.getPicture();
        this.isLiked = isLiked;
        this.likeCount = post.getLikeCount();
        this.commentCount = post.getCommentCount();
        this.createdDate = post.getCreatedDate();
    }
}
