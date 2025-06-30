package com.tunes.SpringTunes.controller;

import com.tunes.SpringTunes.repository.UserRepository;
import com.tunes.SpringTunes.service.SongService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class DownloadController {

    private final SongService songService;
    private final UserRepository userRepository;

    public DownloadController(SongService songService, UserRepository usersRepository) {
        this.songService = songService;
        this.userRepository = usersRepository;
    }

    @PostMapping("/api/download")
    public ResponseEntity<Map<String, String>> downloadSong(@RequestBody Map<String, String> payload) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        String videoUrl = payload.get("url");
        String videoTitle = payload.get("title");

        if (videoUrl == null || videoUrl.isEmpty()) {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Invalid video URL.");
            return ResponseEntity.badRequest().body(response);
        }

        new Thread(() -> convertAndDownload(videoUrl, videoTitle, username)).start();

        Map<String, String> response = new HashMap<>();
        response.put("message", "Started downloading and converting...");
        return ResponseEntity.ok(response);
    }

    private void convertAndDownload(String videoUrl, String videoTitle, String username) {
        String ytDlpPath = "C:\\Users\\srivi\\Projects\\SpringTunes\\yt-dlp.exe";
        String ffmpegPath = "C:\\Users\\srivi\\Projects\\SpringTunes\\ffmpeg-master-latest-win64-gpl\\bin";

        // We'll store sanitized filename only (no extension here)
        String outputDirBase = "C:\\Users\\srivi\\Projects\\SpringTunes\\LocalSongs\\";

        // Sanitize title before passing to yt-dlp
        String safeFileName = sanitizeFilename(videoTitle != null ? videoTitle : "UnknownTitle");
        String fullOutputPath = outputDirBase + safeFileName + ".%(ext)s"; // yt-dlp will add .mp3

        String[] command = {
                ytDlpPath,
                "-x",
                "--audio-format", "mp3",
                "--ffmpeg-location", ffmpegPath,
                videoUrl,
                "-o", fullOutputPath
        };

        ProcessBuilder builder = new ProcessBuilder(command);
        builder.redirectErrorStream(true);

        try {
            Process process = builder.start();

            String sanitizedFinalName = null;

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                    if (line.contains("Destination:")) {
                        int index = line.indexOf("Destination:");
                        String filePath = line.substring(index + "Destination:".length()).trim();
                        sanitizedFinalName = new File(filePath).getName().replace(".mp3", "");
                    }
                }
            }

            int exitCode = process.waitFor();

            if (exitCode == 0 && sanitizedFinalName != null) {
                String finalSafeName = sanitizeFilename(sanitizedFinalName) + ".mp3";
                String dbLocation = finalSafeName;

                // Save to DB using the same sanitized name used in the filesystem
                songService.saveSong(username, videoTitle != null ? videoTitle : sanitizedFinalName, dbLocation);
                System.out.println("Conversion successful for: " + videoUrl);
            } else {
                System.err.println("Error occurred for: " + videoUrl + " | Exit code: " + exitCode);
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String sanitizeFilename(String title) {
        return title.replaceAll("[^a-zA-Z0-9 ._\\-]", "_")  // Replace bad chars
                .replaceAll("[()]", "")                 // Remove parentheses
                .replaceAll("\\s+", "_");               // Replace spaces with underscores
    }
}