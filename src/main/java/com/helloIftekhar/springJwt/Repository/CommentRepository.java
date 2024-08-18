package com.helloIftekhar.springJwt.Repository;

import com.helloIftekhar.springJwt.Bean.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c WHERE c.post.postId = :postId ORDER BY c.createdTime DESC")
    List<Comment> findAllByPostOrderByCreatedTimeDesc(@Param("postId") Long postId);
}
