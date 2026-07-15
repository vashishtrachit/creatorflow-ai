package com.creatorflow.content.service;

import com.creatorflow.ai.service.AIService;
import com.creatorflow.content.dto.ContentPackageResponse;
import com.creatorflow.content.dto.CreateProjectRequest;
import com.creatorflow.content.dto.CreateProjectResponse;
import com.creatorflow.content.model.Project;
import com.creatorflow.content.model.ProjectStatus;
import com.creatorflow.content.repository.ProjectRepository;
import com.creatorflow.image.service.ImageService;
import com.creatorflow.storage.FileStorageService;
import com.creatorflow.storage.dto.ProjectMetadata;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.time.LocalDateTime;
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
    private final FileStorageService fileStorageService;

    public ProjectService(ProjectRepository repository,
                          AIService aiService,
                          ImageService imageService,
                          FileStorageService fileStorageService) {

        this.repository = repository;
        this.aiService = aiService;
        this.imageService = imageService;
        this.fileStorageService=fileStorageService;
    }

    public CreateProjectResponse createProject(CreateProjectRequest request) throws Exception {

        Long id = COUNTER.incrementAndGet();

        ContentPackageResponse content =
                aiService.generateContent(request.topic());

        fileStorageService.createProjectFolder(id);

        fileStorageService.saveStory(id, content);

        fileStorageService.saveMetadata(

                new ProjectMetadata(

                        id,

                        request.topic(),

                        ProjectStatus.CREATED.name(),

                        LocalDateTime.now()
                )

        );

        for (var scene : content.scenes()) {

            try {

                byte[] image =
                        imageService.generateImage(
                                scene.imagePrompt()
                        );

                Path savedImage =
                        fileStorageService.saveImage(
                                id,
                                scene.sceneNumber(),
                                image
                        );

                System.out.println("Generated Image : " + savedImage);

                Thread.sleep(2000);

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