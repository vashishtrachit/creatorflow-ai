package com.creatorflow.voice.provider;

public interface VoiceProvider {

    byte[] generateSpeech(String text) throws Exception;

}