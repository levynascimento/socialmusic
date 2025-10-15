package com.progweb.socialmusic.commentReaction.api.DTO;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor   // ✅ necessário para o ModelMapper
@AllArgsConstructor
public class ReactionResponseDTO {
    private Long id;
    private Long commentId;
    private String username;
    private boolean liked;
}
