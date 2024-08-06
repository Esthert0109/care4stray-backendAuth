package com.helloIftekhar.springJwt.Controller;


import com.helloIftekhar.springJwt.Bean.News;
import com.helloIftekhar.springJwt.DTO.NewsDTO;
import com.helloIftekhar.springJwt.Service.NewsService;
import com.helloIftekhar.springJwt.Utils.Responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/care4stray/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/lists")
    public ResponseEntity<Response<List<NewsDTO>>> getAllNews() {
        try {
            List<NewsDTO> newsList = newsService.getAllNews();
            return ResponseEntity.ok(new Response<>("success", newsList));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Response<>("unsuccess", null));
        }
    }

    @GetMapping("/lists/{id}")
    public ResponseEntity<Response<NewsDTO>> getNewsDetails(@PathVariable String id) {
        if(id == null ) {
            Response badResponse = new Response<>("unsuccess", null);
            return ResponseEntity.badRequest().body(badResponse);
        }

        Long newsID = Long.parseLong(id);
        return ResponseEntity.ok(newsService.getNewsDetails(newsID));
    }


}
