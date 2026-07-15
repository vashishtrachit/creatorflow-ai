package com.creatorflow.content.service;

import com.creatorflow.music.service.BackgroundMusicService;
import com.creatorflow.storage.FileStorageService;
import com.creatorflow.storage.dto.ProjectMetadata;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class AudioMixingService {

    private final BackgroundMusicService backgroundMusicService;
    private final FileStorageService fileStorageService;

    public AudioMixingService(
            BackgroundMusicService backgroundMusicService,
            FileStorageService fileStorageService) {

        this.backgroundMusicService = backgroundMusicService;
        this.fileStorageService = fileStorageService;
    }

    public void generate(Long projectId) throws Exception {

        ProjectMetadata metadata =
                fileStorageService.loadMetadata(projectId);

        System.out.println(metadata);
        System.out.println(metadata.backgroundMusic());
        Path backgroundMusic =
                fileStorageService.getBackgroundMusic(
                        metadata.backgroundMusic()
                );

        var content =
                fileStorageService.loadStory(projectId);

        for (var scene : content.scenes()) {

            Path narration =
                    fileStorageService.getAudioFile(
                            projectId,
                            scene.sceneNumber()
                    );

            Path mixed =
                    fileStorageService.getMixedAudioFile(
                            projectId,
                            scene.sceneNumber()
                    );

            if (Files.exists(mixed)) {

                System.out.println(
                        "Mixed audio already exists for Scene "
                                + scene.sceneNumber()
                );

                continue;
            }
            System.out.println(metadata);
            System.out.println(metadata.backgroundMusic());
            backgroundMusicService.mix(
                    narration,
                    backgroundMusic,
                    mixed
            );

            System.out.println(
                    "Mixed audio generated for Scene "
                            + scene.sceneNumber()
            );

        }

    }

}