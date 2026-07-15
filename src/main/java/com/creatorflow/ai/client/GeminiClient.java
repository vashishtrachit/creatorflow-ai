package com.creatorflow.ai.client;

import com.creatorflow.ai.prompt.ContentPromptBuilder;
import com.creatorflow.content.dto.ContentPackageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import org.springframework.stereotype.Component;

@Component
public class GeminiClient {

    private final VertexAI vertexAI;
    private final ContentPromptBuilder promptBuilder;
    private final ObjectMapper objectMapper;

    public GeminiClient(VertexAI vertexAI,
                        ContentPromptBuilder promptBuilder,
                        ObjectMapper objectMapper) {

        this.vertexAI = vertexAI;
        this.promptBuilder = promptBuilder;
        this.objectMapper = objectMapper;
    }

    public ContentPackageResponse generateContent(String topic) {

        try {

            GenerativeModel model =
                    new GenerativeModel(
                            "gemini-2.5-flash",
                            vertexAI
                    );

            GenerateContentResponse response =
                    model.generateContent(
                            promptBuilder.build(topic)
                    );

            if (response == null
                    || response.getCandidatesCount() == 0) {

                throw new RuntimeException("Empty response received from Gemini.");
            }

            String json = response.getCandidates(0)
                    .getContent()
                    .getParts(0)
                    .getText();

            return objectMapper.readValue(
                    json,
                    ContentPackageResponse.class
            );

        } catch (Exception ex) {

            throw new RuntimeException(
                    "Failed to call Gemini using Vertex AI",
                    ex
            );
        }
    }
}