package com.progweb.socialmusic.playlist.api.controllers;

import com.progweb.socialmusic.playlist.api.DTO.PlaylistRequestDTO;
import com.progweb.socialmusic.playlist.api.DTO.PlaylistResponseDTO;
import com.progweb.socialmusic.playlist.domain.entities.Playlist;
import com.progweb.socialmusic.playlist.service.PlaylistService;
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

import java.util.Set;

@RestController
@RequestMapping("/playlists")
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistService playlistService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<PlaylistResponseDTO> createPlaylist(
            @Valid @RequestBody PlaylistRequestDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(playlistService.createPlaylist(dto, userDetails.getUsername()));
    }

    @GetMapping("/me")
    public ResponseEntity<Page<PlaylistResponseDTO>> getUserPlaylists(
            @AuthenticationPrincipal UserDetails userDetails,
            @PageableDefault(size = 10, sort = "title") Pageable pageable) {
        return ResponseEntity.ok(playlistService.listUserPlaylists(userDetails.getUsername(), pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<PlaylistResponseDTO>> search(
            @RequestParam("q") String q,
            @PageableDefault(size = 10, sort = "title") Pageable pageable) {
        return ResponseEntity.ok(playlistService.searchByTitle(q, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistResponseDTO> getById(@PathVariable Long id) {
        Playlist playlist = playlistService.findById(id);
        return ResponseEntity.ok(modelMapper.map(playlist, PlaylistResponseDTO.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlaylistResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody PlaylistRequestDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(playlistService.update(id, dto, userDetails.getUsername()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        playlistService.delete(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
