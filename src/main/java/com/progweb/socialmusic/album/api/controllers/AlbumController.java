package com.progweb.socialmusic.album.api.controllers;

import com.progweb.socialmusic.album.api.DTO.AlbumRequestDTO;
import com.progweb.socialmusic.album.api.DTO.AlbumResponseDTO;
import com.progweb.socialmusic.album.domain.entities.Album;
import com.progweb.socialmusic.album.service.AlbumService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/albums")
@RequiredArgsConstructor
public class AlbumController {

    private final AlbumService albumService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<AlbumResponseDTO> create(@Valid @RequestBody AlbumRequestDTO dto) {
        return ResponseEntity.ok(albumService.create(dto));
    }

    @GetMapping
    public ResponseEntity<Page<AlbumResponseDTO>> list(@PageableDefault(size = 10, sort = "title") Pageable pageable) {
        return ResponseEntity.ok(albumService.listAll(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<AlbumResponseDTO>> search(@RequestParam("q") String q,
                                                         @PageableDefault(size = 10, sort = "title") Pageable pageable) {
        return ResponseEntity.ok(albumService.searchByTitle(q, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlbumResponseDTO> getById(@PathVariable Long id) {
        Album album = albumService.findById(id);
        return ResponseEntity.ok(modelMapper.map(album, AlbumResponseDTO.class));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlbumResponseDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody AlbumRequestDTO dto) {
        return ResponseEntity.ok(albumService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        albumService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
