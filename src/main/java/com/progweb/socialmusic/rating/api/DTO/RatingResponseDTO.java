package com.progweb.socialmusic.rating.api.DTO;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingResponseDTO {
    private Long id;
    private int stars;
    private String username;
    private Long musicId;
}
