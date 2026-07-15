package com.creatorflow.content.controller;

import com.creatorflow.content.dto.CreateProjectRequest;
import com.creatorflow.content.dto.CreateProjectResponse;
import com.creatorflow.content.dto.HealthResponse;
import com.creatorflow.content.model.Project;
import com.creatorflow.content.service.ProjectService;
import com.creatorflow.image.service.ImageService;
import com.creatorflow.integration.google.vertex.VertexImageClient;
import com.creatorflow.storage.FileStorageService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1")
public class ContentController {

    private final ProjectService projectService;
    private final ImageService imageService;
    private final VertexImageClient vertexImageClient;

    public ContentController(ProjectService projectService, ImageService imageService,
                             VertexImageClient vertexImageClient,FileStorageService fileStorageService) {
        this.projectService = projectService;
        this.imageService = imageService;
        this.vertexImageClient = vertexImageClient;
    }

    @GetMapping("/health")
    public HealthResponse health() {
        return new HealthResponse(
                "UP",
                "CreatorFlow AI",
                "1.0.0"
        );
    }

    @PostMapping("/projects")
    public CreateProjectResponse createProject(
            @Valid @RequestBody CreateProjectRequest request) throws Exception {

        return projectService.createProject(request);
    }

    @GetMapping("/projects")
    public Collection<Project> getProjects() {
        return projectService.findAllProjects();
    }

}