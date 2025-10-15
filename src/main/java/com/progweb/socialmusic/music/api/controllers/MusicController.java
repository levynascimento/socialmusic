package com.progweb.socialmusic.music.api.controllers;

import com.progweb.socialmusic.music.api.DTO.MusicRequestDTO;
import com.progweb.socialmusic.music.api.DTO.MusicResponseDTO;
import com.progweb.socialmusic.music.domain.entities.Music;
import com.progweb.socialmusic.music.service.MusicService;
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

import java.util.List;

@RestController
@RequestMapping("/music")
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<MusicResponseDTO> createMusic(
            @Valid @RequestBody MusicRequestDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(musicService.createMusic(dto, userDetails.getUsername()));
    }

    @GetMapping
    public ResponseEntity<Page<MusicResponseDTO>> getAllMusic(
            @PageableDefault(size = 10, sort = "title") Pageable pageable) {
        return ResponseEntity.ok(musicService.getAllMusic(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<MusicResponseDTO>> search(
            @RequestParam("q") String q,
            @PageableDefault(size = 10, sort = "title") Pageable pageable) {
        return ResponseEntity.ok(musicService.searchByTitle(q, pageable));
    }

    @GetMapping("/me")
    public ResponseEntity<Page<MusicResponseDTO>> getMyMusic(
            @AuthenticationPrincipal UserDetails userDetails,
            @PageableDefault(size = 10, sort = "title") Pageable pageable) {
        return ResponseEntity.ok(musicService.getByUser(userDetails.getUsername(), pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MusicResponseDTO> getById(@PathVariable Long id) {
        Music music = musicService.findById(id);
        return ResponseEntity.ok(modelMapper.map(music, MusicResponseDTO.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MusicResponseDTO> updateMusic(
            @PathVariable Long id,
            @Valid @RequestBody MusicRequestDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(musicService.updateMusic(id, dto, userDetails.getUsername()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMusic(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        musicService.deleteMusic(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
