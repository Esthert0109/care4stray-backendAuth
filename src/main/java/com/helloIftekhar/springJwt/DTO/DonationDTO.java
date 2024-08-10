package com.helloIftekhar.springJwt.DTO;

import com.helloIftekhar.springJwt.Bean.Donation;
import com.helloIftekhar.springJwt.Utils.Enum.DonationStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DonationDTO {
    private Long id;
    private LocalDateTime createdDate;
    private String userName;
    private Double amount;
    private String isAnonymously;
    private DonationStatus status;

    public DonationDTO() {
    }

    public DonationDTO(Donation donation) {
        this.id = donation.getId();
        this.createdDate = donation.getCreatedDate();
        this.userName = donation.getUser().getFirstName() + donation.getUser().getLastName();
        this.amount = donation.getAmount();
        this.isAnonymously = donation.getIsAnonymously();
        this.status = donation.getStatus();
    }
}
