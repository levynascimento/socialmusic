package com.progweb.socialmusic.user.api.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor   // ✅ necessário para o ModelMapper
@AllArgsConstructor
public class UserUpdateDTO {

    @NotBlank(message = "O nome de usuário é obrigatório.")
    @Size(min = 3, max = 30, message = "O nome de usuário deve ter entre 3 e 30 caracteres.")
    private String username;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "E-mail inválido.")
    private String email;

    @Size(min = 6, max = 100, message = "A senha deve ter no mínimo 6 caracteres.")
    private String password;
}
