package com.helloIftekhar.springJwt.Service;

import com.helloIftekhar.springJwt.Bean.*;
import com.helloIftekhar.springJwt.DTO.*;
import com.helloIftekhar.springJwt.Repository.CommentRepository;
import com.helloIftekhar.springJwt.Repository.LikeRepository;
import com.helloIftekhar.springJwt.Repository.PostRepository;
import com.helloIftekhar.springJwt.Repository.UserRepository;
import com.helloIftekhar.springJwt.Utils.Responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
    private NewsService newsService;

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
                adoptionPost.setId(post.getPostId());
                adoptionPost.setUser(new UserDTO(post.getUser()));
                adoptionPost.setStray(new StrayDTO(post.getStray()));
                adoptionPost.setIsLike(isLiked);
                adoptionPost.setLikeCount(post.getLikeCount());
                adoptionPost.setCommentCount(post.getCommentCount());
                adoptionPost.setCreatedDate(post.getCreatedDate());
                adoptionPost.setDuration(newsService.formatDuration(post.getCreatedDate()));

                adoptionPostDTOList.add(adoptionPost);
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
}
