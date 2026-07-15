package com.creatorflow.storage.dto;

import java.time.LocalDateTime;

public record ProjectMetadata(

        Long projectId,

        String topic,

        String status,

        LocalDateTime createdAt

) {
}