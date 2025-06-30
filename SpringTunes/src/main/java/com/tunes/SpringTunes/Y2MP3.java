package com.tunes.SpringTunes;

import java.io.*;

public class Y2MP3 {

    public static void main(String[] args) {
        String ytDlpPath = "C:\\Users\\srivi\\Projects\\SpringTunes\\yt-dlp.exe\\";
        String videoUrl = "https://youtu.be/nF5pK0t9btM?si=bM08iYrdXNyllD-k"; // replace with actual video
        String outputDir = "C:\\Users\\srivi\\Projects\\SpringTunes\\LocalSongs\\%(title)s.%(ext)s"; // output location

        // yt-dlp command
        String[] command = {
                ytDlpPath,
                "-x",
                "--audio-format", "mp3",
                "--ffmpeg-location", "C:\\Users\\srivi\\Projects\\SpringTunes\\ffmpeg-master-latest-win64-gpl\\bin", // Adjust this path to your ffmpeg bin
                videoUrl,
                "-o", outputDir
        };


        ProcessBuilder builder = new ProcessBuilder(command);
        builder.redirectErrorStream(true); // merge stdout and stderr

        try {
            Process process = builder.start();

            // Read the output
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Download and conversion successful!");
            } else {
                System.out.println("Error occurred with exit code: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
