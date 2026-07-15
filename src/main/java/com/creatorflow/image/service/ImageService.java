package com.creatorflow.image.service;

import com.creatorflow.image.provider.ImageProvider;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    private final ImageProvider provider;

    public ImageService(ImageProvider provider) {
        this.provider = provider;
    }

    public String generateImage(String prompt) {
        return provider.generateImage(prompt);
    }
}