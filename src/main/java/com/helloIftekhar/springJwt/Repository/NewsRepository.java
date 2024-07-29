package com.helloIftekhar.springJwt.Repository;

import com.helloIftekhar.springJwt.Bean.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
