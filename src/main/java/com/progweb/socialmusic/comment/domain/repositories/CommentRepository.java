package com.progweb.socialmusic.comment.domain.repositories;

import com.progweb.socialmusic.comment.domain.entities.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByMusicId(Long musicId, Pageable pageable);
}
