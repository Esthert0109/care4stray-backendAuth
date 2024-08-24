package com.helloIftekhar.springJwt.DTO;

import com.helloIftekhar.springJwt.Bean.Adoption;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdoptionApplicationDTO {
    private Long adoptionId;
    private UserDTO user;
    private StrayDTO stray;
    private LocalDateTime applicationDate;

    public AdoptionApplicationDTO() {
    }

    public AdoptionApplicationDTO(Adoption adoption) {
        this.adoptionId = adoption.getAdoptionId();
        this.user = new UserDTO(adoption.getUser());
        this.stray = new StrayDTO(adoption.getStray());
        this.applicationDate = adoption.getCreatedDate();
    }
}
