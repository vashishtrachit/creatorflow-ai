package com.creatorflow.voice.provider;

import com.creatorflow.voice.client.GoogleTtsClient;
import org.springframework.stereotype.Component;

@Component
public class GoogleTtsProvider implements VoiceProvider {

    private final GoogleTtsClient client;

    public GoogleTtsProvider(GoogleTtsClient client) {
        this.client = client;
    }

    @Override
    public byte[] generateSpeech(String text) {

        return client.generateSpeech(text);
    }
}