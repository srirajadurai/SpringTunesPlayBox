package com.tunes.SpringTunes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class YouTubeController {

    private static final String YOUTUBE_API_KEY = "YOUR_KEY";
    private static final String YOUTUBE_SEARCH_URL =
            "https://www.googleapis.com/youtube/v3/search?part=snippet&q={query}&type=video&key=" + YOUTUBE_API_KEY;

    @GetMapping("/api/youtube")
    public ResponseEntity<String> searchYouTube(@RequestParam String query) {
        RestTemplate restTemplate = new RestTemplate();
        String url = YOUTUBE_SEARCH_URL.replace("{query}", query);
        String response = restTemplate.getForObject(url, String.class);
        return ResponseEntity.ok(response);
    }
}