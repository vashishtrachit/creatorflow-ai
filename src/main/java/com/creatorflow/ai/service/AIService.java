package com.creatorflow.ai.service;

import com.creatorflow.ai.provider.AIProvider;
import com.creatorflow.content.dto.ContentPackageResponse;
import org.springframework.stereotype.Service;

@Service
public class AIService {

    private final AIProvider aiProvider;

    public AIService(AIProvider aiProvider) {
        this.aiProvider = aiProvider;
    }

    public ContentPackageResponse generateContent(String topic) {

        return aiProvider.generateContent(topic);

    }
}