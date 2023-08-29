package com.agentes.academy.backend.service;

import com.agentes.academy.backend.domain.News;
import com.agentes.academy.backend.repository.NewsRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NewsService {

    private NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository){
        this.newsRepository = newsRepository;
    }

    public List<News> getAllNews(){
        List<News> news = new ArrayList<>();
        newsRepository.findAll().forEach(news::add);
        return news;
    }

    public News createNews(News news){
        return newsRepository.save(news);
    }

    public Optional<News> getNews(Long id){
        return newsRepository.findById(id);
    }
}
