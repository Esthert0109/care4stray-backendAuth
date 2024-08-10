package com.helloIftekhar.springJwt.Controller;

import com.helloIftekhar.springJwt.DTO.StrayDTO;
import com.helloIftekhar.springJwt.Service.StrayService;
import com.helloIftekhar.springJwt.Utils.Responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/care4stray/adoption")
public class AdoptionController {

    @Autowired
    private StrayService strayService;

    @GetMapping("/getStray")
    public ResponseEntity<Response<List<StrayDTO>>> getAllStray(){
        return ResponseEntity.ok(strayService.getAllStrayList());
    }

    @GetMapping("/getStray/{id}")
    public ResponseEntity<Response<StrayDTO>> getStrayById(@PathVariable("id") Long id){
        if (id == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(strayService.getStrayDetailsById(id));
    }

}
