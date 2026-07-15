package com.creatorflow.content.service;

import com.creatorflow.content.dto.ContentPackageResponse;
import com.creatorflow.image.service.ImageService;
import com.creatorflow.storage.FileStorageService;
import org.springframework.stereotype.Service;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class ImageGenerationService {

    private final ImageService imageService;
    private final FileStorageService fileStorageService;

    public ImageGenerationService(
            ImageService imageService,
            FileStorageService fileStorageService) {

        this.imageService = imageService;
        this.fileStorageService = fileStorageService;
    }

    public void generate(
            Long projectId)
            throws Exception {
        ContentPackageResponse content =
                fileStorageService.loadStory(projectId);

        for (var scene : content.scenes()) {

            try {

                Path imageFile =
                        fileStorageService.getImageFile(
                                projectId,
                                scene.sceneNumber()
                        );

                if (Files.exists(imageFile)) {

                    System.out.println(
                            "Image already exists for Scene "
                                    + scene.sceneNumber()
                    );

                    continue;
                }

                byte[] image =
                        imageService.generateImage(
                                scene.imagePrompt()
                        );

                fileStorageService.saveImage(
                        projectId,
                        scene.sceneNumber(),
                        image
                );

                System.out.println(
                        "Image generated for Scene "
                                + scene.sceneNumber()
                );

            } catch (Exception ex) {

                System.out.println(
                        "Image failed for Scene "
                                + scene.sceneNumber()
                );

                ex.printStackTrace();

            }

        }

    }

}