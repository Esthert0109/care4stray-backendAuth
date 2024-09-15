package com.helloIftekhar.springJwt.Bean;

import com.helloIftekhar.springJwt.DTO.PostDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "is_adoption", nullable = false)
    private Boolean isAdoption;

    @OneToOne
    @JoinColumn(name = "stray_id")
    private Stray stray;

    @Column(name = "content")
    private String content;

    @ElementCollection
    @CollectionTable(name = "post_picture", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "picture")
    private List<String> picture;

    @Column(name = "like_count")
    private int likeCount;

    @Column(name = "comment_count")
    private int commentCount;

    @Column(name = "created_date")
    private LocalDateTime createdDate;


    public Post() {
    }

    public Post(PostDTO post) {
        this.postId = post.getPostId();
        this.user = new User(post.getAuthor());
        this.isAdoption = post.getIsAdoption();
        if (post.getStrayPost() != null) {
            this.stray = new Stray(post.getStrayPost(), new User(post.getAuthor()));
        } else {
            this.stray = null; // Handle this appropriately
        }
        this.content = post.getContent();
        this.picture = post.getPicture();
        this.likeCount = post.getLikeCount();
        this.commentCount = post.getCommentCount();
        this.createdDate = post.getCreatedDate();
    }
}
