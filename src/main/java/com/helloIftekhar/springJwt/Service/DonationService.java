package com.helloIftekhar.springJwt.Service;

import com.helloIftekhar.springJwt.Bean.Donation;
import com.helloIftekhar.springJwt.DTO.DonationDTO;
import com.helloIftekhar.springJwt.Repository.DonationRepository;
import com.helloIftekhar.springJwt.Repository.UserRepository;
import com.helloIftekhar.springJwt.Utils.Responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DonationService {
    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private UserRepository userRepository;

    public Response<DonationDTO> processDonation(DonationDTO request) {
        try {
            Donation donation = new Donation();
            donation.setCreatedDate(LocalDateTime.now());
            donation.setAmount(request.getAmount());
            donation.setIsAnonymously(request.getIsAnonymously());
            donation.setStatus(request.getStatus());
//            donation.setUser(userRepository.findById(request.getId())
//                    .orElseThrow(() -> new RuntimeException("User not found")));
            donation.setUser(userRepository.findByUsername(request.getUserName()).orElseThrow(()-> new RuntimeException("User not found")));

            donation = donationRepository.save(donation);
            DonationDTO responseDTO = new DonationDTO(donation);

            return new Response<>("Payment successful", responseDTO);
        } catch (Exception e) {
            return new Response<>("Payment failed", null);
        }
    }
}
