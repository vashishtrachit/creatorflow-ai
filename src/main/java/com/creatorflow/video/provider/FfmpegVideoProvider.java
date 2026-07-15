package com.creatorflow.video.provider;

import com.creatorflow.video.client.FfmpegClient;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class FfmpegVideoProvider implements VideoProvider {

    private final FfmpegClient client;

    public FfmpegVideoProvider(FfmpegClient client) {
        this.client = client;
    }

    @Override
    public Path generateVideo(
            Path image,
            Path audio,
            Path output)
            throws Exception {

        return client.createVideo(
                image,
                audio,
                output
        );
    }

    @Override
    public void mergeVideos(Long projectId)
            throws Exception {

        client.mergeVideos(projectId);

    }
}