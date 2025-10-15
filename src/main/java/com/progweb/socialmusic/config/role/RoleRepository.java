package com.progweb.socialmusic.config.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    // Método essencial para buscar um papel pelo nome (usado no registro de usuários)
    Optional<Role> findByName(RoleName name);
}
