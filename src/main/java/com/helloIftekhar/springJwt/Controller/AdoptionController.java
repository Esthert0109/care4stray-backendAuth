package com.helloIftekhar.springJwt.Controller;

import com.helloIftekhar.springJwt.DTO.AdoptionApplicationDTO;
import com.helloIftekhar.springJwt.DTO.StrayDTO;
import com.helloIftekhar.springJwt.Service.AdoptionService;
import com.helloIftekhar.springJwt.Service.StrayService;
import com.helloIftekhar.springJwt.Utils.Responses.Response;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/care4stray/adoption")
public class AdoptionController {

    @Autowired
    private StrayService strayService;

    @Autowired
    private AdoptionService adoptionService;

    @GetMapping("/getStray")
    public ResponseEntity<Response<List<StrayDTO>>> getAllStray() {
        return ResponseEntity.ok(strayService.getAllStrayList());
    }

    @GetMapping("/getStray/{id}")
    public ResponseEntity<Response<StrayDTO>> getStrayById(@PathVariable("id") Long id) {
        if (id == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(strayService.getStrayDetailsById(id));
    }

    @PostMapping("/create_application")
    public ResponseEntity<Response<AdoptionApplicationDTO>> createAdoptionApplication(@RequestBody AdoptionApplicationDTO request, HttpServletRequest header) {
        if (request == null) {

            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(adoptionService.createAdoptionApplication(request, header));
    }

    @PostMapping("/application_list")
    public ResponseEntity<Response<List<AdoptionApplicationDTO>>> getApplicationList(@RequestBody Map<String, Integer> request) {
        Integer id = request.get("userId");
        if (request == null || id == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(adoptionService.getAdoptionApplications(id));
    }

    @GetMapping("/application/{applicationId}")
    public ResponseEntity<Response<AdoptionApplicationDTO>> getApplicationById(@PathVariable("applicationId") Long applicationId) {
        if (applicationId == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(adoptionService.getAdoptionApplicationDetail(applicationId));
    }

    @PostMapping("/application/update")
    public ResponseEntity<Response<AdoptionApplicationDTO>> updateApplication(@RequestBody AdoptionApplicationDTO request, HttpServletRequest header) {
        if (request == null || header == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(adoptionService.updateApplication(request, header));
    }

    @PostMapping("/application/cancel")
    public ResponseEntity<Response<String>> cancelApplication(@RequestBody AdoptionApplicationDTO request) {
        if (request == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(adoptionService.deleteApplication(request.getAdoptionId(), request.getUser().getId()));
    }

}
