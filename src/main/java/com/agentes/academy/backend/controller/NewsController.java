package com.agentes.academy.backend.controller;

import com.agentes.academy.backend.domain.News;
import com.agentes.academy.backend.service.NewsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/news/new")
    public String createNews(Model model){
        News news = new News();
        model.addAttribute("news", news);
        return "create_news";
    }

    @PostMapping("/news")
    public String createNews(@ModelAttribute("news") News news){
        newsService.createNews(news);
        return "redirect:/news";
    }
}
