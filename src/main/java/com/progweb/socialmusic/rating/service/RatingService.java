package com.progweb.socialmusic.rating.service;

import com.progweb.socialmusic.exceptions.ResourceNotFoundException;
import com.progweb.socialmusic.music.domain.entities.Music;
import com.progweb.socialmusic.music.domain.repositories.MusicRepository;
import com.progweb.socialmusic.rating.api.DTO.RatingResponseDTO;
import com.progweb.socialmusic.rating.domain.entities.Rating;
import com.progweb.socialmusic.rating.domain.repositories.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final MusicRepository musicRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public RatingResponseDTO upsertRating(Long musicId, int stars, Long userId) {
        Music music = musicRepository.findById(musicId)
                .orElseThrow(() -> new ResourceNotFoundException("Música não encontrada!"));

        var existing = ratingRepository.findByMusicIdAndUserId(musicId, userId);

        Rating rating = existing.orElseGet(() -> Rating.builder()
                .music(music)
                .user(new com.progweb.socialmusic.user.domain.entities.User(userId))
                .build());

        rating.setStars(stars);
        rating = ratingRepository.save(rating);

        // recalcular média
        double avg = ratingRepository.findAllByMusicId(musicId).stream()
                .mapToInt(Rating::getStars)
                .average()
                .orElse(0.0);

        music.setAvgRating(avg);
        musicRepository.save(music);

        // resposta
        RatingResponseDTO dto = modelMapper.map(rating, RatingResponseDTO.class);
        dto.setMusicId(music.getId());
        dto.setUsername(rating.getUser() != null ? rating.getUser().getUsername() : null);
        return dto;
    }

    public Page<Rating> listByMusic(Long musicId, Pageable pageable) {
        return ratingRepository.findByMusicId(musicId, pageable);
    }
}
