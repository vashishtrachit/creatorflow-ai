package com.creatorflow.content.service;

import com.creatorflow.ai.service.AIService;
import com.creatorflow.content.dto.ContentPackageResponse;
import com.creatorflow.content.dto.CreateProjectRequest;
import com.creatorflow.content.dto.CreateProjectResponse;
import com.creatorflow.content.model.PipelineStage;
import com.creatorflow.content.model.Project;
import com.creatorflow.content.model.ProjectStatus;
import com.creatorflow.content.repository.ProjectRepository;
import com.creatorflow.storage.FileStorageService;
import com.creatorflow.storage.dto.ProjectMetadata;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProjectService {

    private static final AtomicLong COUNTER = new AtomicLong();

    private final ProjectRepository repository;
    private final AIService aiService;
    private final FileStorageService fileStorageService;
    private final PipelineService pipelineService;

    public ProjectService(ProjectRepository repository,
                          AIService aiService,
                          FileStorageService fileStorageService,
                          ImageGenerationService imageGenerationService,
                          AudioGenerationService audioGenerationService,
                          PipelineService pipelineService) {

        this.repository = repository;
        this.aiService = aiService;
        this.fileStorageService=fileStorageService;
        this.pipelineService=pipelineService;
    }

    public CreateProjectResponse createProject(CreateProjectRequest request) throws Exception {

        Long id = COUNTER.incrementAndGet();

        fileStorageService.createProjectFolder(id);

        ContentPackageResponse content =
                aiService.generateContent(request.topic());

        fileStorageService.saveStory(id, content);

        fileStorageService.saveMetadata(

                new ProjectMetadata(

                        id,

                        request.topic(),

                        ProjectStatus.CREATED.name(),

                        LocalDateTime.now()
                )

        );
        pipelineService.execute(
                id,
                PipelineStage.ALL
        );

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