package com.creatorflow.ai.service;

import com.creatorflow.ai.dto.StoryResponse;
import com.creatorflow.ai.provider.AIProvider;
import org.springframework.stereotype.Service;

@Service
public class AIService {

    private final AIProvider aiProvider;

    public AIService(AIProvider aiProvider) {
        this.aiProvider = aiProvider;
    }

    public StoryResponse generateStory(String topic) {

        String story = aiProvider.generateStory(topic);

        return new StoryResponse(story);
    }
}