package com.helloIftekhar.springJwt.Repository;

import com.helloIftekhar.springJwt.Bean.News;
import com.helloIftekhar.springJwt.Utils.Enum.NewsStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findAllByStatusOrderByUpdatedDateDesc(NewsStatus status);

    List<News> findAllByOrderByUpdatedDateDesc();
}
