package com.agentes.academy.backend.api.v1.news;

import com.agentes.academy.backend.domain.News;
import com.agentes.academy.backend.service.NewsService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ApiController {

    private NewsService newsService;

    public ApiController (NewsService newsService){
        this.newsService = newsService;
    }

    @GetMapping("/v1/news")
    public List<News> allNews(){
        return newsService.getAllNews();
    }

    @GetMapping("v1/news/{id}")
    public News oneNews(@PathVariable Long id){
        return newsService.getOneNews(id);
    }

    @PostMapping("v1/news")
    public News createNews(@Valid @RequestBody News news){
        return newsService.createNews(news);
    }

    @PutMapping("v1/news/{id}")
    public News updateNews(@Valid @RequestBody News news, @PathVariable Long id){
        News updateNews = newsService.getOneNews(id);
        updateNews.setName(news.getName());
        updateNews.setPerex(news.getPerex());
        updateNews.setContent(news.getContent());
        updateNews.setCategory(news.getCategory());
        updateNews.setUpdatedAt(LocalDateTime.now());
        newsService.updateNews(updateNews);
        return updateNews;
    }


}
