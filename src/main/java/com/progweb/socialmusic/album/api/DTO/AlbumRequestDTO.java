package com.progweb.socialmusic.album.api.DTO;

import lombok.*;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor   // ✅ necessário para o ModelMapper
@AllArgsConstructor
public class AlbumRequestDTO {
    @NotBlank(message = "Título é obrigatório")
    @Size(max = 120)
    private String title;

    @Size(max = 120)
    private String artist;

    private LocalDate releaseDate;
}
