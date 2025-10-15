package com.progweb.socialmusic.config.role;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Garante que o nome do papel (ROLE_USER, ROLE_ADMIN) seja persistido como String
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false, unique = true)
    private RoleName name;

    // ATENÇÃO: Método obrigatório da interface GrantedAuthority.
    // O Spring Security usa isso para verificar permissões.
    @Override
    public String getAuthority() {
        return this.name.name();
    }
}