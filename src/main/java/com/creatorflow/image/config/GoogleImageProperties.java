package com.creatorflow.image.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "google.image")
public record GoogleImageProperties(

        String key,

        String model

) {
}