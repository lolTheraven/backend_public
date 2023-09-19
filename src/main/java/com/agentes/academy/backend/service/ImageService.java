package com.agentes.academy.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ImageService {
    public static String uploadDirectory = System.getProperty("user.dir") + "/src/main/resources/static/images";

    public void uploadImage(MultipartFile image) throws IllegalStateException, IOException {
        image.transferTo(new File(uploadDirectory +image.getOriginalFilename()));
    }
}
