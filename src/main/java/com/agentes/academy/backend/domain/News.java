package com.agentes.academy.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "news")
public class News extends TimeStamps {

    @Column(name = "name")
    @NotBlank(message = "The name is required.")
    @Size(min = 1, max = 200, message = "The name must be between {min} and {max} characters long")
    private String name;

    @Column(name = "perex")
    @NotBlank(message = "The perex is required.")
    @Size(min = 1, max = 450, message = "The perex must be between {min} and {max} characters long")
    private String perex;

    @Column(name = "content")
    @NotBlank(message = "The content is required.")
    private String content;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "image_path")
    private String path;

    public News(){

    }

    public News(String name, String perex, String content,Category category, String path){
        super();
        this.name = name;
        this.perex = perex;
        this.content = content;
        this.category = category;
        this.path = path;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getPerex(){
        return perex;
    }

    public void setPerex(String perex){
        this.perex = perex;
    }

    public String getContent(){
        return content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public Category getCategory(){
        return category;
    }

    public void setCategory(Category category){
        this.category = category;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
