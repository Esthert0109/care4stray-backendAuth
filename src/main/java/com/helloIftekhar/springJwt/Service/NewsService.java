package com.helloIftekhar.springJwt.Service;

import com.helloIftekhar.springJwt.Bean.News;
import com.helloIftekhar.springJwt.Bean.User;
import com.helloIftekhar.springJwt.DTO.NewsDTO;
import com.helloIftekhar.springJwt.DTO.UserDTO;
import com.helloIftekhar.springJwt.Repository.NewsRepository;
import com.helloIftekhar.springJwt.Utils.Enum.NewsStatus;
import com.helloIftekhar.springJwt.Utils.Responses.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
                .map(news -> new NewsDTO(news, formatDuration(news.getCreatedDate())))
                .collect(Collectors.toList());
    }

    public static String formatDuration(LocalDateTime inputDate) {
        try {

            // Set the time zone for the current time to the system's default time zone
            LocalDateTime currentTime = LocalDateTime.now();

            // Calculate time difference
            Duration duration = Duration.between(inputDate, currentTime);

            // Format result
            if (duration.toMinutes() < 1) {
                return duration.getSeconds() + " seconds ago";
            } else if (duration.toHours() < 1) {
                return duration.toMinutes() + " minutes ago";
            } else if (duration.toDays() < 1) {
                return duration.toHours() + " hours ago";
            } else if (duration.toDays() == 1) {
                return "yesterday";
            } else if (duration.toDays() < 365) {
                return inputDate.format(DateTimeFormatter.ofPattern("dd-MMM"));
            } else {
                return inputDate.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Error formatting date";
        }
    }

    public Response<NewsDTO> getNewsDetails(Long id) {
        News news = null;
        try{
            news = newsRepository.findById(id).orElseThrow(() -> new RuntimeException("News not found"));
        } catch (RuntimeException e) {
            return new Response<>("unsuccess", null);
        }
        String duration = formatDuration(news.getCreatedDate());
        NewsDTO newsDetails = new NewsDTO(news, duration);
        return new Response<>("success", newsDetails);
    }
}
