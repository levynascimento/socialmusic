package com.progweb.socialmusic.comment.api.DTO;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor   // ✅ necessário para o ModelMapper
@AllArgsConstructor
public class CommentResponseDTO {
    private Long id;
    private String content;
    private String authorUsername;
    private LocalDateTime createdAt;
    private Long musicId;
    private long likesCount;
    private long dislikesCount;
}
