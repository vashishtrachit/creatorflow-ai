package com.creatorflow.video.provider;

import java.nio.file.Path;

public interface VideoProvider {

    Path generateVideo(
            Path image,
            Path audio,
            Path output
    ) throws Exception;

    void mergeVideos(Long projectId)
            throws Exception;

}