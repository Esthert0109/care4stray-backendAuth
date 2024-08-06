package com.helloIftekhar.springJwt.DTO;

import com.helloIftekhar.springJwt.Bean.News;
import com.helloIftekhar.springJwt.Utils.Enum.NewsStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collector;


@Data
public class NewsDTO {
    private Long id;
    private String title;
    private String author;
    private String content;
    private String pic;
    private NewsStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String duration;

    public NewsDTO() {

    }

    public NewsDTO(News news) {
        this.id = news.getId();
        this.title = news.getTitle();
        this.author = news.getAuthor();
        this.content = news.getContent();
        this.pic = news.getPic();
        this.status = news.getStatus();
        this.createdDate = news.getCreatedDate();
        this.updatedDate = news.getUpdatedDate();
    }

    public NewsDTO(News news, String duration) {
        this.id = news.getId();
        this.title = news.getTitle();
        this.author = news.getAuthor();
        this.content = news.getContent();
        this.pic = news.getPic();
        this.status = news.getStatus();
        this.createdDate = news.getCreatedDate();
        this.updatedDate = news.getUpdatedDate();
        this.duration = duration;
    }
}
