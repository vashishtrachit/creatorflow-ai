package com.creatorflow.music.service;

import com.creatorflow.music.provider.AudioMixerProvider;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class BackgroundMusicService {

    private final AudioMixerProvider provider;

    public BackgroundMusicService(
            AudioMixerProvider provider) {

        this.provider = provider;
    }

    public Path mix(
            Path narration,
            Path music,
            Path output)
            throws Exception {

        return provider.mixAudio(
                narration,
                music,
                output
        );

    }

}