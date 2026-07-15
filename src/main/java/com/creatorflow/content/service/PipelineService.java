package com.creatorflow.content.service;

import com.creatorflow.content.model.PipelineStage;
import org.springframework.stereotype.Service;

@Service
public class PipelineService {

    private final ImageGenerationService imageGenerationService;
    private final AudioGenerationService audioGenerationService;
    private final VideoGenerationService videoGenerationService;
    private final VideoMergeService videoMergeService;

    public PipelineService(
            ImageGenerationService imageGenerationService,
            AudioGenerationService audioGenerationService,
            VideoGenerationService videoGenerationService,
            VideoMergeService videoMergeService) {

        this.imageGenerationService = imageGenerationService;
        this.audioGenerationService = audioGenerationService;
        this.videoGenerationService = videoGenerationService;
        this.videoMergeService=videoMergeService;
    }

    public void execute(
            Long projectId,
            PipelineStage stage)
            throws Exception {

        switch (stage) {

            case IMAGES ->
                    imageGenerationService.generate(projectId);

            case AUDIO ->
                    audioGenerationService.generate(projectId);

            case VIDEO ->
                    videoGenerationService.generate(projectId);

            case ALL -> {

                imageGenerationService.generate(projectId);

                audioGenerationService.generate(projectId);

                videoGenerationService.generate(projectId);

                videoMergeService.merge(projectId);
            }

            case MERGE -> {
                videoMergeService.merge(projectId);
            }
        }
    }
}