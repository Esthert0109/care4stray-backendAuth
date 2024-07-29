package com.helloIftekhar.springJwt.Bean;


import com.helloIftekhar.springJwt.DTO.NewsDTO;
import com.helloIftekhar.springJwt.Utils.Enum.NewsStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "news_pic")
    private String pic;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private NewsStatus status;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;

    public News() {

    }

    public News(NewsDTO newsDTO) {
        this.id = newsDTO.getId();
        this.title = newsDTO.getTitle();
        this.author = newsDTO.getAuthor();
        this.content = newsDTO.getContent();
        this.pic = newsDTO.getPic();
        this.status = newsDTO.getStatus();
        this.createdDate = newsDTO.getCreatedDate();
        this.updatedDate = newsDTO.getUpdatedDate();
    }

    @PrePersist
    protected void onCreate() {
        this.createdDate = LocalDateTime.now();
        this.updatedDate = createdDate;
    }
}
