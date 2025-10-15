package com.progweb.socialmusic.rating.domain.repositories;

import com.progweb.socialmusic.rating.domain.entities.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Optional<Rating> findByMusicIdAndUserId(Long musicId, Long userId);

    List<Rating> findAllByMusicId(Long musicId);

    Page<Rating> findByMusicId(Long musicId, Pageable pageable);
}
