package com.helloIftekhar.springJwt.DTO;

import com.helloIftekhar.springJwt.Bean.Donation;
import com.helloIftekhar.springJwt.Bean.User;
import com.helloIftekhar.springJwt.Utils.Enum.DonationStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DonationDTO {
    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private UserDTO user;
    private Double amount;
    private Boolean isAnonymously;
    private DonationStatus status;

    public DonationDTO() {
    }

    public DonationDTO(Donation donation) {
        this.id = donation.getId();
        this.createdDate = donation.getCreatedDate();
        this.updatedDate = donation.getUpdatedDate();
        if(donation.getUser() != null) {
            this.user = new UserDTO(donation.getUser());
        }else{
            this.user = null;
        }
        this.amount = donation.getAmount();
        this.isAnonymously = donation.getIsAnonymously();
        this.status = donation.getStatus();
    }
}
