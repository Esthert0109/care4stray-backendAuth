package com.helloIftekhar.springJwt.DTO;

import com.helloIftekhar.springJwt.Bean.*;
import com.helloIftekhar.springJwt.Utils.Enum.NotificationType;
import lombok.Data;

@Data
public class NotificationDTO {
    private long notificationId;
    private NotificationType notificationType;
    private UserDTO receiver;
    private UserDTO sender;
    private CreateCommentDTO comment;
    private Like like;
    private AdoptionApplicationDTO adoption;
    private String message;

    // Default no-args constructor
    public NotificationDTO() {}

    // Constructor that accepts a Notification entity
    public NotificationDTO(Notification notification) {
        this.notificationId = notification.getNotificationId();
        this.notificationType = notification.getNotificationType();
        this.receiver = new UserDTO(notification.getReceiver());
        this.sender = new UserDTO(notification.getSender());
        if (notification.getComment() != null) {
            this.comment = new CreateCommentDTO(notification.getComment());  // Mapping Comment to CreateCommentDTO
        }
        if (notification.getLiked() != null) {
            this.like = notification.getLiked();  // Mapping Like entity directly
        }
        if (notification.getAdoption() != null) {
            this.adoption = new AdoptionApplicationDTO(notification.getAdoption());  // Mapping AdoptionApplication
        }
        this.message = notification.getMessage();
    }
}
