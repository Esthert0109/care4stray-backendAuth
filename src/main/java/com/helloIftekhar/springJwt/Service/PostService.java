package com.helloIftekhar.springJwt.Service;

import com.helloIftekhar.springJwt.Bean.*;
import com.helloIftekhar.springJwt.DTO.*;
import com.helloIftekhar.springJwt.Repository.CommentRepository;
import com.helloIftekhar.springJwt.Repository.LikeRepository;
import com.helloIftekhar.springJwt.Repository.PostRepository;
import com.helloIftekhar.springJwt.Repository.UserRepository;
import com.helloIftekhar.springJwt.Utils.Enum.NotificationType;
import com.helloIftekhar.springJwt.Utils.Enum.StrayStatus;
import com.helloIftekhar.springJwt.Repository.*;
import com.helloIftekhar.springJwt.Service.Auth.AuthenticationService;
import com.helloIftekhar.springJwt.Utils.NotificationMessagesConstants;
import com.helloIftekhar.springJwt.Utils.Responses.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private NewsService newsService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private NotificationService notificationService;

    public void createAdoptionPost(Stray stray) {
        Post adoptionPost = new Post();
        adoptionPost.setUser(stray.getUser());
        adoptionPost.setIsAdoption(true);
        adoptionPost.setStray(stray);
        adoptionPost.setLikeCount(0);
        adoptionPost.setCommentCount(0);
        adoptionPost.setCreatedDate(LocalDateTime.now());
        postRepository.save(adoptionPost);
    }

    public Response<CreatedPostDTO> createNewPost(CreatePostDTO createPost) {
        try {
            Post newPost = new Post();
            newPost.setUser(new User(createPost.getAuthor()));
            newPost.setIsAdoption(false);
            newPost.setContent(createPost.getContent());
            newPost.setPicture(createPost.getPicture());
            newPost.setLikeCount(0);
            newPost.setCommentCount(0);
            newPost.setCreatedDate(LocalDateTime.now());

            postRepository.save(newPost);
            CreatedPostDTO createdPostDTO = new CreatedPostDTO(newPost, false);
            return new Response<>("success", createdPostDTO);
        } catch (Exception e) {
            return new Response<>("unsuccess", null);
        }
    }

    public Response<PostDTO> getPostDetail(Long postId, Integer userId) {
        try {
            Post post = postRepository.findPostByPostId(postId);
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found."));
            Boolean isLiked = likeRepository.existsByUserAndPost(user, post);
            String duration = newsService.formatDuration(post.getCreatedDate());

            PostDTO createdPostDTO = new PostDTO(post, isLiked, duration);

            return new Response<>("success", createdPostDTO);
        } catch (Exception e) {
            return new Response<>("unsuccess", null);
        }
    }

    public Response<List<AdoptionPostDTO>> getAdoptedPostList(Integer userId) {
        try {
            List<Post> postList = postRepository.findAllAdoptionPost();
            List<AdoptionPostDTO> adoptionPostDTOList = new ArrayList<>();
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found."));

            for (Post post : postList) {
                AdoptionPostDTO adoptionPost = new AdoptionPostDTO();
                Boolean isLiked = likeRepository.existsByUserAndPost(user, post);

                Stray stray = post.getStray();
                if (stray.getStatus() == StrayStatus.AVAILABLE || stray.getStatus() == StrayStatus.RETURNED) {
                    adoptionPost.setId(post.getPostId());
                    adoptionPost.setUser(new UserDTO(post.getUser()));
                    adoptionPost.setStray(new StrayDTO(stray));

                    //                adoptionPost.setStray(new StrayDTO(post.getStray()));
                    adoptionPost.setIsLike(isLiked);
                    adoptionPost.setLikeCount(post.getLikeCount());
                    adoptionPost.setCommentCount(post.getCommentCount());
                    adoptionPost.setCreatedDate(post.getCreatedDate());
                    adoptionPost.setDuration(newsService.formatDuration(post.getCreatedDate()));

                    adoptionPostDTOList.add(adoptionPost);
                }
            }

            return new Response<>("success", adoptionPostDTOList);
        } catch (Exception e) {
            return new Response<>("unsuccess", null);
        }
    }

    public Response<List<CreatedPostDTO>> getCreatedPostList(Integer userId) {
        try {
            List<Post> postList = postRepository.findAllCreatedPost();
            List<CreatedPostDTO> createdPostDTOList = new ArrayList<>();
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found."));

            for (Post post : postList) {
                CreatedPostDTO createdPostDTO = new CreatedPostDTO();
                Boolean isLiked = likeRepository.existsByUserAndPost(user, post);
                createdPostDTO.setPostId(post.getPostId());
                createdPostDTO.setAuthor(new UserDTO(post.getUser()));
                createdPostDTO.setContent(post.getContent());
                createdPostDTO.setPicture(post.getPicture());
                createdPostDTO.setIsLiked(isLiked);
                createdPostDTO.setLikeCount(post.getLikeCount());
                createdPostDTO.setCommentCount(post.getCommentCount());
                createdPostDTO.setCreatedDate(post.getCreatedDate());
                createdPostDTO.setDuration(newsService.formatDuration(post.getCreatedDate()));

                createdPostDTOList.add(createdPostDTO);
            }

            return new Response<>("success", createdPostDTOList);
        } catch (Exception e) {
            return new Response<>("unsuccess", null);
        }
    }

    public Response<List<CreatedPostDTO>> getCreatedPostListByUserLocation(HttpServletRequest header) {
        try {
            String token = authenticationService.extractTokenFromHeader(header);
            if (token == null || token == "") {
                return new Response<>("unsuccess", null);
            }
            Token tokenSelected = tokenRepository.findByAccessToken(token).orElseThrow(() -> new RuntimeException("Token not found."));
            User user = tokenSelected.getUser();

            if (user.getState() != null) {
                String userState = user.getState();

                List<Post> postList = postRepository.findAllNearPost(userState);
                List<CreatedPostDTO> createdPostDTOList = new ArrayList<>();
                for (Post post : postList) {
                    CreatedPostDTO createdPostDTO = new CreatedPostDTO();
                    Boolean isLiked = likeRepository.existsByUserAndPost(user, post);
                    createdPostDTO.setPostId(post.getPostId());
                    createdPostDTO.setAuthor(new UserDTO(post.getUser()));
                    createdPostDTO.setContent(post.getContent());
                    createdPostDTO.setPicture(post.getPicture());
                    createdPostDTO.setIsLiked(isLiked);
                    createdPostDTO.setLikeCount(post.getLikeCount());
                    createdPostDTO.setCommentCount(post.getCommentCount());
                    createdPostDTO.setCreatedDate(post.getCreatedDate());
                    createdPostDTO.setDuration(newsService.formatDuration(post.getCreatedDate()));

                    createdPostDTOList.add(createdPostDTO);
                }
                return new Response<>("success", createdPostDTOList);
            } else {
                return new Response<>("unsuccess", null);
            }

        } catch (Exception e) {
            return new Response<>("unsuccess", null);
        }
    }

    public Response<List<PostDTO>> getUserCreatedPostList(Integer userId) {
        try {
            List<Post> postList = postRepository.findAllOwnCreatedPostByUserId(Long.valueOf(userId));
            List<PostDTO> postDTOList = new ArrayList<>();
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found."));

            for (Post post : postList) {
                // Create a PostDTO for each post
                PostDTO postDTO = new PostDTO(post, likeRepository.existsByUserAndPost(user, post), newsService.formatDuration(post.getCreatedDate()));

                // If the post has an associated Stray, add StrayDTO to PostDTO
                if (post.getStray() != null) {
                    Stray stray = post.getStray();
                    StrayDTO strayDTO = new StrayDTO();
                    strayDTO.setStrayId(stray.getStrayId());
                    strayDTO.setCreatedDate(stray.getCreatedDate());
                    strayDTO.setIsDewormed(stray.getIsDewormed());
                    strayDTO.setIsVaccinated(stray.getIsVaccinated());
                    strayDTO.setMainPicture(stray.getMainPicture());
                    strayDTO.setPictureUrl(stray.getPictureUrl());
                    strayDTO.setStatus(stray.getStatus());
                    strayDTO.setName(stray.getName());
                    strayDTO.setAge(stray.getAge());
                    strayDTO.setBehaviour(stray.getBehaviour());
;
                    postDTO.setStrayPost(strayDTO);
                }

                postDTOList.add(postDTO);
            }

            return new Response<>("success", postDTOList);
        } catch (Exception e) {
            return new Response<>("unsuccess", null);
        }
    }



    public Response<LikedDTO> likeOrUnlikedPost(LikeDTO likeDTO) {
        try {
            User user = userRepository.findById(likeDTO.getUser().getId()).orElseThrow(() -> new RuntimeException("User not found."));
            Post post = postRepository.findById(likeDTO.getPost().getPostId()).orElseThrow(() -> new RuntimeException("Post not found."));
            Boolean isLiked = likeRepository.existsByUserAndPost(user, post);
            if (isLiked) {
                Like createdLike = likeRepository.findByUserAndPost(user, post);
                likeRepository.deleteById(createdLike.getId());

                post.setLikeCount(post.getLikeCount() - 1);
                postRepository.save(post);

                LikedDTO likedDTO = new LikedDTO();
                likedDTO.setIsLiked(false);
                return new Response<>("success", likedDTO);
            } else {
                Like likePost = new Like();
                likePost.setUser(user);
                likePost.setPost(post);
                likePost.setCreatedDate(LocalDateTime.now());
                likePost.setUpdatedDate(likePost.getCreatedDate());

                likeRepository.save(likePost);

                post.setLikeCount(post.getLikeCount() + 1);
                postRepository.save(post);

                LikedDTO likedDTO = new LikedDTO(likePost, true);

//                User Notification
                NotificationDTO createNotification = new NotificationDTO();
                createNotification.setNotificationType(NotificationType.POST);
                createNotification.setReceiver(new UserDTO(likePost.getPost().getUser()));
                createNotification.setSender(new UserDTO(likePost.getUser()));
                createNotification.setLike(likePost);
                createNotification.setMessage(NotificationMessagesConstants.USER_POST_LIKED_NOTIFICATION_MESSAGE);

                // Manually add dynamic message without changing NotificationMessagesConstants
//                String message = user.getUsername() + NotificationMessagesConstants.USER_POST_LIKED_NOTIFICATION_MESSAGE;
//                createNotification.setMessage(message);

                notificationService.createNotification(createNotification);

                return new Response<>("success", likedDTO);
            }
        } catch (Exception e) {
            return new Response<>("unsuccess", null);
        }
    }

    public Response<CreatedCommentDTO> createComment(CreateCommentDTO commentDTO) {
        try {
            User user = userRepository.findById(commentDTO.getUser().getId()).orElseThrow(() -> new RuntimeException("User not found."));
            Post post = postRepository.findById(commentDTO.getPost().getPostId()).orElseThrow(() -> new RuntimeException("Post not found."));
            Comment newComment = new Comment();
            newComment.setUser(user);
            newComment.setComment(commentDTO.getComment());
            newComment.setPost(post);
            newComment.setCreatedTime(LocalDateTime.now());

            commentRepository.save(newComment);

            post.setCommentCount(post.getCommentCount() + 1);
            postRepository.save(post);

            CreatedCommentDTO createdCommentDTO = new CreatedCommentDTO(newComment);


//            User Notification
            NotificationDTO createNotification = new NotificationDTO();
            createNotification.setNotificationType(NotificationType.POST);
            createNotification.setReceiver(new UserDTO(post.getUser()));
            createNotification.setSender(new UserDTO(user));
            createNotification.setComment(new CreateCommentDTO(newComment));
            createNotification.setMessage(NotificationMessagesConstants.USER_POST_COMMENTED_NOTIFICATION_MESSAGE);

            // Manually add dynamic message without changing NotificationMessagesConstants
//            String message = user.getUsername() + NotificationMessagesConstants.USER_POST_COMMENTED_NOTIFICATION_MESSAGE;
//            createNotification.setMessage(message);

            notificationService.createNotification(createNotification);

            return new Response<>("success", createdCommentDTO);
        } catch (Exception e) {
            return new Response<>("unsuccess", null);
        }
    }

    public Response<List<CreatedCommentDTO>> getCommentListByPostId(Long postId) {
        try {
            List<Comment> commentList = commentRepository.findAllByPostOrderByCreatedTimeDesc(postId);
            List<CreatedCommentDTO> createdCommentList = new ArrayList<>();

            for (Comment comment : commentList) {
                CreatedCommentDTO createdCommentDTO = new CreatedCommentDTO(comment);
                createdCommentDTO.setDuration(newsService.formatDuration(comment.getCreatedTime()));
                createdCommentList.add(createdCommentDTO);
            }
            return new Response<>("success", createdCommentList);
        } catch (Exception e) {
            return new Response<>("unsuccess", null);
        }
    }

    public Response<List<PostDTO>> searchPostsByContent(Integer userId, String keyword) {
        try {
            List<Post> createdPostList = postRepository.searchCreatedPostsByContent(keyword);
            List<Post> adoptionPostList = postRepository.searchAdoptionPostsByStrayContent(keyword);

            List<PostDTO> postDTOList = new ArrayList<>();
            User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found."));

            // Process both created and adoption posts into PostDTO
            for (Post post : createdPostList) {
                PostDTO postDTO = new PostDTO(post, likeRepository.existsByUserAndPost(user, post), newsService.formatDuration(post.getCreatedDate()));
                postDTOList.add(postDTO);
            }

            for (Post post : adoptionPostList) {
                PostDTO postDTO = new PostDTO(post, likeRepository.existsByUserAndPost(user, post), newsService.formatDuration(post.getCreatedDate()));
                postDTOList.add(postDTO);
            }

            return new Response<>("success", postDTOList);
        } catch (Exception e) {
            return new Response<>("unsuccess", null);
        }
    }

    public Response<PostStatisticsDTO> getPostStatistics() {
        try {
            // Get the current time and time one week ago
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime oneWeekAgo = now.minus(1, ChronoUnit.WEEKS);

            // Count the total posts
            long totalPosts = postRepository.count();

            // Count the posts created within the last week
            long postsThisWeek = postRepository.countByCreatedDateBetween(oneWeekAgo, now);

            // Count the posts created in the week before last
            LocalDateTime twoWeeksAgo = now.minus(2, ChronoUnit.WEEKS);
            long postsLastWeek = postRepository.countByCreatedDateBetween(twoWeeksAgo, oneWeekAgo);

            // Calculate the percentage increase
            double percentageIncrease = 0;
            if (postsLastWeek > 0) {
                percentageIncrease = ((double) (postsThisWeek - postsLastWeek) / postsLastWeek) * 100;
            } else if (postsThisWeek > 0) {
                percentageIncrease = 100;
            }

            // Create the DTO and return the response
            PostStatisticsDTO statisticsDTO = new PostStatisticsDTO(totalPosts, percentageIncrease);
            return new Response<>("success", statisticsDTO);
        } catch (Exception e) {
            return new Response<>("unsuccess", null);
        }
    }


}
