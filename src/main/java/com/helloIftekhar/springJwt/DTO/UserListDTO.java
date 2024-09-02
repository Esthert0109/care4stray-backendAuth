package com.helloIftekhar.springJwt.DTO;

import com.helloIftekhar.springJwt.Bean.User;
import lombok.Data;

@Data
public class UserListDTO {
    private UserDTO user;
    private boolean isAdoptedStray;
    private int totalAdoptedStray;

    public UserListDTO() {
    }

    public UserListDTO(User user, boolean isAdoptedStray, int totalAdoptedStray) {
        this.user = new UserDTO(user);
        this.isAdoptedStray = isAdoptedStray;
        this.totalAdoptedStray = totalAdoptedStray;
    }
}
