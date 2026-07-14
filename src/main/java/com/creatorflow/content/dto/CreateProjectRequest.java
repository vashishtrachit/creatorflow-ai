package com.creatorflow.content.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateProjectRequest (@NotBlank(message = "Topic cannot be empty") String topic){
}
