package io.github.chewyaan.eliud.model;

import jakarta.persistence.*;

@Entity
public class RaceGoal {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;
}
