package com.agentes.academy.backend.service;

import com.agentes.academy.backend.domain.Image;
import com.agentes.academy.backend.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ImageService {
    private ImageRepository imageRepository;

    public ImageService (ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/images/";

    public Image saveImage(MultipartFile image) throws IllegalStateException, IOException {

        File newImage = new File(uploadDirectory + image.getOriginalFilename());
        if (!newImage.exists()) {
            image.transferTo(newImage);
        }
        String image_name = image.getOriginalFilename();
        String MIME_type = image.getContentType();
        String path = uploadDirectory + image.getOriginalFilename();

        Image saveImage = new Image(image_name, MIME_type, path);
        return imageRepository.save(saveImage);
    }
}
