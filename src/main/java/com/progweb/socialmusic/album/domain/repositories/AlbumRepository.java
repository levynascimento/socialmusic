package com.progweb.socialmusic.album.domain.repositories;

import com.progweb.socialmusic.album.domain.entities.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    Page<Album> findByTitleContainingIgnoreCase(String title, Pageable pageable);
}
