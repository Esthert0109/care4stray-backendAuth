package com.helloIftekhar.springJwt.Service;

import com.helloIftekhar.springJwt.Bean.Notification;
import com.helloIftekhar.springJwt.Bean.User;
import com.helloIftekhar.springJwt.DTO.NotificationDTO;
import com.helloIftekhar.springJwt.Repository.AdoptionRepository;
import com.helloIftekhar.springJwt.Repository.NotificationRepository;
import com.helloIftekhar.springJwt.Repository.PostRepository;
import com.helloIftekhar.springJwt.Repository.UserRepository;
import com.helloIftekhar.springJwt.Utils.Responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private AdoptionRepository adoptionRepository;

    //    create notification
    public void createNotification(NotificationDTO notificationDTO) {
        try {
            Notification notification = new Notification(notificationDTO);
            notificationRepository.save(notification);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Response<List<NotificationDTO>> getUserNotifications(Long userId) {
        try {
            List<Notification> notifications = notificationRepository.findByReceiverIdAndSenderNotReceiver(userId);
            List<NotificationDTO> notificationDTOs = notifications.stream()
                    .map(notification -> new NotificationDTO(notification)) // Assuming you have a constructor that accepts Notification
                    .collect(Collectors.toList());
            return new Response<>("success", notificationDTOs);
        } catch (Exception e) {
            return new Response<>("unsuccess", null);
        }
    }

    /**
     * Fetch notifications for admins (You could have a separate field in the User entity to identify admins).
     */
//    public Response<List<NotificationDTO>> getAdminNotifications() {
//        try {
//            List<Notification> notifications = notificationRepository.findByNotificationTypeAdmin(); // Modify query logic for admin
//            List<NotificationDTO> notificationDTOs = notifications.stream()
//                    .map(this::createNotificationWithSenderName)
//                    .collect(Collectors.toList());
//            return new Response<>("success", notificationDTOs);
//        } catch (Exception e) {
//            return new Response<>("unsuccess", null);
//        }
//    }
    private NotificationDTO createNotificationWithSenderName(Notification notification) {
        NotificationDTO dto = new NotificationDTO(notification);

        // Fetch the sender's name using the sender's ID
        Optional<User> sender = userRepository.findById(notification.getSender().getId());
        if (sender.isPresent()) {
            // Prepend the sender's name to the message
            String senderName = sender.get().getFirstName() + " " + sender.get().getLastName();  // Assuming User entity has a 'name' field
            dto.setMessage(senderName + " " + dto.getMessage());
        }

        return dto;
    }


//    get all noti with receiver_id
//    public Response<List<NotificationDTO>> getNotifications(UserDTO receiver) {
//        try{
//
//        }
//    }
}
