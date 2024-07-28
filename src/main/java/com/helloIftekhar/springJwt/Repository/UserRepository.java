package com.helloIftekhar.springJwt.Repository;

import com.helloIftekhar.springJwt.Bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
}
