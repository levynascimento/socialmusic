package com.progweb.socialmusic.commentReaction.api.DTO;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor   // ✅ necessário para o ModelMapper
@AllArgsConstructor
public class ReactionRequestDTO {
    private boolean liked;
}
