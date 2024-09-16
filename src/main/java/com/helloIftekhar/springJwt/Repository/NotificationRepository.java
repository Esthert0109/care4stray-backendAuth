package com.helloIftekhar.springJwt.Repository;

import com.helloIftekhar.springJwt.Bean.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByReceiverId(Long receiverId);

//    @Query("")
//    List<Notification> findByNotificationTypeAdmin();
}
