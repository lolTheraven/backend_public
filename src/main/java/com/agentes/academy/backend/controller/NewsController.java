package com.agentes.academy.backend.controller;

import com.agentes.academy.backend.domain.Category;
import com.agentes.academy.backend.domain.News;
import com.agentes.academy.backend.service.ImageService;
import com.agentes.academy.backend.service.NewsService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class NewsController {
    private NewsService newsService;
    private ImageService imageService;

    public NewsController(NewsService newsService, ImageService imageService){
        this.newsService = newsService;
        this.imageService = imageService;
    }

    public String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/images/";

//    @GetMapping("/news")
//    public String listOfNews(Model model, Category category){
//
//        if(category != null){
//            model.addAttribute("news", newsService.getFilteredNews(category));
//        }
//        else {
//            model.addAttribute("news", newsService.getAllNews());
//        }
//        return "news";
//    }

    @GetMapping("/news")
    public String getAllPages(Model model) {
        return getOnePage(model, 1);
    }


    @GetMapping("/news/page/{pageNumber}")
    public String getOnePage(Model model, @PathVariable("pageNumber") int currentPage) {
        Page<News> page = newsService.getPaginatedNews(currentPage);
        int totalPages = page.getTotalPages();
        Long totalItems = page.getTotalElements();
        List<News> news = page.getContent();
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("total_pages", totalPages);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("news", news);
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
        String path = image.getOriginalFilename();
        news.setPath(path);
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
