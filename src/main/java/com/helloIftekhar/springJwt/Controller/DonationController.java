package com.helloIftekhar.springJwt.Controller;

import com.helloIftekhar.springJwt.Bean.Donation;
import com.helloIftekhar.springJwt.Bean.User;
import com.helloIftekhar.springJwt.DTO.DonationDTO;
import com.helloIftekhar.springJwt.DTO.UserDTO;
import com.helloIftekhar.springJwt.Service.Auth.AuthenticationService;
import com.helloIftekhar.springJwt.Service.DonationService;
import com.helloIftekhar.springJwt.Utils.Enum.DonationStatus;
import com.helloIftekhar.springJwt.Utils.Responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/care4stray/donation")
public class DonationController {

    @Autowired
    private DonationService donationService;

//    @PostMapping("/makePayment")
//    public ResponseEntity<Response<DonationDTO>> makePayment(@RequestBody DonationDTO request) {
//        try {
//            Response<DonationDTO> response = donationService.processDonation(request);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new Response<>("Payment failed", null));
//        }
//    }

    @PostMapping("/create")
    public ResponseEntity<Response<DonationDTO>> createDonation(@RequestBody DonationDTO donation) {
        if(donation == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.ok(donationService.createDonation(donation));
    }

    @GetMapping("/list")
    public ResponseEntity<Response<List<DonationDTO>>> getDonationsByUserId(@RequestParam("userId") Integer userId) {
        if(userId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(donationService.getDonationListByUserId(userId));
    }

    @GetMapping("/all")
    public ResponseEntity<Response<List<DonationDTO>>> getAllDonation() {
        try {
            return ResponseEntity.ok(donationService.getAllUserViewDonationList());
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response<>("unsuccess", null));
        }
    }

    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<Response<DonationDTO>> updateDonationStatus(@RequestBody Map<String, String> request, @PathVariable Long id) {
        if(request == null || id == null || request.get("donationStatus") == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        String status = request.get("donationStatus");
        DonationStatus donationStatus = DonationStatus.valueOf(status.toUpperCase());
        return ResponseEntity.ok(donationService.updateDonationStatus(id, donationStatus));
    }

    @GetMapping("/donationList")
    public ResponseEntity<Response<List<UserDTO>>> getDonorList(){
        return ResponseEntity.ok(donationService.getDonorList());
    }
}
