package com.creatorflow.image.dto.google;

import java.util.List;

public record GenerateImageRequest(

        List<Content> contents,

        GenerationConfig generationConfig

) {
}