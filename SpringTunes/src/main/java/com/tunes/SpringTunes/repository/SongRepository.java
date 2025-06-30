package com.tunes.SpringTunes.repository;

import com.tunes.SpringTunes.model.Songs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Songs, Long> {
    List<Songs> findByUsername(String username);

    List<Songs> findByNameContainingIgnoreCase(String name);
}