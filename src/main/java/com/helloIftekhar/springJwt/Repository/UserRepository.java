package com.helloIftekhar.springJwt.Repository;

import com.helloIftekhar.springJwt.Bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
//    Optional<User> findByEmail(String email);

    List<User> findAllByOrderByFirstNameAsc();

    long countByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);

//    User findById(Integer id);
//    User findById(Integer id);

}
