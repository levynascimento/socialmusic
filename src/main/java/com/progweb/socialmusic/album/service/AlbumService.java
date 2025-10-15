package com.progweb.socialmusic.album.service;

import com.progweb.socialmusic.album.api.DTO.AlbumRequestDTO;
import com.progweb.socialmusic.album.api.DTO.AlbumResponseDTO;
import com.progweb.socialmusic.album.domain.entities.Album;
import com.progweb.socialmusic.album.domain.repositories.AlbumRepository;
import com.progweb.socialmusic.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final ModelMapper modelMapper;

    // ✅ Criar novo álbum
    public AlbumResponseDTO create(AlbumRequestDTO dto) {
        Album album = Album.builder()
                .title(dto.getTitle())
                .artist(dto.getArtist())
                .releaseDate(dto.getReleaseDate())
                .build();

        albumRepository.save(album);
        return modelMapper.map(album, AlbumResponseDTO.class);
    }

    // ✅ Listar todos os álbuns (paginado)
    public Page<AlbumResponseDTO> listAll(Pageable pageable) {
        return albumRepository.findAll(pageable)
                .map(album -> modelMapper.map(album, AlbumResponseDTO.class));
    }

    // ✅ Buscar por título
    public Page<AlbumResponseDTO> searchByTitle(String q, Pageable pageable) {
        return albumRepository.findByTitleContainingIgnoreCase(q, pageable)
                .map(album -> modelMapper.map(album, AlbumResponseDTO.class));
    }

    // ✅ Buscar por ID
    public Album findById(Long id) {
        return albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Álbum não encontrado"));
    }

    // ✅ Atualizar álbum
    @Transactional
    public AlbumResponseDTO update(Long id, AlbumRequestDTO dto) {
        Album album = albumRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Álbum não encontrado"));

        album.setTitle(dto.getTitle());
        album.setArtist(dto.getArtist());
        album.setReleaseDate(dto.getReleaseDate());

        albumRepository.save(album);
        return modelMapper.map(album, AlbumResponseDTO.class);
    }

    // ✅ Deletar álbum
    @Transactional
    public void delete(Long id) {
        if (!albumRepository.existsById(id)) {
            throw new ResourceNotFoundException("Álbum não encontrado");
        }
        albumRepository.deleteById(id);
    }
}
