package com.creatorflow.music.provider;

import java.nio.file.Path;

public interface AudioMixerProvider {

    Path mixAudio(
            Path narration,
            Path music,
            Path output)
            throws Exception;

}