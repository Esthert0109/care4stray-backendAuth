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
}
