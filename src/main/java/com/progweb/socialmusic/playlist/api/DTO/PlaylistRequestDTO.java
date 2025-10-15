package com.progweb.socialmusic.playlist.api.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor   // ✅ necessário para o ModelMapper
@AllArgsConstructor
public class PlaylistRequestDTO {
    @NotBlank(message = "Título é obrigatório")
    @Size(max = 120)
    private String title;

    private Set<Long> musicIds;
}
