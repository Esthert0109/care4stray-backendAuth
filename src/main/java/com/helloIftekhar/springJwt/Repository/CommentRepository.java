package com.helloIftekhar.springJwt.Repository;

import com.helloIftekhar.springJwt.Bean.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {


}
