package io.github.chewyaan.eliud.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class User {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private int age;
    private Double height;
    private Double weight;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<RaceGoal> raceGoals;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    private List<DailyLog> dailyLogs;

    @OneToOne
    private TrainingPlan trainingPlan;
}
