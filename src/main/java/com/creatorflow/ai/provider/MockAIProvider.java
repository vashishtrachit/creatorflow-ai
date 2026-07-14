package com.creatorflow.ai.provider;

import org.springframework.stereotype.Component;

@Component
public class MockAIProvider implements AIProvider {

    @Override
    public String generateStory(String topic) {

        return """
                Once upon a time,
                there was a beautiful story about
                %s.

                They lived happily ever after.
                """.formatted(topic);

    }
}