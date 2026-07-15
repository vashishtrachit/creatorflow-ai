package com.creatorflow.image.service;

import com.creatorflow.image.provider.ImageProvider;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class ImageService {

    private final ImageProvider provider;

    public ImageService(ImageProvider provider) {
        this.provider = provider;
    }

    public Path generateImage(String prompt) throws Exception {
        return provider.generateImage(prompt);
    }
}