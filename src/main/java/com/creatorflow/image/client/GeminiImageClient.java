package com.creatorflow.image.client;

import com.creatorflow.image.config.GoogleImageProperties;
import com.creatorflow.image.dto.google.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class GeminiImageClient {

    private final RestClient restClient;
    private final GoogleImageProperties properties;

    public GeminiImageClient(GoogleImageProperties properties) {

        this.properties = properties;

        this.restClient = RestClient.builder()
                .baseUrl("https://generativelanguage.googleapis.com")
                .build();
    }

    public String generateImage(String prompt) {

        GenerateImageRequest request =
                new GenerateImageRequest(

                        List.of(
                                new Content(
                                        "user",
                                        List.of(
                                                new Part(prompt)
                                        )
                                )
                        ),

                        new GenerationConfig(
                                1.0,
                                32768,
                                List.of("TEXT", "IMAGE"),
                                0.95,
                                new ImageConfig(
                                        "1:1",
                                        "1K"
                                )
                        )
                );
        String response = restClient
                .post()
                .uri(
                        "/v1beta/models/{model}:generateContent?key={key}",
                        properties.model(),
                        properties.key()
                )
                .body(request)
                .retrieve()
                .body(String.class);

        System.out.println(response);

        return response;
    }
}