package com.helloIftekhar.springJwt.DTO;

import com.helloIftekhar.springJwt.Bean.News;
import com.helloIftekhar.springJwt.Utils.Enum.NewsStatus;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class NewsDTO {
    private int id;
    private String title;
    private String author;
    private String content;
    private String pic;
    private NewsStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

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
}
