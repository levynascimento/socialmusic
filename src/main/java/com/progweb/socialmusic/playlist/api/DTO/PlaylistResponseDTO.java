package com.progweb.socialmusic.playlist.api.DTO;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor   // ✅ necessário para o ModelMapper
@AllArgsConstructor
public class PlaylistResponseDTO {
    private Long id;
    private String title;
    private String ownerUsername;
    private Set<String> musicTitles;
}
