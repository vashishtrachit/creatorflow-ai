package com.creatorflow.image.provider;

import com.creatorflow.integration.google.vertex.VertexImageClient;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class GeminiImageProvider implements ImageProvider {

    private final VertexImageClient client;

    public GeminiImageProvider(VertexImageClient client) {
        this.client = client;
    }

    @Override
    public byte[] generateImage(String prompt) throws Exception {
        return client.generateImage(prompt);
    }
}