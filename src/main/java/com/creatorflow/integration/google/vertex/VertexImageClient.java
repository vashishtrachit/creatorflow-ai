package com.creatorflow.integration.google.vertex;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class VertexImageClient {

    private final VertexAI vertexAI;

    public VertexImageClient(VertexAI vertexAI) {
        this.vertexAI = vertexAI;
    }

    public Path generateImage(String prompt) throws Exception {

        GenerativeModel model =
                new GenerativeModel(
                        "gemini-2.5-flash-image",
                        vertexAI
                );

        GenerateContentResponse response =
                model.generateContent(prompt);

        ByteString imageBytes =
                response.getCandidates(0)
                        .getContent()
                        .getParts(1)
                        .getInlineData()
                        .getData();

        Path output = Files.createTempFile("scene-", ".png");

        Files.write(output, imageBytes.toByteArray());

        return output;
    }
}