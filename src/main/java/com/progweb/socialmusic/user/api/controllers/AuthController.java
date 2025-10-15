package com.progweb.socialmusic.user.api.controllers;


import com.progweb.socialmusic.user.api.DTO.AuthResponseDTO;
import com.progweb.socialmusic.user.api.DTO.UserLoginDTO;
import com.progweb.socialmusic.user.api.DTO.UserRegisterDTO;
import com.progweb.socialmusic.user.domain.entities.User;
import com.progweb.socialmusic.user.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRegisterDTO registerRequest) {
        try{
            authService.registerNewUser(registerRequest);
            return new ResponseEntity<>("Usuário registrado com sucesso!", HttpStatus.CREATED);
        } catch (IllegalStateException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserLoginDTO loginRequest) {
        String jwt = authService.authenticateUserAndGenerateToken(loginRequest);

        // Após a autenticação, podemos obter os detalhes do usuário
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // NOVO: Você precisará carregar o User do banco para pegar o ID e Email
        // Se a sua classe UserDetailsServiceImpl retornar o seu User completo (não apenas o User do Spring Security),
        // esse passo é mais simples. Vou assumir que você tem que buscar o User original:
        User user = authService.findUserByUsername(userDetails.getUsername()); // NOVO MÉTODO no AuthService

        return new ResponseEntity<>(
                new AuthResponseDTO(
                        jwt,
                        user.getId(),
                        user.getUsername(),
                        user.getEmail()
                ),
                HttpStatus.OK
        );
    }



}
