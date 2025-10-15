package com.progweb.socialmusic.config.role;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class RoleSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // Este método é executado automaticamente assim que a aplicação sobe
    @Override
    public void run(String... args) throws Exception {
        // Lista de todas as roles que a aplicação suporta
        List<RoleName> rolesToCreate = Arrays.asList(
                RoleName.ROLE_USER,
                RoleName.ROLE_MOD,
                RoleName.ROLE_ADMIN
        );

        rolesToCreate.forEach(roleName -> {
            // Verifica se a role já existe no banco
            if (roleRepository.findByName(roleName).isEmpty()) {
                // Se não existe, cria e salva
                Role newRole = new Role();
                newRole.setName(roleName);
                roleRepository.save(newRole);
                System.out.println("Role criada: " + roleName.name());
            }
        });
    }
}
