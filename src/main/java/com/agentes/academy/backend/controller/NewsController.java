package com.agentes.academy.backend.controller;

import com.agentes.academy.backend.domain.Category;
import com.agentes.academy.backend.domain.News;
import com.agentes.academy.backend.service.ImageService;
import com.agentes.academy.backend.service.NewsService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Controller
public class NewsController {
    private NewsService newsService;
    private ImageService imageService;

    public NewsController(NewsService newsService, ImageService imageService){
        this.newsService = newsService;
        this.imageService = imageService;
    }

    @GetMapping("/news")
    public String listOfNews(Model model, Category category){

        if(category != null){
            model.addAttribute("news", newsService.getFilteredNews(category));
        }
        else {
            model.addAttribute("news", newsService.getAllNews());
        }
        return "news";
    }

    @GetMapping("/news/{id}")
    public String oneNews(@PathVariable Long id, Model model){
        model.addAttribute("news", newsService.getOneNews(id));
        return "news_detail";
    }

    @GetMapping("/news/new")
    public String createNews(Model model){
        News news = new News();
        model.addAttribute("news", news);
        return "create_news";
    }

    @GetMapping("/news/edit/{id}")
    public String editNews(@PathVariable Long id, Model model){
        model.addAttribute("news", newsService.getOneNews(id));
        return "edit_news";
    }

    @PostMapping("/news")
    public String createNews(@Valid @ModelAttribute("news") News news, BindingResult bindingResult, Model model, @RequestBody MultipartFile image) throws IOException {
        boolean errors = bindingResult.hasErrors();
        if (errors){
            model.addAttribute("news", news);
            return "create_news";
        }
        newsService.createNews(news);
        imageService.saveImage(image);
        return "redirect:/news";
    }

    @PostMapping("/news/{id}")
    public String updateNews(@PathVariable Long id, @ModelAttribute("news") News news, Model model){
        News existingNews = newsService.getOneNews(id);
        existingNews.setId(id);
        existingNews.setName(news.getName());
        existingNews.setPerex(news.getPerex());
        existingNews.setContent(news.getContent());
        existingNews.setCategory(news.getCategory());
        existingNews.setUpdatedAt(LocalDateTime.now());
        newsService.updateNews(existingNews);
        return "redirect:/news";
    }
}
