package com.creatorflow.content.service;

import com.creatorflow.content.dto.ContentPackageResponse;
import com.creatorflow.storage.FileStorageService;
import com.creatorflow.video.service.VideoService;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class VideoGenerationService {

    private final VideoService videoService;
    private final FileStorageService fileStorageService;

    public VideoGenerationService(
            VideoService videoService,
            FileStorageService fileStorageService) {

        this.videoService = videoService;
        this.fileStorageService = fileStorageService;
    }

    public void generate(Long projectId) throws Exception {

        ContentPackageResponse content =
                fileStorageService.loadStory(projectId);

        for (var scene : content.scenes()) {

            try {

                Path image =
                        fileStorageService.getImageFile(
                                projectId,
                                scene.sceneNumber()
                        );

                Path audio =
                        fileStorageService.getAudioFile(
                                projectId,
                                scene.sceneNumber()
                        );

                Path video =
                        fileStorageService.getVideoFile(
                                projectId,
                                scene.sceneNumber()
                        );

                if (Files.exists(video)) {

                    System.out.println(
                            "Video already exists for Scene "
                                    + scene.sceneNumber()
                    );

                    continue;
                }

                if (!Files.exists(image)) {

                    System.out.println(
                            "Image missing for Scene "
                                    + scene.sceneNumber()
                    );

                    continue;
                }

                if (!Files.exists(audio)) {

                    System.out.println(
                            "Audio missing for Scene "
                                    + scene.sceneNumber()
                    );

                    continue;
                }

                videoService.generateVideo(
                        image,
                        audio,
                        video
                );

                System.out.println(
                        "Video generated for Scene "
                                + scene.sceneNumber()
                );

            } catch (Exception ex) {

                System.out.println(
                        "Video generation failed for Scene "
                                + scene.sceneNumber()
                );

                ex.printStackTrace();

            }

        }

    }

}