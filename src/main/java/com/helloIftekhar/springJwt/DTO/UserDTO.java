package com.helloIftekhar.springJwt.DTO;

import com.helloIftekhar.springJwt.Bean.User;
import com.helloIftekhar.springJwt.Utils.Enum.Gender;
import com.helloIftekhar.springJwt.Utils.Enum.Occupation;
import com.helloIftekhar.springJwt.Utils.Enum.Role;
import com.helloIftekhar.springJwt.Utils.Enum.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
public class UserDTO {

    private Integer id;
    private UserStatus userStatus;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private Role role;
    private String userAvatar;
    private String phoneNumber;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String postal;
    private String address;
    private String city;
    private String state;
    private Occupation occupation;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
        this.phoneNumber = user.getPhoneNumber();
        this.gender = user.getGender();
        this.dateOfBirth = user.getDateOfBirth();
        this.postal = user.getPostal();
        this.address = user.getAddress();
        this.city = user.getCity();
        this.occupation = user.getOccupation();
        this.userStatus = user.getUserStatus();
        this.userAvatar = user.getUserAvatar();
        this.state = user.getState();
    }
}
