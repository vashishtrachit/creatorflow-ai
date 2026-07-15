package com.creatorflow.video.client;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Component
public class FfmpegClient {

    public Path createVideo(
            Path image,
            Path audio,
            Path output)
            throws Exception {

        ProcessBuilder builder = new ProcessBuilder(

                List.of(

                        "ffmpeg",

                        "-y",

                        "-loop", "1",

                        "-i", image.toString(),

                        "-i", audio.toString(),

                        "-c:v", "libx264",

                        "-tune", "stillimage",

                        "-c:a", "aac",

                        "-b:a", "192k",

                        "-pix_fmt", "yuv420p",

                        "-shortest",

                        output.toString()

                )

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

            throw new RuntimeException(
                    "FFmpeg failed with exit code "
                            + exitCode
            );

        }

        return output;
    }

    public void mergeVideos(Long projectId) throws Exception {

        Path videoFolder = Path.of("creatorflow-data")
                .resolve("projects")
                .resolve(projectId.toString())
                .resolve("video");

        Path listFile = videoFolder.resolve("videos.txt");

        List<Path> videos = Files.list(videoFolder)
                .filter(path -> path.getFileName().toString().startsWith("scene"))
                .filter(path -> path.toString().endsWith(".mp4"))
                .sorted((p1, p2) -> {

                    int scene1 = Integer.parseInt(
                            p1.getFileName()
                                    .toString()
                                    .replace("scene", "")
                                    .replace(".mp4", "")
                    );

                    int scene2 = Integer.parseInt(
                            p2.getFileName()
                                    .toString()
                                    .replace("scene", "")
                                    .replace(".mp4", "")
                    );

                    return Integer.compare(scene1, scene2);

                })
                .toList();
        List<String> lines = new ArrayList<>();

        for (Path video : videos) {

            lines.add("file '" + video.getFileName() + "'");

        }

        Files.write(listFile, lines);

        ProcessBuilder builder = new ProcessBuilder(

                "ffmpeg",

                "-y",

                "-f", "concat",

                "-safe", "0",

                "-i", listFile.getFileName().toString(),

                "-c", "copy",

                "final.mp4"

        );

        builder.directory(videoFolder.toFile());

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

            throw new RuntimeException(
                    "Video merge failed."
            );

        }

    }
}