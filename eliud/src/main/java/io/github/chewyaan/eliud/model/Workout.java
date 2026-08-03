package io.github.chewyaan.eliud.model;

import jakarta.persistence.*;

@Entity
public class Workout {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private TrainingPlan trainingPlan;
}
