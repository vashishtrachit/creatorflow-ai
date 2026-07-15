package com.creatorflow.music.client;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class FfmpegAudioClient {

    public Path mixAudio(
            Path narration,
            Path backgroundMusic,
            Path output)
            throws Exception {

        Files.deleteIfExists(output);

        ProcessBuilder builder = new ProcessBuilder(

                "ffmpeg",

                "-y",

                "-i",
                narration.toString(),

                "-i",
                backgroundMusic.toString(),

                "-filter_complex",
                "[1:a]volume=0.10[m];[0:a][m]amix=inputs=2:duration=first:dropout_transition=2",

                "-c:a",
                "libmp3lame",

                "-q:a",
                "2",

                output.toString()

        );

        builder.redirectErrorStream(true);

        Process process = builder.start();

        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                process.getInputStream()
                        )
                );

        String line;

        while ((line = reader.readLine()) != null) {

            System.out.println(line);

        }

        int exitCode = process.waitFor();

        if (exitCode != 0) {

            Files.deleteIfExists(output);

            throw new RuntimeException(
                    "Failed to mix background music."
            );

        }

        if (!Files.exists(output) || Files.size(output) == 0) {

            throw new RuntimeException(
                    "Mixed audio file is empty."
            );

        }

        return output;
    }
}