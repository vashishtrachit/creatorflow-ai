package com.creatorflow.image.provider;

import com.creatorflow.image.client.GeminiImageClient;
import com.creatorflow.image.provider.ImageProvider;
import org.springframework.stereotype.Component;

@Component
public class GeminiImageProvider implements ImageProvider {

    private final GeminiImageClient client;

    public GeminiImageProvider(GeminiImageClient client) {
        this.client = client;
    }

    @Override
    public String generateImage(String prompt) {
        return client.generateImage(prompt);
    }
}