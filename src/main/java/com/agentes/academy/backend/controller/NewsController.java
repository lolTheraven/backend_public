package com.agentes.academy.backend.controller;

import com.agentes.academy.backend.domain.News;
import com.agentes.academy.backend.service.NewsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
public class NewsController {
    private NewsService newsService;

    public NewsController(NewsService newsService){
        this.newsService = newsService;
    }

    @GetMapping("/news")
    public String listOfNews(Model model){
        model.addAttribute("news", newsService.getAllNews());
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
    public String createNews(@ModelAttribute("news") News news){
        newsService.createNews(news);
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
