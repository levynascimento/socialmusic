package com.progweb.socialmusic.comment.api.controllers;

import com.progweb.socialmusic.comment.api.DTO.CommentRequestDTO;
import com.progweb.socialmusic.comment.api.DTO.CommentResponseDTO;
import com.progweb.socialmusic.comment.domain.entities.Comment;
import com.progweb.socialmusic.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{musicId}")
    public ResponseEntity<CommentResponseDTO> createComment(
            @PathVariable Long musicId,
            @Valid @RequestBody CommentRequestDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(commentService.create(musicId, dto, userDetails.getUsername()));
    }

    @GetMapping("/music/{musicId}")
    public ResponseEntity<Page<CommentResponseDTO>> getCommentsByMusic(
            @PathVariable Long musicId,
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable) {
        return ResponseEntity.ok(commentService.listByMusic(musicId, pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        commentService.deleteComment(id, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
