package com.helloIftekhar.springJwt.Service;

import com.helloIftekhar.springJwt.Bean.News;
import com.helloIftekhar.springJwt.DTO.NewsDTO;
import com.helloIftekhar.springJwt.Repository.NewsRepository;
import com.helloIftekhar.springJwt.Utils.Enum.NewsStatus;
import com.helloIftekhar.springJwt.Utils.Responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsService {
    @Autowired
    NewsRepository newsRepository;

    public Response<NewsDTO> createNews(News request) {
        try {
            News news = new News();
            news.setTitle(request.getTitle());
            news.setContent(request.getContent());
            news.setAuthor(request.getAuthor());
            news.setPic(request.getPic());
            news.setStatus(NewsStatus.ACTIVE);
            news.setCreatedDate(LocalDateTime.now());
            news.setUpdatedDate(news.getCreatedDate());

            news = newsRepository.save(news);

            NewsDTO createdNews = new NewsDTO(news);

            return new Response<>("success", createdNews);
        } catch (Exception e) {
            return new Response<>("unsuccess", null);
        }
    }


    public Response<NewsDTO> updateNews(News request) {
        try {

            News updatedNews = newsRepository.findById(request.getId()).orElseThrow(() -> new RuntimeException("no news found"));
            updatedNews.setTitle(request.getTitle());
            updatedNews.setContent(request.getContent());
            updatedNews.setAuthor(request.getAuthor());
            updatedNews.setPic(request.getPic());
            updatedNews.setStatus(request.getStatus());
            updatedNews.setUpdatedDate(LocalDateTime.now());
            newsRepository.save(updatedNews);

            NewsDTO newsDTO = new NewsDTO(updatedNews);

            return new Response<>("success", newsDTO);
        } catch (Exception e) {
            return new Response<>("unsuccess", null);
        }
    }

    public List<NewsDTO> getAllNews() {
        List<News> newsList = newsRepository.findAll();
        return newsList.stream()
                .map(NewsDTO::new)
                .collect(Collectors.toList());
    }
}
