package com.creatorflow.voice.client;

import com.google.cloud.texttospeech.v1.AudioConfig;
import com.google.cloud.texttospeech.v1.AudioEncoding;
import com.google.cloud.texttospeech.v1.SynthesisInput;
import com.google.cloud.texttospeech.v1.SynthesizeSpeechResponse;
import com.google.cloud.texttospeech.v1.TextToSpeechClient;
import com.google.cloud.texttospeech.v1.VoiceSelectionParams;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Component;

@Component
public class GoogleTtsClient {

    private final TextToSpeechClient client;

    public GoogleTtsClient(TextToSpeechClient client) {
        this.client = client;
    }

    public byte[] generateSpeech(String text) {

        SynthesisInput input =
                SynthesisInput.newBuilder()
                        .setText(text)
                        .build();

        VoiceSelectionParams voice =
                VoiceSelectionParams.newBuilder()
                        .setLanguageCode("en-US")
                        .setName("en-US-Neural2-F")
                        .build();

        AudioConfig audioConfig =
                AudioConfig.newBuilder()
                        .setAudioEncoding(AudioEncoding.MP3)
                        .build();

        SynthesizeSpeechResponse response =
                client.synthesizeSpeech(
                        input,
                        voice,
                        audioConfig
                );

        ByteString audioContents =
                response.getAudioContent();

        return audioContents.toByteArray();
    }
}