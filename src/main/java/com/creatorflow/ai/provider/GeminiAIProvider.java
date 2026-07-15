package com.creatorflow.ai.provider;

import com.creatorflow.ai.client.GeminiClient;
import com.creatorflow.content.dto.ContentPackageResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class GeminiAIProvider implements AIProvider {

    private final GeminiClient geminiClient;

    public GeminiAIProvider(GeminiClient geminiClient) {
        this.geminiClient = geminiClient;
    }

    @Override
    public ContentPackageResponse generateContent(String topic) {
        return geminiClient.generateContent(topic);
    }

}