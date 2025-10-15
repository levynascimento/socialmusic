package com.progweb.socialmusic.user.api.controllers;

import com.progweb.socialmusic.user.api.DTO.UserResponseDTO;
import com.progweb.socialmusic.user.api.DTO.UserUpdateDTO;
import com.progweb.socialmusic.user.domain.entities.User;
import com.progweb.socialmusic.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAll() {
        List<UserResponseDTO> users = userService.findAll().stream()
                .map(user -> modelMapper.map(user, UserResponseDTO.class))
                .toList();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getById(@PathVariable Long id) {
        User user = userService.findById(id);
        UserResponseDTO dto = modelMapper.map(user, UserResponseDTO.class);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(
            @PathVariable Long id,
            @Valid @RequestBody UserUpdateDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) {

        // opcional: só deixa o próprio usuário editar
        if (!userDetails.getUsername().equals(userService.findById(id).getUsername())) {
            return ResponseEntity.status(403).build();
        }

        return ResponseEntity.ok(userService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {

        // opcional: só deixa o próprio usuário deletar
        if (!userDetails.getUsername().equals(userService.findById(id).getUsername())) {
            return ResponseEntity.status(403).build();
        }

        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
