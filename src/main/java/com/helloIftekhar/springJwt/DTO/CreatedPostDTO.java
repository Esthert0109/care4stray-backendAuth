package com.helloIftekhar.springJwt.DTO;

import com.helloIftekhar.springJwt.Bean.Post;
import com.helloIftekhar.springJwt.Utils.Enum.LikeStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreatedPostDTO {
    private Long postId;
    private UserDTO author;
    private String content;
    private List<String> picture;
    private LikeStatus isLiked;
    private int likeCount;
    private int commentCount;
    private LocalDateTime createdDate;
    private String duration;
//    private LikeStatus likeStatus;

    public CreatedPostDTO() {
    }

    public CreatedPostDTO(Post post, LikeStatus isLiked) {
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
