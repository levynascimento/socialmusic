package com.progweb.socialmusic.music.api.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MusicRequestDTO {
    @NotBlank(message = "Título é obrigatório")
    @Size(max = 120, message = "Título deve ter no máximo 120 caracteres")
    private String title;

    @Size(max = 120, message = "Artista deve ter no máximo 120 caracteres")
    private String artist;

    @Size(max = 120, message = "Álbum deve ter no máximo 120 caracteres")
    private String album;

    @Size(max = 60, message = "Gênero deve ter no máximo 60 caracteres")
    private String genre;
}
