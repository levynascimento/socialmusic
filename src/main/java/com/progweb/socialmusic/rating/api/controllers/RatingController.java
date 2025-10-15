package com.progweb.socialmusic.rating.api.controllers;

import com.progweb.socialmusic.rating.api.DTO.RatingRequestDTO;
import com.progweb.socialmusic.rating.api.DTO.RatingResponseDTO;
import com.progweb.socialmusic.rating.service.RatingService;
import com.progweb.socialmusic.user.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;
    private final AuthService authService;
    private final ModelMapper modelMapper;

    @PostMapping("/{musicId}")
    public ResponseEntity<RatingResponseDTO> rateMusic(
            @PathVariable Long musicId,
            @Valid @RequestBody RatingRequestDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) {

        Long userId = authService.findUserIdByUsername(userDetails.getUsername());
        RatingResponseDTO response = ratingService.upsertRating(musicId, dto.getStars(), userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/music/{musicId}")
    public ResponseEntity<Page<RatingResponseDTO>> listRatingsByMusic(
            @PathVariable Long musicId,
            @PageableDefault(size = 10) Pageable pageable) {
        Page<RatingResponseDTO> page = ratingService.listByMusic(musicId, pageable)
                .map(r -> modelMapper.map(r, RatingResponseDTO.class));
        return ResponseEntity.ok(page);
    }
}
