package com.helloIftekhar.springJwt.Bean;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "stray_picture")
@Data
public class StrayPicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_id")
    private Long pictureId;

    @Column(name = "picture_url")
    private String pictureUrl;
}
