package com.helloIftekhar.springJwt.Bean;

import com.helloIftekhar.springJwt.DTO.NotificationDTO;
import com.helloIftekhar.springJwt.Utils.Enum.NotificationType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private long notificationId;

    @Column(name = "notification_type")
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "liked_id")
    private Like liked;

    @ManyToOne
    @JoinColumn(name = "adoption_id")
    private Adoption adoption;

    @Column(name = "message")
    private String message;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    public Notification() {
    }

    public Notification(NotificationDTO notification) {
        this.notificationId = notification.getNotificationId();
        this.notificationType = notification.getNotificationType();
        this.receiver = new User(notification.getReceiver());
        this.sender = new User(notification.getSender());
        if (notification.getComment() != null) {
            this.comment = new Comment(notification.getComment());
        }
        if (notification.getLike() != null) {
            this.liked = new Like(notification.getLike());
        }
        this.adoption = new Adoption(notification.getAdoption());
        this.message = notification.getMessage();
    }
}
