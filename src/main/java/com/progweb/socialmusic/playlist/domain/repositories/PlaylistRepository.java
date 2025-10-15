package com.progweb.socialmusic.playlist.domain.repositories;

import com.progweb.socialmusic.playlist.domain.entities.Playlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    Page<Playlist> findByOwnerUsername(String username, Pageable pageable);
    Page<Playlist> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
