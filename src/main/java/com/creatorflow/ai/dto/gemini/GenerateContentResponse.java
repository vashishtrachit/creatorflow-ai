package com.creatorflow.ai.dto.gemini;

import java.util.List;

public record GenerateContentResponse(

        List<Candidate> candidates

) {
}