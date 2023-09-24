package com.agentes.academy.backend.service;

import com.agentes.academy.backend.domain.Category;
import com.agentes.academy.backend.domain.News;
import com.agentes.academy.backend.repository.NewsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    public Page<News> getPaginatedNews(int pageNumber, String sortField, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending():
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNumber-1, 5, sort);
        return newsRepository.findAll(pageable);
    }

    public News createNews(News news){
        return newsRepository.save(news);
    }

    public News getOneNews(Long id){
        return newsRepository.findById(id).get();
    }

    public News updateNews(News news){
        return newsRepository.save(news);
    }

    public List<News> getFilteredNews(Category category){
        return newsRepository.findByCategory(category);
    }

    public Page<News> getPaginatedNewsFilteredByCategory(int pageNumber, Category category) {
        Pageable pageable = PageRequest.of(pageNumber-1, 5);
        return newsRepository.findPaginatedByCategory(category, pageable);
    }

    public Page<News> getPaginatedNewsFilteredByCategorySorted(int pageNumber, String sortField, String sortDirection, Category category) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending():
                Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNumber-1, 5, sort);
        return newsRepository.findPaginatedByCategory(category, pageable);
    }

}