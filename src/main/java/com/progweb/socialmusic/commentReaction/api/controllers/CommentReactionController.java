package com.progweb.socialmusic.commentReaction.api.controllers;

import com.progweb.socialmusic.commentReaction.api.DTO.ReactionRequestDTO;
import com.progweb.socialmusic.commentReaction.api.DTO.ReactionResponseDTO;
import com.progweb.socialmusic.commentReaction.service.CommentReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentReactionController {

    private final CommentReactionService commentReactionService;

    @PostMapping("/{commentId}/react")
    public ResponseEntity<ReactionResponseDTO> reactToComment(
            @PathVariable Long commentId,
            @RequestBody ReactionRequestDTO dto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        ReactionResponseDTO response = commentReactionService.react(commentId, dto, userDetails.getUsername());
        return ResponseEntity.ok(response);
    }
}
