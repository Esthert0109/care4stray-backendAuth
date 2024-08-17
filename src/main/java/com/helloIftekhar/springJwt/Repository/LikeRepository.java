package com.helloIftekhar.springJwt.Repository;

import com.helloIftekhar.springJwt.Bean.Like;
import com.helloIftekhar.springJwt.Bean.Post;
import com.helloIftekhar.springJwt.Bean.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LikeRepository extends JpaRepository<Like, Long> {

//    @Query("SELECT EXISTS (SELECT 1 FROM Like )")
    Boolean existsByUserAndPost(User user, Post post);
}
