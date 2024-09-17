package com.helloIftekhar.springJwt.Bean;

import com.helloIftekhar.springJwt.DTO.LikeDTO;
import com.helloIftekhar.springJwt.Utils.Enum.LikeStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "liked")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @Column(name = "like_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private LikeStatus likeStatus;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;

    public Like() {
    }

    public Like(LikeDTO dto) {
        this.id = dto.getId();
        this.user = new User(dto.getUser());
        this.post = new Post(dto.getPost());
    }
}
