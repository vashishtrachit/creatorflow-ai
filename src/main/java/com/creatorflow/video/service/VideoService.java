package com.creatorflow.video.service;

import com.creatorflow.video.provider.VideoProvider;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class VideoService {

    private final VideoProvider provider;

    public VideoService(VideoProvider provider) {
        this.provider = provider;
    }

    public Path generateVideo(
            Path image,
            Path audio,
            Path output)
            throws Exception {

        return provider.generateVideo(
                image,
                audio,
                output
        );
    }

    public void mergeVideos(Long projectId)
            throws Exception {

        provider.mergeVideos(projectId);

    }
}