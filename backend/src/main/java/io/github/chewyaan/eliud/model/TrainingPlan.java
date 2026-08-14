package io.github.chewyaan.eliud.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class TrainingPlan {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;


    //    Temp. limiting fetch/cascade interaction

    @OneToMany(mappedBy = "trainingPlan", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Workout> workouts;
}
