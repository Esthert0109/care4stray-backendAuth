package com.helloIftekhar.springJwt.Repository;

import com.helloIftekhar.springJwt.Bean.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

        @Query("SELECT p FROM Post p WHERE p.isAdoption = true ORDER BY p.createdDate DESC")
        List<Post> findAllAdoptionPost();

        @Query("SELECT p FROM Post p WHERE p.isAdoption = false ORDER BY p.createdDate DESC")
        List<Post> findAllCreatedPost();

        @Query("SELECT p FROM Post p WHERE p.isAdoption = false AND p.user.id = :userId ORDER BY p.createdDate DESC")
        List<Post> findAllCreatedPostByUserId(Long userId);

        Post findPostByPostId(Long postId);

        @Query("SELECT p FROM Post p WHERE p.content LIKE %:keyword%")
        List<Post> searchCreatedPostsByContent(@Param("keyword") String keyword);

        @Query("SELECT p FROM Post p JOIN p.stray s LEFT JOIN s.behaviour b WHERE s.name LIKE %:keyword% OR b LIKE %:keyword%")
        List<Post> searchAdoptionPostsByStrayContent(@Param("keyword") String keyword);


//        @Query("SELECT p FROM Post p WHERE p.stray.name LIKE %:keyword%")
//        List<Post> searchAdoptionPostsByStrayContent(@Param("keyword") String keyword);

}
