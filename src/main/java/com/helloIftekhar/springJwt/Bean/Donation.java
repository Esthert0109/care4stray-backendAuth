package com.helloIftekhar.springJwt.Bean;

import com.helloIftekhar.springJwt.DTO.DonationDTO;
import com.helloIftekhar.springJwt.Utils.Enum.DonationStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "donation")
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "donation_id")
    private Long id;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "isAnonymously", nullable = false)
    private String isAnonymously;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private DonationStatus status;

    public Donation() {
    }

    public Donation(DonationDTO donationDTO, User user) {
        this.id = donationDTO.getId();
        this.createdDate = donationDTO.getCreatedDate();
        this.user = user;
        this.amount = donationDTO.getAmount();
        this.isAnonymously = donationDTO.getIsAnonymously();
        this.status = donationDTO.getStatus();
    }
}
