package com.creatorflow.content.service;

import com.creatorflow.content.dto.ContentPackageResponse;
import com.creatorflow.storage.FileStorageService;
import com.creatorflow.voice.service.VoiceService;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class AudioGenerationService {

    private final VoiceService voiceService;
    private final FileStorageService fileStorageService;

    public AudioGenerationService(
            VoiceService voiceService,
            FileStorageService fileStorageService) {

        this.voiceService = voiceService;
        this.fileStorageService = fileStorageService;
    }

    public void generate(Long projectId)
            throws Exception {

        ContentPackageResponse content =
                fileStorageService.loadStory(projectId);

        for (var scene : content.scenes()) {

            try {

                Path audioFile =
                        fileStorageService.getAudioFile(
                                projectId,
                                scene.sceneNumber()
                        );

                if (Files.exists(audioFile)) {

                    System.out.println(
                            "Audio already exists for Scene "
                                    + scene.sceneNumber()
                    );

                    continue;
                }

                byte[] audio =
                        voiceService.generateSpeech(
                                scene.narration()
                        );

                fileStorageService.saveAudio(
                        projectId,
                        scene.sceneNumber(),
                        audio
                );

                System.out.println(
                        "Audio generated for Scene "
                                + scene.sceneNumber()
                );

            } catch (Exception ex) {

                System.out.println(
                        "Audio failed for Scene "
                                + scene.sceneNumber()
                );

                ex.printStackTrace();

            }

        }

    }
}