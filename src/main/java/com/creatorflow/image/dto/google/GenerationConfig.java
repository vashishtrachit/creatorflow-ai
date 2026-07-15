package com.creatorflow.image.dto.google;

import java.util.List;

public record GenerationConfig(
        Double temperature,
        Integer maxOutputTokens,
        List<String> responseModalities,
        Double topP,
        ImageConfig imageConfig
) {
}