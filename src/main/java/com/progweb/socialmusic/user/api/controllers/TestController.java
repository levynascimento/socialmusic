package com.progweb.socialmusic.user.api.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    // Este endpoint NÃO está liberado no SecurityConfig, exigindo autenticação
    @GetMapping("/user")
    // Você pode usar @PreAuthorize para checar roles (papéis), mas o '.authenticated()' no SecurityConfig já fará a checagem básica.
    public String userAccess() {
        // Exemplo de como obter o nome de usuário autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return "Conteúdo Acessível. Olá, " + username + "! Seu JWT está funcionando.";
    }

    // Apenas para mostrar um endpoint que DEVE ser público (embora o AuthController já faça isso)
    @GetMapping("/public")
    public String publicAccess() {
        return "Conteúdo público. Nenhuma autenticação necessária.";
    }
}
