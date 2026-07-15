package com.creatorflow.storage;

import com.creatorflow.content.dto.ContentPackageResponse;
import com.creatorflow.storage.dto.ProjectMetadata;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class FileStorageService {

    private final ObjectMapper objectMapper;
    private final StorageProperties properties;

    public FileStorageService(ObjectMapper objectMapper,StorageProperties properties) {
        this.objectMapper = objectMapper;
        this.properties=properties;
    }

    private Path getRoot() {
        return Path.of(properties.rootFolder());
    }
    public Path createProjectFolder(Long projectId) throws IOException {

        Path projectFolder = getRoot()
                .resolve("projects")
                .resolve(projectId.toString());

        Files.createDirectories(projectFolder);

        Files.createDirectories(projectFolder.resolve("images"));
        Files.createDirectories(projectFolder.resolve("audio"));
        Files.createDirectories(projectFolder.resolve("video"));
        Files.createDirectories(projectFolder.resolve("subtitles"));
        Files.createDirectories(projectFolder.resolve("thumbnail"));

        return projectFolder;
    }

    public void saveStory(Long projectId,
                          ContentPackageResponse content)
            throws IOException {

        Path storyFile = getRoot()
                .resolve("projects")
                .resolve(projectId.toString())
                .resolve("story.json");

        objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValue(storyFile.toFile(), content);
    }

    public Path saveImage(Long projectId,
                          int sceneNumber,
                          byte[] imageBytes)
            throws IOException {

        Path imageFile = getRoot()
                .resolve("projects")
                .resolve(projectId.toString())
                .resolve("images")
                .resolve("scene" + sceneNumber + ".png");

        Files.write(imageFile, imageBytes);

        return imageFile;
    }

    public void saveMetadata(ProjectMetadata metadata)
            throws IOException {

        Path metadataFile = getRoot()
                .resolve("projects")
                .resolve(metadata.projectId().toString())
                .resolve("metadata.json");

        objectMapper
                .writerWithDefaultPrettyPrinter()
                .writeValue(
                        metadataFile.toFile(),
                        metadata
                );
    }
}