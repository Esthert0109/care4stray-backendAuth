package com.helloIftekhar.springJwt.DTO;

import com.helloIftekhar.springJwt.Bean.*;
import com.helloIftekhar.springJwt.Utils.Enum.NotificationType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDTO {
    private long notificationId;
    private NotificationType notificationType;
    private UserDTO receiver;
    private UserDTO sender;
    private CreateCommentDTO comment;
    private LikeDTO like;
    private AdoptionApplicationDTO adoption;
    private String message;
    private String duration;
    private LocalDateTime createdDate;


    // Default no-args constructor
    public NotificationDTO() {}

    // Constructor that accepts a Notification entity
    public NotificationDTO(Notification notification, String duration) {
        this.notificationId = notification.getNotificationId();
        this.notificationType = notification.getNotificationType();
        this.receiver = new UserDTO(notification.getReceiver());
        this.sender = new UserDTO(notification.getSender());
        if (notification.getComment() != null) {
            this.comment = new CreateCommentDTO(notification.getComment());  // Mapping Comment to CreateCommentDTO
        }
        if (notification.getLiked() != null) {
            this.like = new LikeDTO(notification.getLiked());  // Mapping Like entity directly
        }
        if (notification.getAdoption() != null) {
            this.adoption = new AdoptionApplicationDTO(notification.getAdoption());  // Mapping AdoptionApplication
        }
        this.message = notification.getMessage();
        this.duration = duration;
        this.createdDate = notification.getCreateDate();
    }
}
