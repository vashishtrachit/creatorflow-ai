package com.creatorflow.integration.google.vertex;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerateContentResponse;
import com.google.cloud.vertexai.api.Part;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.google.protobuf.ByteString;
import org.springframework.stereotype.Component;

@Component
public class VertexImageClient {

    private final VertexAI vertexAI;

    public VertexImageClient(VertexAI vertexAI) {
        this.vertexAI = vertexAI;
    }

    public byte[] generateImage(String prompt) throws Exception {

        Exception lastException = null;

        for (int attempt = 1; attempt <= 3; attempt++) {

            try {

                GenerativeModel model =
                        new GenerativeModel(
                                "gemini-2.5-flash-image",
                                vertexAI
                        );

                GenerateContentResponse response =
                        model.generateContent(prompt);

                for (Part part : response.getCandidates(0)
                        .getContent()
                        .getPartsList()) {

                    if (part.hasInlineData()) {

                        ByteString imageBytes =
                                part.getInlineData().getData();

                        return imageBytes.toByteArray();
                    }
                }

                throw new RuntimeException("No image returned.");

            } catch (Exception ex) {

                lastException = ex;

                System.out.println(
                        "Image generation failed. Attempt "
                                + attempt + "/3"
                );

                if (attempt < 3) {
                    Thread.sleep(5000);
                }
            }
        }

        throw new RuntimeException(
                "Image generation failed after 3 attempts.",
                lastException
        );
    }
}