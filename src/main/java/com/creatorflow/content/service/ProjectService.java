package com.creatorflow.content.service;

import com.creatorflow.ai.dto.StoryResponse;
import com.creatorflow.ai.service.AIService;
import com.creatorflow.content.dto.CreateProjectRequest;
import com.creatorflow.content.dto.CreateProjectResponse;
import com.creatorflow.content.model.Project;
import com.creatorflow.content.model.ProjectStatus;
import com.creatorflow.content.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProjectService {

    private static final AtomicLong COUNTER = new AtomicLong();

    private final ProjectRepository repository;
    private final AIService aiService;

    public ProjectService(ProjectRepository repository,
                          AIService aiService) {

        this.repository = repository;
        this.aiService = aiService;
    }

    public CreateProjectResponse createProject(CreateProjectRequest request) {

        Long id = COUNTER.incrementAndGet();

        StoryResponse storyResponse =
                aiService.generateStory(request.topic());

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
                storyResponse.story()
        );
    }

    public Collection<Project> findAllProjects() {
        return repository.findAll();
    }
}