package com.helloIftekhar.springJwt.Repository;

import com.helloIftekhar.springJwt.Bean.Like;
import com.helloIftekhar.springJwt.Bean.Post;
import com.helloIftekhar.springJwt.Bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Boolean existsByUserAndPost(User user, Post post);

    Like findByUserAndPost(User user, Post post);
}
