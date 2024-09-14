package com.helloIftekhar.springJwt.Repository;

import com.helloIftekhar.springJwt.Bean.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
