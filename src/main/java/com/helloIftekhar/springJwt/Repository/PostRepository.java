package com.helloIftekhar.springJwt.Repository;

import com.helloIftekhar.springJwt.Bean.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

        @Query("SELECT p FROM Post p WHERE p.isAdoption = true ORDER BY p.createdDate DESC")
        List<Post> findAllAdoptionPost();

        @Query("SELECT p FROM Post p WHERE p.isAdoption = false ORDER BY p.createdDate DESC")
        List<Post> findAllCreatedPost();
}
