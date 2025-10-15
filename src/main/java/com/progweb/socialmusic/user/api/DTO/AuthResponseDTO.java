package com.progweb.socialmusic.user.api.DTO;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor   // ✅ necessário para o ModelMapper
@AllArgsConstructor
public class AuthResponseDTO {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;

    public AuthResponseDTO(String token, Long id, String username, String email) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
