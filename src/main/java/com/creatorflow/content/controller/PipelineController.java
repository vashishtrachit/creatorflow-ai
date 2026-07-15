package com.creatorflow.content.controller;

import com.creatorflow.content.model.PipelineStage;
import com.creatorflow.content.service.PipelineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
public class PipelineController {

    private final PipelineService pipelineService;

    public PipelineController(
            PipelineService pipelineService) {

        this.pipelineService = pipelineService;
    }

    @PostMapping("/{projectId}/pipeline/images")
    public ResponseEntity<String> regenerateImages(
            @PathVariable Long projectId)
            throws Exception {

        pipelineService.execute(
                projectId,
                PipelineStage.IMAGES
        );

        return ResponseEntity.ok(
                "Image generation completed."
        );
    }

    @PostMapping("/{projectId}/pipeline/audio")
    public ResponseEntity<String> regenerateAudio(
            @PathVariable Long projectId)
            throws Exception {

        pipelineService.execute(
                projectId,
                PipelineStage.AUDIO
        );

        return ResponseEntity.ok(
                "Audio generation completed."
        );
    }

    @PostMapping("/{projectId}/pipeline/video")
    public ResponseEntity<String> regenerateVideo(
            @PathVariable Long projectId)
            throws Exception {

        pipelineService.execute(
                projectId,
                PipelineStage.VIDEO
        );

        return ResponseEntity.ok(
                "Video generation completed."
        );
    }

    @PostMapping("/{projectId}/pipeline/merge")
    public ResponseEntity<String> mergeVideo(
            @PathVariable Long projectId)
            throws Exception {

        pipelineService.execute(
                projectId,
                PipelineStage.MERGE
        );

        return ResponseEntity.ok(
                "Video merge completed."
        );
    }

    @PostMapping("/{projectId}/pipeline/all")
    public ResponseEntity<String> executePipeline(
            @PathVariable Long projectId)
            throws Exception {

        pipelineService.execute(
                projectId,
                PipelineStage.ALL
        );

        return ResponseEntity.ok(
                "Pipeline completed."
        );
    }

    @PostMapping("/{projectId}/pipeline/audio-mix")
    public ResponseEntity<String> mixAudio(
            @PathVariable Long projectId)
            throws Exception {

        pipelineService.execute(
                projectId,
                PipelineStage.AUDIO_MIX
        );

        return ResponseEntity.ok(
                "Audio mixing completed."
        );
    }
}