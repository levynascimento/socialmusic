package com.progweb.socialmusic.commentReaction.domain.entities;

import com.progweb.socialmusic.comment.domain.entities.Comment;
import com.progweb.socialmusic.user.domain.entities.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comment_reaction")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CommentReaction {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private boolean liked;
}

