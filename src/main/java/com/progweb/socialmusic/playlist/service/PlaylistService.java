package com.progweb.socialmusic.playlist.service;

import com.progweb.socialmusic.exceptions.BusinessRuleException;
import com.progweb.socialmusic.exceptions.ResourceNotFoundException;
import com.progweb.socialmusic.music.domain.entities.Music;
import com.progweb.socialmusic.music.domain.repositories.MusicRepository;
import com.progweb.socialmusic.playlist.api.DTO.PlaylistRequestDTO;
import com.progweb.socialmusic.playlist.api.DTO.PlaylistResponseDTO;
import com.progweb.socialmusic.playlist.domain.entities.Playlist;
import com.progweb.socialmusic.playlist.domain.repositories.PlaylistRepository;
import com.progweb.socialmusic.user.domain.entities.User;
import com.progweb.socialmusic.user.domain.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistRepository playlistRepository;
    private final MusicRepository musicRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    // ✅ Criar playlist
    public PlaylistResponseDTO createPlaylist(PlaylistRequestDTO dto, String username) {
        User owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        var musics = new HashSet<>(musicRepository.findAllById(dto.getMusicIds()));

        Playlist playlist = Playlist.builder()
                .title(dto.getTitle())
                .owner(owner)
                .musics(musics)
                .build();

        playlistRepository.save(playlist);
        return modelMapper.map(playlist, PlaylistResponseDTO.class);
    }

    // ✅ Listar playlists do usuário logado
    public Page<PlaylistResponseDTO> listUserPlaylists(String username, Pageable pageable) {
        return playlistRepository.findByOwnerUsername(username, pageable)
                .map(playlist -> modelMapper.map(playlist, PlaylistResponseDTO.class));
    }

    // ✅ Buscar por título
    public Page<PlaylistResponseDTO> searchByTitle(String q, Pageable pageable) {
        return playlistRepository.findByTitleContainingIgnoreCase(q, pageable)
                .map(playlist -> modelMapper.map(playlist, PlaylistResponseDTO.class));
    }

    // ✅ Buscar por ID
    public Playlist findById(Long id) {
        return playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist não encontrada"));
    }

    // ✅ Atualizar playlist
    @Transactional
    public PlaylistResponseDTO update(Long id, PlaylistRequestDTO dto, String username) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist não encontrada"));

        if (!playlist.getOwner().getUsername().equals(username)) {
            throw new BusinessRuleException("Você não tem permissão para editar esta playlist");
        }

        playlist.setTitle(dto.getTitle());

        if (dto.getMusicIds() != null) {
            var musics = musicRepository.findAllById(dto.getMusicIds());
            playlist.setMusics(new HashSet<>(musics));
        }

        playlistRepository.save(playlist);
        return modelMapper.map(playlist, PlaylistResponseDTO.class);
    }

    // ✅ Deletar playlist
    @Transactional
    public void delete(Long id, String username) {
        Playlist playlist = playlistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Playlist não encontrada"));

        if (!playlist.getOwner().getUsername().equals(username)) {
            throw new BusinessRuleException("Você não tem permissão para deletar esta playlist");
        }

        playlistRepository.delete(playlist);
    }
}
