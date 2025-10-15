package com.progweb.socialmusic.comment.service;

import com.progweb.socialmusic.comment.api.DTO.CommentRequestDTO;
import com.progweb.socialmusic.comment.api.DTO.CommentResponseDTO;
import com.progweb.socialmusic.comment.domain.entities.Comment;
import com.progweb.socialmusic.comment.domain.repositories.CommentRepository;
import com.progweb.socialmusic.commentReaction.domain.entities.CommentReaction;
import com.progweb.socialmusic.commentReaction.domain.repositories.CommentReactionRepository;
import com.progweb.socialmusic.exceptions.BusinessRuleException;
import com.progweb.socialmusic.exceptions.ResourceNotFoundException;
import com.progweb.socialmusic.music.domain.entities.Music;
import com.progweb.socialmusic.music.domain.repositories.MusicRepository;
import com.progweb.socialmusic.user.domain.entities.User;
import com.progweb.socialmusic.user.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentReactionRepository reactionRepository;
    private final MusicRepository musicRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponseDTO create(Long musicId, CommentRequestDTO dto, String username) {
        Music music = musicRepository.findById(musicId)
                .orElseThrow(() -> new ResourceNotFoundException("Música não encontrada"));
        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .music(music)
                .author(author)
                .build();

        commentRepository.save(comment);
        return toDTO(comment);
    }

    public Page<CommentResponseDTO> listByMusic(Long musicId, Pageable pageable) {
        return commentRepository.findByMusicId(musicId, pageable)
                .map(this::toDTO);
    }

    @Transactional
    public void deleteComment(Long commentId, String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comentário não encontrado"));

        if (!comment.getAuthor().getUsername().equals(username)) {
            throw new BusinessRuleException("Você não pode deletar o comentário de outro usuário.");
        }

        // deletar reações associadas (se não houver cascade)
        reactionRepository.findAll().stream()
                .filter(r -> r.getComment().getId().equals(commentId))
                .forEach(reactionRepository::delete);

        commentRepository.delete(comment);
    }

    // like/dislike (permanece aqui se você preferir centralizar; se já usa CommentReactionService, pode remover)
    @Transactional
    public void react(Long commentId, String username, boolean liked) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comentário não encontrado"));

        var existing = reactionRepository.findByCommentIdAndUserId(commentId, user.getId());

        CommentReaction reaction = existing.orElseGet(() -> CommentReaction.builder()
                .comment(comment)
                .user(user)
                .build());

        reaction.setLiked(liked);
        reactionRepository.save(reaction);
    }

    // — helpers —
    private CommentResponseDTO toDTO(Comment c) {
        long likesCount = (c.getReactions() == null) ? 0 :
                c.getReactions().stream().filter(CommentReaction::isLiked).count();

        long dislikesCount = (c.getReactions() == null) ? 0 :
                c.getReactions().stream().filter(r -> !r.isLiked()).count();

        return CommentResponseDTO.builder()
                .id(c.getId())
                .content(c.getContent())
                .authorUsername(c.getAuthor().getUsername())
                .createdAt(c.getCreatedAt())
                .musicId(c.getMusic().getId())
                .likesCount(likesCount)
                .dislikesCount(dislikesCount)
                .build();
    }
}
