package com.helloIftekhar.springJwt.Service;

import com.helloIftekhar.springJwt.Bean.Post;
import com.helloIftekhar.springJwt.Bean.Stray;
import com.helloIftekhar.springJwt.Bean.User;
import com.helloIftekhar.springJwt.DTO.StrayDTO;
import com.helloIftekhar.springJwt.Repository.AdoptionRepository;
import com.helloIftekhar.springJwt.Repository.PostRepository;
import com.helloIftekhar.springJwt.Repository.StrayRepository;
import com.helloIftekhar.springJwt.Repository.UserRepository;
import com.helloIftekhar.springJwt.Utils.Enum.StrayStatus;
import com.helloIftekhar.springJwt.Utils.Responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StrayService {
    @Autowired
    private StrayRepository strayRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private AdoptionRepository adoptionRepository;

    public Response<StrayDTO> createStray(StrayDTO strayDTO) {
        try {
            Stray stray = new Stray();
            stray.setUser(new User(strayDTO.getUser()));
            stray.setName(strayDTO.getName());
            stray.setAge(strayDTO.getAge());
            stray.setGender(strayDTO.getGender());
            stray.setBehaviour(strayDTO.getBehaviour());
            stray.setMainPicture(strayDTO.getMainPicture());
            stray.setPictureUrl(strayDTO.getPictureUrl());
            stray.setIsVaccinated(strayDTO.getIsVaccinated());
            stray.setIsDewormed(strayDTO.getIsDewormed());
            stray.setStatus(StrayStatus.AVAILABLE);
            stray.setCreatedDate(LocalDateTime.now());
            stray.setUpdatedDate(stray.getCreatedDate());
            strayRepository.save(stray);

//            create Post
            postService.createAdoptionPost(stray);

            StrayDTO savedStray = new StrayDTO(stray);
            return new Response<>("success", savedStray);
        } catch (Exception e) {
            return new Response<>("unsuccess", null);
        }
    }

    public Response<String> deleteStray(StrayDTO strayDTO) {
        try{
//            Post relatedPost = postRepository.findPostByStray_StrayId(strayDTO.getStrayId());
//            postRepository.delete(relatedPost);
//            Stray selectedStray = strayRepository.findById(strayDTO.getStrayId()).orElseThrow(() -> new RuntimeException("Stray not found"));
//            strayRepository.delete(selectedStray);
//
//            return new Response<>("success", "Stray deleted successfully");

            Stray selectedStray = strayRepository.findById(strayDTO.getStrayId()).orElseThrow(() -> new RuntimeException("Stray not found"));
            selectedStray.setStatus(StrayStatus.REMOVED);
            selectedStray.setUpdatedDate(LocalDateTime.now());
            strayRepository.save(selectedStray);

            return new Response<>("success", "Stray deleted successfully");
        }catch(Exception e){
            return new Response<>("unsuccess", null);
        }
    }

    public Response<List<StrayDTO>> getAllStrays() {
        try {
            List<Stray> strays = strayRepository.findAllByStatusNotOrderByUpdatedDateDesc(StrayStatus.REMOVED);

            // Map each Stray entity to StrayDTO and check if it has an adoption application
            List<StrayDTO> strayDTOs = strays.stream().map(stray -> {
                boolean hasAdoptionApplication = adoptionRepository.existsByStray(stray);
                return new StrayDTO(stray, hasAdoptionApplication);
            }).toList();

            return new Response<>("success", strayDTOs);
        } catch (Exception e) {
            return new Response<>("unsuccess", null);

        }
    }

    public Response<StrayDTO> updateStrayStatus(Long id, StrayStatus status) {
        try {
            Stray selectedStray = strayRepository.findById(id).orElseThrow(() -> new RuntimeException("Stray not found"));
            selectedStray.setStatus(status);
            selectedStray.setUpdatedDate(LocalDateTime.now());
            strayRepository.save(selectedStray);

            StrayDTO savedStray = new StrayDTO(selectedStray);
            return new Response<>("success", savedStray);
        } catch (Exception e) {
            return new Response<>("unsuccess", null);

        }
    }

    public Response<List<StrayDTO>> getAllStrayList() {
        try {
            List<Stray> strays = strayRepository.findAllStray();
            return new Response<>("success", strays.stream().map(stray -> new StrayDTO(stray)).toList());
        } catch (Exception e) {
            return new Response<>("unsuccess", null);
        }
    }

    public Response<StrayDTO> getStrayDetailsById(Long id) {
        try{
            Stray stray = strayRepository.findById(id).orElseThrow(() -> new RuntimeException("Stray not found"));
            return new Response<>("success", new StrayDTO(stray));
        }catch (Exception e){
            return new Response<>("unsuccess", null);
        }
    }

    public Response<StrayDTO> updateStray(StrayDTO request) {
        try {
            Stray updateStray = strayRepository.findById(request.getStrayId()).orElseThrow(() -> new RuntimeException("no stray found"));
            updateStray.setName(request.getName());
            updateStray.setAge(request.getAge());
            updateStray.setGender(request.getGender());
            updateStray.setBehaviour(request.getBehaviour());
            updateStray.setMainPicture(request.getMainPicture());
            updateStray.setPictureUrl(request.getPictureUrl());
            updateStray.setIsVaccinated(request.getIsVaccinated());
            updateStray.setIsDewormed(request.getIsDewormed());
            updateStray.setUpdatedDate(LocalDateTime.now());
            strayRepository.save(updateStray);

            StrayDTO strayDTO = new StrayDTO(updateStray);

            return new Response<>("sucess", strayDTO);
        } catch (Exception e) {
            return new Response<>("unsuccess", null);
        }
    }
}
