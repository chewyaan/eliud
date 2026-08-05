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

    //    Temp. limiting fetch/cascade interaction

//    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    private List<RaceGoal> raceGoals;

    //    Temp. limiting fetch/cascade interaction

//    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    private List<DailyLog> dailyLogs;
//    Temp. limiting fetch/cascade interaction

//    @OneToOne
//    private TrainingPlan trainingPlan;
}
