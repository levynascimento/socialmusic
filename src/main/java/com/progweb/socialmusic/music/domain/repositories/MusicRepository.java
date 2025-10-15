package com.progweb.socialmusic.music.domain.repositories;

import com.progweb.socialmusic.music.domain.entities.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepository extends JpaRepository<Music, Long> {
    Page<Music> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Music> findByOwnerUsername(String username, Pageable pageable);
}

