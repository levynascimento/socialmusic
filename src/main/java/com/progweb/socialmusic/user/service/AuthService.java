package com.progweb.socialmusic.user.service;

import com.progweb.socialmusic.exceptions.BusinessRuleException;
import com.progweb.socialmusic.exceptions.ResourceNotFoundException;
import com.progweb.socialmusic.security.jwt.JwtUtils;
import com.progweb.socialmusic.user.api.DTO.UserLoginDTO;
import com.progweb.socialmusic.user.api.DTO.UserRegisterDTO;
import com.progweb.socialmusic.user.domain.entities.User;
import com.progweb.socialmusic.user.domain.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final ModelMapper modelMapper;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.modelMapper = modelMapper;
    }

    public User registerNewUser(UserRegisterDTO request) {
        if(userRepository.existsByUsername(request.getUsername())){
            throw new BusinessRuleException("Nome do usuário já está em uso!");
        }
        if(userRepository.existsByEmail(request.getEmail())){
            throw new BusinessRuleException("Email já está em uso!");
        }

        // 2b. Mapeia o DTO para a Entidade User
        User user = modelMapper.map(request, User.class);

        // 2c. Codifica a senha
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // --- 3. Salva o Usuário ---
        return userRepository.save(user);
    }

    public String authenticateUserAndGenerateToken(UserLoginDTO request) { // Mudei o nome e o retorno

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsernameOrEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Gera o Token JWT e o retorna
        return jwtUtils.generateJwtToken(authentication);
    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Erro: Usuário não encontrado após a autenticação."));
    }

    // Exemplo no UserService/AuthService
    public Long findUserIdByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(User::getId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário logado não encontrado no banco de dados."));
    }

    public String getLoggedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ResourceNotFoundException("Nenhum usuário autenticado no momento.");
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        } else {
            return principal.toString();
        }
    }
}
