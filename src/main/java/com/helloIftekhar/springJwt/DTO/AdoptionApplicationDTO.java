package com.helloIftekhar.springJwt.DTO;

import com.helloIftekhar.springJwt.Bean.Adoption;
import com.helloIftekhar.springJwt.Utils.Enum.AdoptionStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdoptionApplicationDTO {
    private Long adoptionId;
    private UserDTO user;
    private StrayDTO stray;
    private AdoptionStatus adoptionStatus;
    private LocalDateTime applicationDate;

    public AdoptionApplicationDTO() {
    }

    public AdoptionApplicationDTO(Adoption adoption) {
        this.adoptionId = adoption.getAdoptionId();
        this.user = new UserDTO(adoption.getUser());
        this.stray = new StrayDTO(adoption.getStray());
        this.adoptionStatus = adoption.getAdoptionStatus();
        this.applicationDate = adoption.getCreatedDate();
    }
}
