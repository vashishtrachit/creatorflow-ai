package com.creatorflow.ai.client;

import com.creatorflow.ai.config.GeminiProperties;
import com.creatorflow.ai.dto.gemini.Content;
import com.creatorflow.ai.dto.gemini.GenerateContentRequest;
import com.creatorflow.ai.dto.gemini.GenerateContentResponse;
import com.creatorflow.ai.dto.gemini.Part;
import com.creatorflow.ai.prompt.ContentPromptBuilder;
import com.creatorflow.content.dto.ContentPackageResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class GeminiClient {
    private final RestClient restClient;
    private final GeminiProperties properties;
    private final ContentPromptBuilder promptBuilder;
    private final ObjectMapper objectMapper;


    public GeminiClient( GeminiProperties properties,ContentPromptBuilder promptBuilder,
                         ObjectMapper objectMapper) {
        this.restClient = RestClient.builder().baseUrl("https://generativelanguage.googleapis.com").build();
        this.properties = properties;
        this.promptBuilder=promptBuilder;
        this.objectMapper = objectMapper;

    }

    public ContentPackageResponse generateContent(String topic) {

        GenerateContentRequest request =
                new GenerateContentRequest(
                        List.of(
                                new Content(
                                        List.of(
                                                new Part(
                                                        promptBuilder.build(topic)
                                                )
                                )
                        )
                )
        );
        try {

            GenerateContentResponse response =
                    restClient.post()
                            .uri("/v1beta/models/{model}:generateContent?key={key}",
                                    properties.model(),
                                    properties.key())
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(request)
                            .retrieve()
                            .body(GenerateContentResponse.class);

            if (response == null
                    || response.candidates() == null
                    || response.candidates().isEmpty()) {

                throw new RuntimeException("Empty response received from Gemini");
            }

            String json= response.candidates()
                    .get(0)
                    .content()
                    .parts()
                    .get(0)
                    .text();
        return objectMapper.readValue(
                json,
                ContentPackageResponse.class
        );
        } catch (Exception ex) {

            throw new RuntimeException("Failed to call Gemini API", ex);

        }
    }
}
