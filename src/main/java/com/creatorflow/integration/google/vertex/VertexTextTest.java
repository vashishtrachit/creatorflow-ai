package com.creatorflow.integration.google.vertex;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class VertexTextTest {

    private final VertexAI vertexAI;

    public VertexTextTest(VertexAI vertexAI) {
        this.vertexAI = vertexAI;
    }
    String prompt = """
A cute 3D Pixar-style baby mermaid with pink curly hair,
purple tail,
big blue eyes,
underwater coral reef,
vibrant colors,
Pixar style,
children's cartoon,
ultra detailed.
""";
    public void test() throws Exception {

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

        Files.write(
                Path.of("generated-image.png"),
                imageBytes.toByteArray()
        );

        System.out.println(response.getCandidates(0));

    }
}