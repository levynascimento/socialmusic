package com.progweb.socialmusic.album.api.DTO;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlbumResponseDTO {
    private Long id;
    private String title;
    private String artist;
    private LocalDate releaseDate;
}
