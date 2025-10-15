package com.progweb.socialmusic.commentReaction.domain.repositories;

import com.progweb.socialmusic.commentReaction.domain.entities.CommentReaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentReactionRepository extends JpaRepository<CommentReaction, Long> {
    Optional<CommentReaction> findByCommentIdAndUserId(Long commentId, Long userId);
}
