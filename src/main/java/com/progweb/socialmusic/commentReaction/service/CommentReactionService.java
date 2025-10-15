package com.progweb.socialmusic.commentReaction.service;

import com.progweb.socialmusic.comment.domain.entities.Comment;
import com.progweb.socialmusic.comment.domain.repositories.CommentRepository;
import com.progweb.socialmusic.commentReaction.api.DTO.ReactionRequestDTO;
import com.progweb.socialmusic.commentReaction.api.DTO.ReactionResponseDTO;
import com.progweb.socialmusic.commentReaction.domain.entities.CommentReaction;
import com.progweb.socialmusic.commentReaction.domain.repositories.CommentReactionRepository;
import com.progweb.socialmusic.user.domain.entities.User;
import com.progweb.socialmusic.user.domain.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentReactionService {

    private final CommentRepository commentRepository;
    private final CommentReactionRepository reactionRepository;
    private final UserRepository userRepository;

    public ReactionResponseDTO react(Long commentId, ReactionRequestDTO dto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("Comentário não encontrado"));

        var existing = reactionRepository.findByCommentIdAndUserId(commentId, user.getId());

        CommentReaction reaction = existing.orElseGet(() ->
                CommentReaction.builder()
                        .comment(comment)
                        .user(user)
                        .build()
        );

        reaction.setLiked(dto.isLiked());
        reaction = reactionRepository.save(reaction);

        return ReactionResponseDTO.builder()
                .id(reaction.getId())
                .commentId(commentId)
                .username(username)
                .liked(reaction.isLiked())
                .build();
    }
}
