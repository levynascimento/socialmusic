package com.progweb.socialmusic.user.api.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor   // ✅ necessário para o ModelMapper
@AllArgsConstructor
public class UserLoginDTO {
    @NotBlank(message = "Username/Email não pode estar vazio.")
    private String usernameOrEmail;

    @NotBlank(message = "Senha não pode estar vazia.")
    private String password;
}
