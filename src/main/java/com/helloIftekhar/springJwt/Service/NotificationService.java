package com.helloIftekhar.springJwt.Service;

import com.helloIftekhar.springJwt.Bean.Notification;
import com.helloIftekhar.springJwt.DTO.NotificationDTO;
import com.helloIftekhar.springJwt.DTO.UserDTO;
import com.helloIftekhar.springJwt.Repository.AdoptionRepository;
import com.helloIftekhar.springJwt.Repository.NotificationRepository;
import com.helloIftekhar.springJwt.Repository.PostRepository;
import com.helloIftekhar.springJwt.Repository.UserRepository;
import com.helloIftekhar.springJwt.Utils.Responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        try{
            Notification notification = new Notification(notificationDTO);
            notificationRepository.save(notification);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

//    get all noti with receiver_id
//    public Response<List<NotificationDTO>> getNotifications(UserDTO receiver) {
//        try{
//
//        }
//    }
}
