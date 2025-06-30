package com.tunes.SpringTunes.controller;

import com.tunes.SpringTunes.model.Songs;
import com.tunes.SpringTunes.service.SongService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    private final SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    // GET: Get all songs
    @GetMapping
    public ResponseEntity<List<Songs>> getAllSongs() {
        List<Songs> songs = songService.getAllSongs();
        return ResponseEntity.ok(songs);
    }

    // GET: Search songs by name (case-insensitive)
    @GetMapping("/search")
    public ResponseEntity<List<Songs>> searchSongs(@RequestParam String query) {
        List<Songs> songs = songService.searchByName(query);
        return ResponseEntity.ok(songs);
    }

    // GET: Get song by ID
    @GetMapping("/{id}")
    public ResponseEntity<Songs> getSongById(@PathVariable Long id) {
        Optional<Songs> song = songService.getSongById(id);
        return song.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    // DELETE: Delete song by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSong(@PathVariable Long id) {
        try {
            songService.deleteSong(id);
            return ResponseEntity.ok("Song deleted successfully");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}