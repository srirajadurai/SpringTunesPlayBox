package com.tunes.SpringTunes.service;

import com.tunes.SpringTunes.model.Songs;
import com.tunes.SpringTunes.repository.SongRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongService {

    private final SongRepository songRepository;

    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public void saveSong(String username, String name, String location) {
        Songs song = new Songs();
        song.setUsername(username);
        song.setName(name);
        song.setLocation(location);
        songRepository.save(song);
    }

    public void deleteSong(Long id) {
        if (!songRepository.existsById(id)) {
            throw new RuntimeException("Song not found with ID: " + id);
        }
        songRepository.deleteById(id);
    }

    public List<Songs> getAllSongs(){
        return songRepository.findAll();
    }

    public List<Songs> searchByName(String name){
        return songRepository.findByNameContainingIgnoreCase(name);
    }

    public Optional<Songs> getSongById(Long id) {
        return songRepository.findById(id);
    }

    public List<Songs> getSongsByUser(String username) {
        return songRepository.findByUsername(username);
    }

    public boolean deleteSongForUser(Long id, String username) {
        Optional<Songs> optionalSong = songRepository.findById(id);
        if (optionalSong.isPresent()) {
            Songs song = optionalSong.get();
            if (song.getUsername().equals(username)) {
                songRepository.deleteById(id);
                return true;
            }
        }
        return false;
    }
}