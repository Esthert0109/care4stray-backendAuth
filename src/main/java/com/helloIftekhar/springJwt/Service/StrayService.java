package com.helloIftekhar.springJwt.Service;

import com.helloIftekhar.springJwt.Bean.Stray;
import com.helloIftekhar.springJwt.Bean.User;
import com.helloIftekhar.springJwt.DTO.StrayDTO;
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

    public Response<StrayDTO> createStray(StrayDTO strayDTO) {
        try {
            Stray stray = new Stray();
            stray.setUser(new User(strayDTO.getUser()));
            stray.setName(strayDTO.getName());
            stray.setAge(strayDTO.getAge());
            stray.setBehaviour(strayDTO.getBehaviour());
            stray.setMainPicture(strayDTO.getMainPicture());
            stray.setPictureUrl(strayDTO.getPictureUrl());
            stray.setVaccinated(stray.isVaccinated());
            stray.setDewormed(stray.isDewormed());
            stray.setStatus(StrayStatus.AVAILABLE);
            stray.setCreatedDate(LocalDateTime.now());
            stray.setUpdatedDate(stray.getCreatedDate());
            strayRepository.save(stray);

            StrayDTO savedStray = new StrayDTO(stray);
            return new Response<>("success", savedStray);
        } catch (Exception e) {
            return new Response<>("unsuccess", null);
        }
    }

    public Response<List<StrayDTO>> getAllStrays() {
        try {
            List<Stray> strays = strayRepository.findAllByOrderByUpdatedDateDesc();
            return new Response<>("success", strays.stream().map(stray -> new StrayDTO(stray)).toList());
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
        try{
            List<Stray> strays = strayRepository.findAllStray();
            return new Response<>("success", strays.stream().map(stray -> new StrayDTO(stray)).toList());
        }catch (Exception e){
            return new Response<>("unsuccess", null);
        }
    }
}
