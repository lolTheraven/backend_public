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

    @GetMapping("/news")
    public String getAllPages(Model model, Category category) {
        return getOnePage(model, 1, "id", "asc", category);
    }


    @GetMapping("/news/page/{pageNumber}")
    public String getOnePage(Model model, @PathVariable("pageNumber") int currentPage,
                             @RequestParam( required = false, defaultValue = "id", name = "sortField") String sortField,
                             @RequestParam( required = false, defaultValue = "asc", name = "sortDir") String sortDir,
                             Category category) {
        if(category == null) {
            Page<News> page = newsService.getPaginatedNews(currentPage, sortField, sortDir);
            int totalPages = page.getTotalPages();
            Long totalItems = page.getTotalElements();
            List<News> news = page.getContent();
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("total_pages", totalPages);
            model.addAttribute("totalItems", totalItems);

            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDir", sortDir);
            model.addAttribute("reverseSortDir", sortDir.equals("asc")?"desc":"asc");
            model.addAttribute("category", category);

            model.addAttribute("news", news);
        } else {
            Page<News> page = newsService.getPaginatedNewsFilteredByCategorySorted(currentPage, sortField, sortDir, category);
            int totalPages = page.getTotalPages();
            Long totalItems = page.getTotalElements();
            List<News> news = page.getContent();
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("total_pages", totalPages);
            model.addAttribute("totalItems", totalItems);

            model.addAttribute("sortField", sortField);
            model.addAttribute("sortDir", sortDir);
            model.addAttribute("reverseSortDir", sortDir.equals("asc")?"desc":"asc");
            model.addAttribute("category", category);

            model.addAttribute("news", news);
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
        if (image.getOriginalFilename().isEmpty()) {
            model.addAttribute("news", news);
            model.addAttribute("error_msg", "Image is required");
            return "create_news";
        }
        news.setPath(image.getOriginalFilename());
        newsService.createNews(news);
        imageService.saveImage(image);
        return "redirect:/news";
    }

    @PostMapping("/news/{id}")
    public String updateNews(@Valid @PathVariable Long id, @ModelAttribute("news") News news, Model model, @RequestBody MultipartFile image) throws  IOException{
        News existingNews = newsService.getOneNews(id);
        existingNews.setName(news.getName());
        existingNews.setPerex(news.getPerex());
        existingNews.setContent(news.getContent());
        existingNews.setCategory(news.getCategory());
        existingNews.setUpdatedAt(LocalDateTime.now());
        existingNews.setPath(image.getOriginalFilename());
        newsService.updateNews(existingNews);
        return "redirect:/news";

    }
}
