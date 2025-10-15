package com.progweb.socialmusic.music.service;

import com.progweb.socialmusic.exceptions.BusinessRuleException;
import com.progweb.socialmusic.exceptions.ResourceNotFoundException;
import com.progweb.socialmusic.music.api.DTO.MusicRequestDTO;
import com.progweb.socialmusic.music.api.DTO.MusicResponseDTO;
import com.progweb.socialmusic.music.domain.entities.Music;
import com.progweb.socialmusic.music.domain.repositories.MusicRepository;
import com.progweb.socialmusic.user.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MusicService {

    private final MusicRepository musicRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public Music findById(Long id) {
        return musicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Música não encontrada"));
    }

    @Transactional
    public MusicResponseDTO createMusic(MusicRequestDTO dto, String username) {
        var owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        var music = Music.builder()
                .title(dto.getTitle())
                .artist(dto.getArtist())
                .album(dto.getAlbum())
                .genre(dto.getGenre())
                .owner(owner)
                .avgRating(0.0)
                .build();

        musicRepository.save(music);
        return modelMapper.map(music, MusicResponseDTO.class);
    }

    public Page<MusicResponseDTO> getAllMusic(Pageable pageable) {
        return musicRepository.findAll(pageable)
                .map(m -> modelMapper.map(m, MusicResponseDTO.class));
    }

    public Page<MusicResponseDTO> searchByTitle(String q, Pageable pageable) {
        return musicRepository.findByTitleContainingIgnoreCase(q, pageable)
                .map(m -> modelMapper.map(m, MusicResponseDTO.class));
    }

    public Page<MusicResponseDTO> getByUser(String username, Pageable pageable) {
        return musicRepository.findByOwnerUsername(username, pageable)
                .map(m -> modelMapper.map(m, MusicResponseDTO.class));
    }

    @Transactional
    public MusicResponseDTO updateMusic(Long id, MusicRequestDTO dto, String username) {
        var music = musicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Música não encontrada"));

        if (!music.getOwner().getUsername().equals(username)) {
            throw new BusinessRuleException("Você não tem permissão para editar esta música.");
        }

        music.setTitle(dto.getTitle());
        music.setArtist(dto.getArtist());
        music.setAlbum(dto.getAlbum());
        music.setGenre(dto.getGenre());

        musicRepository.save(music);
        return modelMapper.map(music, MusicResponseDTO.class);
    }

    @Transactional
    public void deleteMusic(Long id, String username) {
        var music = musicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Música não encontrada"));

        if (!music.getOwner().getUsername().equals(username)) {
            throw new BusinessRuleException("Você não tem permissão para deletar esta música.");
        }

        musicRepository.delete(music);
    }
}
