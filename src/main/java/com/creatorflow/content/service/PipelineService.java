package com.creatorflow.content.service;

import com.creatorflow.content.model.PipelineStage;
import org.springframework.stereotype.Service;

@Service
public class PipelineService {

    private final ImageGenerationService imageGenerationService;
    private final AudioGenerationService audioGenerationService;
    private final VideoGenerationService videoGenerationService;
    private final VideoMergeService videoMergeService;
    private final AudioMixingService audioMixingService;

    public PipelineService(
            ImageGenerationService imageGenerationService,
            AudioGenerationService audioGenerationService,
            VideoGenerationService videoGenerationService,
            VideoMergeService videoMergeService,
            AudioMixingService audioMixingService) {

        this.imageGenerationService = imageGenerationService;
        this.audioGenerationService = audioGenerationService;
        this.videoGenerationService = videoGenerationService;
        this.videoMergeService=videoMergeService;
        this.audioMixingService=audioMixingService;
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

            case AUDIO_MIX ->

                    audioMixingService.generate(projectId);

            case ALL -> {

                imageGenerationService.generate(projectId);

                audioGenerationService.generate(projectId);

                audioMixingService.generate(projectId);

                videoGenerationService.generate(projectId);

                videoMergeService.merge(projectId);
            }

            case MERGE -> {
                videoMergeService.merge(projectId);
            }
        }
    }
}