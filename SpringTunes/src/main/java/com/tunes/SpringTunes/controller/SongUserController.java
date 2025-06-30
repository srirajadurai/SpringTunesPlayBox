package com.tunes.SpringTunes.controller;

import com.tunes.SpringTunes.model.Songs;
import com.tunes.SpringTunes.repository.UserRepository;
import com.tunes.SpringTunes.service.SongService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/songs")
public class SongUserController {

    private final SongService songService;
    private final UserRepository userRepository;

    public SongUserController(SongService songService, UserRepository userRepository) {
        this.songService = songService;
        this.userRepository = userRepository;
    }

    // Get songs for current logged-in user
    @GetMapping
    public List<Songs> getMySongs() {
        String username = getCurrentUsername();
        return songService.getSongsByUser(username);
    }

    // Delete song (only if it belongs to the user)
    @DeleteMapping("/{id}")
    public String deleteMySong(@PathVariable Long id) {
        String username = getCurrentUsername();
        boolean deleted = songService.deleteSongForUser(id, username);

        if (deleted) {
            return "Song deleted successfully";
        } else {
            return "Song not found or not authorized";
        }
    }

    // Helper method to get current user
    private String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName(); // Returns username
    }
}