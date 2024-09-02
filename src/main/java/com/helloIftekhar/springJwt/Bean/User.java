package com.helloIftekhar.springJwt.Bean;

import com.helloIftekhar.springJwt.DTO.UserDTO;
import com.helloIftekhar.springJwt.Utils.Enum.Gender;
import com.helloIftekhar.springJwt.Utils.Enum.Occupation;
import com.helloIftekhar.springJwt.Utils.Enum.Role;
import com.helloIftekhar.springJwt.Utils.Enum.UserStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.SQLInsert;
import org.hibernate.annotations.SQLInserts;
import org.hibernate.annotations.processing.SQL;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

//@SQLInsert(sql = "INSERT INTO user (dateOfBirth, id, address, city, email, first_name, last_name, password, phone_number, postal, user_avatar, gender, occupation, role, status)\n" +
//        "VALUES\n" +
//        "    ('1999-09-09', 1, 'anyPlace', 'anyCity', 'admin@gmail.com', 'Au', 'admin', ''),\n" +
//        "    ('ACTIVE', 'aU', 'User', 'user@gmail.com', 'user123', 'USER', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTFc0kLj4UisX9AQSUzDXukFV56Y73E5sRK8g&s', '012-7485961', 'FEMALE', '1985-05-15', '67890', '456 Oak Avenue', 'Othertown', 'STUDENT');\n")

@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status")
    private UserStatus userStatus;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "user_avatar", nullable = false)
    private String userAvatar;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "dateOfBirth")
    private LocalDate dateOfBirth;

    @Column(name = "postal")
    private String postal;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "occupation")
    private Occupation occupation;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    public User() {
    }


    public User(UserDTO user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.userAvatar = user.getUserAvatar();
        this.role = user.getRole();
        this.phoneNumber = user.getPhoneNumber();
        this.gender = user.getGender();
        this.dateOfBirth = user.getDateOfBirth();
        this.postal = user.getPostal();
        this.address = user.getAddress();
        this.city = user.getCity();
        this.occupation = user.getOccupation();
        this.userStatus = user.getUserStatus();
        this.state = user.getState();
        this.createdDate = user.getCreatedDate();
    }


    @OneToMany(mappedBy = "user")
    private List<Token> tokens;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }
    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Occupation getOccupation() {
        return occupation;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
