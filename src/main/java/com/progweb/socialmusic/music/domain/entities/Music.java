package com.progweb.socialmusic.music.domain.entities;

import com.progweb.socialmusic.rating.domain.entities.Rating;
import com.progweb.socialmusic.user.domain.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "music")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String artist;

    private String album;

    private String genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User owner; // usuário que adicionou a música

    @OneToMany(mappedBy = "music", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings;

    @Column(name = "avg_rating")
    private Double avgRating = 0.0;
}

