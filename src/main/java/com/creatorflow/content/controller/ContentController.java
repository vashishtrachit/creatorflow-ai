package com.creatorflow.content.controller;

import com.creatorflow.content.dto.CreateProjectRequest;
import com.creatorflow.content.dto.CreateProjectResponse;
import com.creatorflow.content.dto.HealthResponse;
import com.creatorflow.content.model.Project;
import com.creatorflow.content.service.ProjectService;
import com.creatorflow.image.service.ImageService;
import com.creatorflow.integration.google.vertex.VertexTextTest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1")
public class ContentController {

    private final ProjectService projectService;
    private final ImageService imageService;
    private final VertexTextTest vertexTextTest;

    public ContentController(ProjectService projectService,ImageService imageService,
                             VertexTextTest vertexTextTest) {
        this.projectService = projectService;
        this.imageService=imageService;
        this.vertexTextTest=vertexTextTest;
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
            @Valid @RequestBody CreateProjectRequest request) {

        return projectService.createProject(request);
    }

    @GetMapping("/projects")
    public Collection<Project> getProjects() {
        return projectService.findAllProjects();
    }

    @GetMapping("/image")
    public String generateImage() {

        return imageService.generateImage(
                "A cute 3D Pixar-style baby mermaid with pink hair, purple tail, smiling, underwater coral reef, vibrant colors."
        );

    }

    @GetMapping("/vertex-test")
    public String vertexTest() throws Exception {

        vertexTextTest.test();

        return "Done";
    }
}