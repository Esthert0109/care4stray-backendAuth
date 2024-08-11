package com.helloIftekhar.springJwt.Bean;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "post_picture")
public class PostPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_picture_id")
    private Long pictureId;

    @Column(name = "picture_url")
    private String pictureUrl;
}
