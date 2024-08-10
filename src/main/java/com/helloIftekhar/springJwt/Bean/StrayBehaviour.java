package com.helloIftekhar.springJwt.Bean;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "stray_behaviour")
@Data
public class StrayBehaviour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "behaviour_id")
    private Long behaviourId;

    @Column(name = "behaviour")
    private String behaviour;
}
