package com.helloIftekhar.springJwt.Controller;

import com.helloIftekhar.springJwt.Bean.Donation;
import com.helloIftekhar.springJwt.Bean.User;
import com.helloIftekhar.springJwt.DTO.DonationDTO;
import com.helloIftekhar.springJwt.DTO.UserDTO;
import com.helloIftekhar.springJwt.Service.Auth.AuthenticationService;
import com.helloIftekhar.springJwt.Service.DonationService;
import com.helloIftekhar.springJwt.Utils.Responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/care4stray/user")
public class DonationController {

    @Autowired
    private DonationService donationService;

    @PostMapping("/makePayment")
    public ResponseEntity<Response<DonationDTO>> makePayment(@RequestBody DonationDTO request) {
        try {
            Response<DonationDTO> response = donationService.processDonation(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>("Payment failed", null));
        }
    }
}
