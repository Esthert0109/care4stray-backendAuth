package com.helloIftekhar.springJwt.Controller;


import com.helloIftekhar.springJwt.Bean.News;
import com.helloIftekhar.springJwt.DTO.NewsDTO;
import com.helloIftekhar.springJwt.Service.NewsService;
import com.helloIftekhar.springJwt.Utils.Responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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


}
