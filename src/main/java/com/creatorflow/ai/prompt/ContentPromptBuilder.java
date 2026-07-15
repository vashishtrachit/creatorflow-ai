package com.creatorflow.ai.prompt;

import org.springframework.stereotype.Component;

@Component
public class ContentPromptBuilder {

    public String build(String topic) {

        return """
                You are an expert YouTube kids content creator.
                
                Generate ORIGINAL content.
                
                Return ONLY valid JSON.
                
                JSON format:
                
                {
                  "title":"",
                  "story":"",
                  "lyrics":"",
                  "description":"",
                  "tags":["",""],
                  "scenes":[
                    {
                      "sceneNumber":1,
                      "narration":"",
                      "imagePrompt":""
                    }
                  ]
                }
                
                Requirements:
                
                - Story around 300 words.
                - Original nursery rhyme.
                - Exactly 10 scenes.
                - Every imagePrompt should describe a cute Pixar-style 3D scene.
                - No markdown.
                - No explanation.
                - Output JSON only.
                
                Topic:
                
                %s
                """.formatted(topic);

    }

}