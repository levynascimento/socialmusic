package com.progweb.socialmusic.config;

import com.progweb.socialmusic.music.api.DTO.MusicResponseDTO;
import com.progweb.socialmusic.music.domain.entities.Music;
import com.progweb.socialmusic.playlist.api.DTO.PlaylistResponseDTO;
import com.progweb.socialmusic.playlist.domain.entities.Playlist;
import com.progweb.socialmusic.rating.api.DTO.RatingResponseDTO;
import com.progweb.socialmusic.rating.domain.entities.Rating;
import com.progweb.socialmusic.user.api.DTO.UserResponseDTO;
import com.progweb.socialmusic.user.domain.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper(); // ✅ só essa variável

        // ⚙️ Configurações padrão
        modelMapper.getConfiguration()
                .setSkipNullEnabled(true)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        // 👤 User → UserResponseDTO
        modelMapper.typeMap(User.class, UserResponseDTO.class);

        // 🎵 Music → MusicResponseDTO
        modelMapper.typeMap(Music.class, MusicResponseDTO.class)
                .addMappings(map -> map.map(src -> src.getOwner().getUsername(), MusicResponseDTO::setOwnerUsername));

        // 🎧 Playlist → PlaylistResponseDTO (corrigido e estável)
        modelMapper.typeMap(Playlist.class, PlaylistResponseDTO.class)
                .addMappings(map -> {
                    map.map(src -> src.getOwner().getUsername(), PlaylistResponseDTO::setOwnerUsername);
                    map.using(ctx -> {
                        Set<Music> musics = (Set<Music>) ctx.getSource();
                        if (musics == null) return Set.of();
                        return musics.stream()
                                .map(Music::getTitle)
                                .collect(Collectors.toSet());
                    }).map(Playlist::getMusics, PlaylistResponseDTO::setMusicTitles);
                });

        // ⭐ Rating → RatingResponseDTO
        modelMapper.typeMap(Rating.class, RatingResponseDTO.class)
                .addMappings(map -> {
                    map.map(src -> src.getUser().getUsername(), RatingResponseDTO::setUsername);
                    map.map(src -> src.getMusic().getId(), RatingResponseDTO::setMusicId);
                });

        return modelMapper; // ✅ devolve o único ModelMapper criado
    }
}
