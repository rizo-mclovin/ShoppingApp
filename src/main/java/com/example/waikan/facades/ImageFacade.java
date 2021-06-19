package com.example.waikan.facades;

import com.example.waikan.models.Image;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class ImageFacade {
    public Image toEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        for (int i = 0; i < file.getBytes().length; i++) {
            System.out.print(file.getBytes()[i]);
        }
        return image;
    }
}
