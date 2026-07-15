package com.creatorflow.integration.google.vertex;

import com.google.cloud.vertexai.VertexAI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class VertexConfiguration {

    @Bean
    public VertexAI vertexAI() {

        return new VertexAI(
                "project-eeb63564-49aa-4ef1-b70",
                "us-central1"
        );

    }

}