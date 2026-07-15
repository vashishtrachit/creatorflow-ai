package com.creatorflow.ai.dto.gemini;

import java.util.List;

public record GenerateContentRequest(

        List<Content> contents

) {
}