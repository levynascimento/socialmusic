package com.progweb.socialmusic.rating.api.DTO;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingRequestDTO {

    @Min(value = 0, message = "Stars mínimo é 0")
    @Max(value = 5, message = "Stars máximo é 5")
    private int stars;
}
