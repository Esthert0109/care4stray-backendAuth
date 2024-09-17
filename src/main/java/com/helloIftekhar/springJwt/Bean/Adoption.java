package com.helloIftekhar.springJwt.Bean;

import com.helloIftekhar.springJwt.DTO.AdoptionApplicationDTO;
import com.helloIftekhar.springJwt.Utils.Enum.AdoptionStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "adoption")
public class Adoption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adoption_id")
    private Long adoptionId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "stray_id", nullable = false)
    private Stray stray;

    @Column(name = "adoption_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AdoptionStatus adoptionStatus;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    public Adoption() {
    }

    public Adoption(AdoptionApplicationDTO adoption) {
        this.adoptionId =adoption.getAdoptionId();
        this.user = new User(adoption.getUser());
        this.stray = new Stray(adoption.getStray(), null);
        this.adoptionStatus = adoption.getAdoptionStatus();
        this.createdDate = adoption.getApplicationDate();
    }
}
