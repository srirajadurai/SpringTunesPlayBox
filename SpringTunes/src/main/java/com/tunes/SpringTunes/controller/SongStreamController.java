package com.tunes.SpringTunes.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RestController
@RequestMapping("/songs")
public class SongStreamController {

    private static final String MUSIC_FOLDER = "C:/Users/srivi/Projects/SpringTunes/LocalSongs/";

    @GetMapping("/{filename:.+}")
    public ResponseEntity<InputStreamResource> streamSong(@PathVariable String filename) throws FileNotFoundException {
        File file = new File(MUSIC_FOLDER + filename);
        FileInputStream fileInputStream = new FileInputStream(file);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + file.getName() + "\"")
                .body(new InputStreamResource(fileInputStream));
    }
}