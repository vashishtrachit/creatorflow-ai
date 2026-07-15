package com.creatorflow.content.service;

import com.creatorflow.ai.service.AIService;
import com.creatorflow.content.dto.ContentPackageResponse;
import com.creatorflow.content.dto.CreateProjectRequest;
import com.creatorflow.content.dto.CreateProjectResponse;
import com.creatorflow.content.model.Project;
import com.creatorflow.content.model.ProjectStatus;
import com.creatorflow.content.repository.ProjectRepository;
import com.creatorflow.image.service.ImageService;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProjectService {

    private static final AtomicLong COUNTER = new AtomicLong();

    private final ProjectRepository repository;
    private final AIService aiService;
    private final ImageService imageService;

    public ProjectService(ProjectRepository repository,
                          AIService aiService,
                          ImageService imageService) {

        this.repository = repository;
        this.aiService = aiService;
        this.imageService = imageService;
    }

    public CreateProjectResponse createProject(CreateProjectRequest request) throws Exception {

        Long id = COUNTER.incrementAndGet();

        ContentPackageResponse content =
                aiService.generateContent(request.topic());

        List<Path> imagePaths = new ArrayList<>();

        for (var scene : content.scenes()) {
try{
            Path imagePath =
                    imageService.generateImage(
                            scene.imagePrompt()
                    );

            imagePaths.add(imagePath);
            Thread.sleep(2000);
            System.out.println("Generated Image : " + imagePath);
        } catch (Exception e) {
    System.out.println(
            "Skipping image generation for Scene "
                    + scene.sceneNumber()
    );
}
}

        Project project = new Project(
                id,
                request.topic(),
                ProjectStatus.CREATED
        );

        repository.save(project);

        return new CreateProjectResponse(
                id,
                request.topic(),
                ProjectStatus.CREATED.name(),
                content.story()
        );
    }

    public Collection<Project> findAllProjects() {
        return repository.findAll();
    }
}