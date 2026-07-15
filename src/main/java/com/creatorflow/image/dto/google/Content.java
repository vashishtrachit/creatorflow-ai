package com.creatorflow.image.dto.google;

import java.util.List;

public record Content(
        String role,
        List<Part> parts
) {
}