package com.creatorflow.content.service;

import com.creatorflow.video.service.VideoService;
import org.springframework.stereotype.Service;

@Service
public class VideoMergeService {

    private final VideoService videoService;

    public VideoMergeService(VideoService videoService) {
        this.videoService = videoService;
    }

    public void merge(Long projectId)
            throws Exception {

        videoService.mergeVideos(projectId);

    }
}