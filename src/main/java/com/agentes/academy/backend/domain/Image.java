package com.agentes.academy.backend.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class Image extends TimeStamps {
    @Column(name = "image_name")
    private String image_name;

    @Column(name = "MIME")
    private String mime_type;

    @Column(name = "path")
    private String path;

    public Image () {

    }

    public Image (String image_name, String mime_type, String path) {
        super();
        this.image_name = image_name;
        this.mime_type = mime_type;
        this.path = path;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getMime_type() {
        return mime_type;
    }

    public void setMime_type(String mime_type) {
        this.mime_type = mime_type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
