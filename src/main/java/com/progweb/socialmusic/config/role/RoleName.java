package com.progweb.socialmusic.config.role;

// Define os papéis fixos da aplicação
public enum RoleName {
    // Padrão do Spring Security é usar o prefixo "ROLE_"
    ROLE_USER,
    ROLE_MOD,       // Moderador (pode criar/editar conteúdos, moderar comentários)
    ROLE_ADMIN      // Administrador (acesso total, pode deletar tudo)
}