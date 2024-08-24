package com.helloIftekhar.springJwt.Bean;

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

    @OneToOne
    @JoinColumn(name = "stray_id", nullable = false)
    private Stray stray;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;
}
