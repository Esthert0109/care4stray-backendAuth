package com.helloIftekhar.springJwt.Service;

import com.helloIftekhar.springJwt.Bean.Adoption;
import com.helloIftekhar.springJwt.Bean.Stray;
import com.helloIftekhar.springJwt.Bean.User;
import com.helloIftekhar.springJwt.DTO.AdoptionApplicationDTO;
import com.helloIftekhar.springJwt.Repository.AdoptionRepository;
import com.helloIftekhar.springJwt.Repository.StrayRepository;
import com.helloIftekhar.springJwt.Repository.UserRepository;
import com.helloIftekhar.springJwt.Service.Auth.AuthenticationService;
import com.helloIftekhar.springJwt.Utils.Enum.AdoptionStatus;
import com.helloIftekhar.springJwt.Utils.Enum.StrayStatus;
import com.helloIftekhar.springJwt.Utils.Responses.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdoptionService {

    @Autowired
    private AdoptionRepository adoptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StrayRepository strayRepository;

    @Autowired
    private AuthenticationService authService;

    @Autowired
    private StrayService strayService;

    public Response<AdoptionApplicationDTO> createAdoptionApplication(AdoptionApplicationDTO adoptionApplicationDTO, HttpServletRequest requestHeader) {
        try {
            User appliedUser = new User(adoptionApplicationDTO.getUser());
            //update user info
            authService.updateUserInfo(requestHeader, appliedUser);

            //            get user info back
            User updatedUser = userRepository.findById(appliedUser.getId()).orElseThrow(() -> new RuntimeException("User not found"));

            //            set status
            strayService.updateStrayStatus(adoptionApplicationDTO.getStray().getStrayId(), StrayStatus.ADOPTION_IN_PROGRESS);


            //            find stray info
            Stray stray = strayRepository.findById(adoptionApplicationDTO.getStray().getStrayId()).orElseThrow(() -> new RuntimeException("Stray not found"));

            Adoption adoptionApplication = new Adoption();
            adoptionApplication.setStray(stray);
            adoptionApplication.setUser(updatedUser);
            adoptionApplication.setCreatedDate(LocalDateTime.now());
            adoptionApplication.setAdoptionStatus(AdoptionStatus.APPLICATION_PENDING);
            adoptionRepository.save(adoptionApplication);

            AdoptionApplicationDTO applicationResponse = new AdoptionApplicationDTO(adoptionApplication);

            return new Response<>("success", applicationResponse);

        } catch (RuntimeException e) {
            return new Response<>("unsuccess", null);
        }
    }

    public Response<List<AdoptionApplicationDTO>> getAdoptionApplications(Integer userId) {
       try{
           List<Adoption> adoptionList = adoptionRepository.findByUserIdOrderByCreatedDateDesc(userId);
           return new Response<>("success", adoptionList.stream().map(adoption -> new AdoptionApplicationDTO(adoption)).toList());
       }catch (RuntimeException e) {
           return new Response<>("unsuccess", null);
       }
    }

    public Response<List<AdoptionApplicationDTO>> getAdminAdoptionApplications() {
        try{
            List<Adoption> adoptionList = adoptionRepository.findAllByOrderByCreatedDateDesc();
            return new Response<>("success", adoptionList.stream().map(adoption -> new AdoptionApplicationDTO(adoption)).toList());
        }catch (RuntimeException e) {
            return new Response<>("unsuccess", null);
        }
    }

    public Response<AdoptionApplicationDTO> getAdoptionApplicationDetail(Long applicationId){
        try{
            Adoption selectedAdoption = adoptionRepository.findById(applicationId).orElseThrow(() -> new RuntimeException("Adoption not found"));
            AdoptionApplicationDTO applicationDTO = new AdoptionApplicationDTO(selectedAdoption);
            return new Response<>("success", applicationDTO);
        }catch (RuntimeException e) {
            return new Response<>("unsuccess", null);
        }
    }

    public Response<AdoptionApplicationDTO> updateApplication(AdoptionApplicationDTO adoptionApplicationDTO, HttpServletRequest requestHeader) {
        try{
            User appliedUser = new User(adoptionApplicationDTO.getUser());
            authService.updateUserInfo(requestHeader, appliedUser);
            Stray selectedStray = strayRepository.findById(adoptionApplicationDTO.getStray().getStrayId()).orElse(null);
            selectedStray.setStatus(StrayStatus.ADOPTION_IN_PROGRESS);
            strayRepository.save(selectedStray);

            Adoption adoptionApplication = adoptionRepository.findById(adoptionApplicationDTO.getAdoptionId()).orElseThrow(() -> new RuntimeException("Adoption not found"));
            adoptionApplication.setStray(selectedStray);
            adoptionApplication.setUser(appliedUser);
            adoptionApplication.setAdoptionStatus(adoptionApplicationDTO.getAdoptionStatus());
            adoptionApplication.setCreatedDate(LocalDateTime.now());

            adoptionRepository.save(adoptionApplication);
            AdoptionApplicationDTO applicationResponse = new AdoptionApplicationDTO(adoptionApplication);

            return new Response<>("success", applicationResponse);
        }catch (RuntimeException e) {
            return new Response<>("unsuccess", null);
        }
    }

    public Response<String> deleteApplication (Long applicationId, Integer userId){
        try{
            Adoption selectedAdoption = adoptionRepository.findByUserIdAndAdoptionId(userId, applicationId);
            if(selectedAdoption != null){
                Long strayId = selectedAdoption.getStray().getStrayId();
                Stray selectedStray = strayRepository.findById(strayId).orElse(null);
                selectedStray.setStatus(StrayStatus.AVAILABLE);
                strayRepository.save(selectedStray);

                adoptionRepository.delete(selectedAdoption);
                return new Response<>("success", "Adoption canceled");
            }else{
                return new Response<>("unsuccess", "Adoption not found");
            }
        }catch (RuntimeException e) {
            return new Response<>("unsuccess", null);
        }
    }
}
