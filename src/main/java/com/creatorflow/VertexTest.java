package com.creatorflow;

import com.google.cloud.vertexai.VertexAI;

public class VertexTest {

    public static void main(String[] args) throws Exception {

        try (VertexAI vertexAI =
                     new VertexAI("project-eeb63564-49aa-4ef1-b70", "us-central1")) {

            System.out.println("Vertex AI Connected Successfully!");

        }

    }
}