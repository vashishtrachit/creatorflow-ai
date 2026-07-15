package com.creatorflow.music.provider;

import com.creatorflow.music.client.FfmpegAudioClient;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

@Component
public class FfmpegAudioProvider
        implements AudioMixerProvider {

    private final FfmpegAudioClient client;

    public FfmpegAudioProvider(
            FfmpegAudioClient client) {

        this.client = client;
    }

    @Override
    public Path mixAudio(
            Path narration,
            Path music,
            Path output)
            throws Exception {

        return client.mixAudio(
                narration,
                music,
                output
        );
    }
}