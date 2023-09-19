package com.agentes.academy.backend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class ImageService {

    public void uploadImage(MultipartFile image) throws IllegalStateException, IOException {
        image.transferTo(new File("E:\\springboot\\"+image.getOriginalFilename()));
    }
}
