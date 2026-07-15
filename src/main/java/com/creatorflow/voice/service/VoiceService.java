package com.creatorflow.voice.service;

import com.creatorflow.voice.provider.VoiceProvider;
import org.springframework.stereotype.Service;

@Service
public class VoiceService {

    private final VoiceProvider provider;

    public VoiceService(VoiceProvider provider) {
        this.provider = provider;
    }

    public byte[] generateSpeech(String text) throws Exception {
        return provider.generateSpeech(text);
    }
}