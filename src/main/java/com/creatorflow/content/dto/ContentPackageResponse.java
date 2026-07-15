package com.creatorflow.content.dto;

import java.util.List;

public record ContentPackageResponse(

        String title,

        String story,

        String lyrics,

        String description,

        List<String> tags,

        List<SceneResponse> scenes

) {
}