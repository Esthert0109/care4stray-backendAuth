package com.helloIftekhar.springJwt.Repository;

import com.helloIftekhar.springJwt.Bean.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
//    List<Notification> findByReceiverId(Long receiverId);

    // Find notifications by receiverId where senderId does not match receiverId
//    @Query("SELECT n FROM Notification n WHERE n.receiver.id = :receiverId AND n.sender.id != :receiverId")
//    List<Notification> findByReceiverIdAndSenderNotReceiver(Long receiverId);

    @Query("SELECT n FROM Notification n WHERE n.receiver.id = :receiverId AND n.sender.id != :receiverId ORDER BY n.createDate DESC")
    List<Notification> findByReceiverIdAndSenderNotReceiverOrderByCreatedDateDesc(Long receiverId);

//    @Query("")
//    List<Notification> findByNotificationTypeAdmin();
}
