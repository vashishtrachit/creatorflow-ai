package com.creatorflow;

import com.creatorflow.ai.config.GeminiProperties;
import com.creatorflow.image.config.GoogleImageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({GeminiProperties.class, GoogleImageProperties.class})
public class CreatorFlowApplication {
    public static void main(String[] args) {
        SpringApplication.run(CreatorFlowApplication.class, args);
    }
}
