package com.helloIftekhar.springJwt.Service;

import com.helloIftekhar.springJwt.Bean.Donation;
import com.helloIftekhar.springJwt.Bean.User;
import com.helloIftekhar.springJwt.DTO.DonationDTO;
import com.helloIftekhar.springJwt.DTO.UserDTO;
import com.helloIftekhar.springJwt.Repository.DonationRepository;
import com.helloIftekhar.springJwt.Repository.UserRepository;
import com.helloIftekhar.springJwt.Utils.Enum.DonationStatus;
import com.helloIftekhar.springJwt.Utils.Responses.Response;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DonationService {
    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private UserRepository userRepository;

//    public Response<DonationDTO> processDonation(DonationDTO request) {
//        try {
//            Donation donation = new Donation();
//            donation.setCreatedDate(LocalDateTime.now());
//            donation.setAmount(request.getAmount());
//            donation.setIsAnonymously(request.getIsAnonymously());
//            donation.setStatus(request.getStatus());
////            donation.setUser(userRepository.findById(request.getId())
////                    .orElseThrow(() -> new RuntimeException("User not found")));
//            donation.setUser(userRepository.findByUsername(request.getUserName()).orElseThrow(()-> new RuntimeException("User not found")));
//
//            donation = donationRepository.save(donation);
//            DonationDTO responseDTO = new DonationDTO(donation);
//
//            return new Response<>("Payment successful", responseDTO);
//        } catch (Exception e) {
//            return new Response<>("Payment failed", null);
//        }
//    }

    public Response<DonationDTO> createDonation(DonationDTO donation) {
        try{
            Donation createdDonation = new Donation();
            createdDonation.setIsAnonymously(donation.getIsAnonymously());
            createdDonation.setAmount(donation.getAmount());
//            createdDonation.setUser(new User(donation.getUser()));
            createdDonation.setStatus(DonationStatus.PENDING);
            if(donation.getUser() == null){
                createdDonation.setUser(null);
            }else{
                createdDonation.setUser(new User(donation.getUser()));
            }
            createdDonation.setCreatedDate(LocalDateTime.now());
            createdDonation.setUpdatedDate(createdDonation.getCreatedDate());

            createdDonation = donationRepository.save(createdDonation);

            DonationDTO savedDonation = new DonationDTO(createdDonation);
            return new Response<>("success", savedDonation);

        }catch(Exception e){
            return new Response<>("unsuccess", null);
        }
    }

    public Response<List<DonationDTO>> getDonationListByUserId(Integer userId) {
        try{
            List<Donation> donationList= donationRepository.findAllByUserIdOrderByUpdatedDateDesc(userId);
            return new Response<>("success", donationList.stream().map(donation-> new DonationDTO(donation)).toList());
        }catch(Exception e){
            return new Response<>("unsuccess", null);
        }
    }

    public Response<List<DonationDTO>> getAllDonationList(){
        try{
            List<Donation> donationList= donationRepository.findAllByOrderByUpdatedDateDesc();
            return new Response<>("success", donationList.stream().map(donation-> new DonationDTO(donation)).toList());
        }catch(Exception e){
            return new Response<>("unsuccess", null);
        }
    }

    public Response<List<UserDTO>> getDonorList(){
        try{
            List<User> donorList = donationRepository.findAllUserByOrderByUpdatedDateDesc();
            return new Response<>("success", donorList.stream().map(user-> new UserDTO(user)).toList());
        }catch(Exception e){
            return new Response<>("unsuccess", null);
        }
    }

    @Transactional
    public Response<DonationDTO> updateDonationStatus(Long id, DonationStatus status) {
        try{
            Donation selectedDonation = donationRepository.findById(id).orElseThrow(()->new RuntimeException("Donation not found"));
            selectedDonation.setStatus(status);
            selectedDonation.setUpdatedDate(LocalDateTime.now());
            donationRepository.save(selectedDonation);

            DonationDTO savedDonation = new DonationDTO(selectedDonation);
            return new Response<>("success", savedDonation);
        }catch(Exception e){
            return new Response<>("unsuccess", null);
        }
    }

}
