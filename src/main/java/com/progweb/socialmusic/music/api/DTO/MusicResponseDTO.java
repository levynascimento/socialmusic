package com.progweb.socialmusic.music.api.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor   // ✅ necessário para o ModelMapper
@AllArgsConstructor
public class MusicResponseDTO {
    private Long id;
    private String title;
    private String artist;
    private String album;
    private String genre;
    private Double avgRating;
    private String ownerUsername;
}
