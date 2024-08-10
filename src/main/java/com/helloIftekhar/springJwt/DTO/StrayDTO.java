package com.helloIftekhar.springJwt.DTO;

import com.helloIftekhar.springJwt.Bean.Stray;
import com.helloIftekhar.springJwt.Bean.User;
import com.helloIftekhar.springJwt.Utils.Enum.StrayStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class StrayDTO {
    private Long strayId;

    private UserDTO user;

    private String name;

    private int age;

    private List<String> behaviour;

    private String mainPicture;

    private List<String> pictureUrl;

    private boolean isVaccinated;

    private boolean isDewormed;

    private StrayStatus status;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    public StrayDTO() {}

    public StrayDTO(Stray stray) {
        this.strayId = stray.getStrayId();
        this.user = new UserDTO(stray.getUser());
        this.name = stray.getName();
        this.age = stray.getAge();
        this.behaviour = stray.getBehaviour();
        this.mainPicture = stray.getMainPicture();
        this.pictureUrl = stray.getPictureUrl();
        this.isVaccinated = stray.isVaccinated();
        this.isDewormed = stray.isDewormed();
        this.status = stray.getStatus();
        this.createdDate = stray.getCreatedDate();
        this.updatedDate = stray.getUpdatedDate();
    }

}
