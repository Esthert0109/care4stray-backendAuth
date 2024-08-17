package com.helloIftekhar.springJwt.Service;

import com.helloIftekhar.springJwt.Bean.Post;
import com.helloIftekhar.springJwt.Bean.Stray;
import com.helloIftekhar.springJwt.Bean.User;
import com.helloIftekhar.springJwt.DTO.AdoptionPostDTO;
import com.helloIftekhar.springJwt.DTO.StrayDTO;
import com.helloIftekhar.springJwt.DTO.UserDTO;
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

    public Response<List<AdoptionPostDTO>> getAdoptedPostList(Integer userId) {
        try {
            List<Post> postList = postRepository.findAllAdoptionPost();
            List<AdoptionPostDTO> adoptionPostDTOList = new ArrayList<>();
            User user = userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found."));

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

                adoptionPostDTOList.add(adoptionPost);
            }

            return new Response<>("success", adoptionPostDTOList);
        } catch (Exception e) {
            return new Response<>("unsuccess", null);
        }
    }

}
