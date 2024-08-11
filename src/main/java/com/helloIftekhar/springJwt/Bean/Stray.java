package com.helloIftekhar.springJwt.Bean;

import com.helloIftekhar.springJwt.DTO.StrayDTO;
import com.helloIftekhar.springJwt.Utils.Enum.Gender;
import com.helloIftekhar.springJwt.Utils.Enum.StrayStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "stray")
@Data
public class Stray {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stray_id")
    private Long strayId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age")
    private int age;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @ElementCollection
    @CollectionTable(name = "stray_behaviour", joinColumns = @JoinColumn(name = "stray_id"))
    @Column(name = "behaviour")
    private List<String> behaviour;

    @Column(name = "main_picture", nullable = false)
    private String mainPicture;

    @ElementCollection
    @CollectionTable(name = "stray_picture", joinColumns = @JoinColumn(name = "stray_id"))
    @Column(name = "picture")
    private List<String> pictureUrl;

    @Column(name = "is_vaccinated", nullable = false)
    private boolean isVaccinated;

    @Column(name = "is_dewormed", nullable = false)
    private boolean isDewormed;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StrayStatus status;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date",nullable = false)
    private LocalDateTime updatedDate;

    public Stray() {
    }

    public Stray(StrayDTO strayDTO, User user) {
        this.strayId = strayDTO.getStrayId();
        this.user = user;
        this.name = strayDTO.getName();
        this.age = strayDTO.getAge();
        this.gender = strayDTO.getGender();
        this.behaviour = strayDTO.getBehaviour();
        this.mainPicture = strayDTO.getMainPicture();
        this.pictureUrl = strayDTO.getPictureUrl();
        this.isVaccinated = strayDTO.isVaccinated();
        this.isDewormed = strayDTO.isDewormed();
        this.status = strayDTO.getStatus();
        this.createdDate = strayDTO.getCreatedDate();
        this.updatedDate = strayDTO.getUpdatedDate();
    }
}
