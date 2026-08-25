package io.github.chewyaan.eliud.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
public class TrainingPlan {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "trainingPlan", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<PlannedWorkout> plannedWorkouts;

    @ManyToOne(fetch = FetchType.LAZY)
    private TrainingPlan previousVersion;

    @ManyToOne
    private RaceGoal raceGoal;

    private int numOfWeeks;
    private int versionNumber;

    public TrainingPlan() {
        this.plannedWorkouts = new ArrayList<>();
    }

    public TrainingPlan(TrainingPlan previousVersion, RaceGoal raceGoal, int numOfWeeks, int versionNumber) {
        this();
        this.previousVersion = previousVersion;
        this.raceGoal = raceGoal;
        this.numOfWeeks = numOfWeeks;
        this.versionNumber = versionNumber;
    }

    public Long getId() {
        return id;
    }

    public int getNumOfWeeks() {
        return numOfWeeks;
    }

    public List<PlannedWorkout> getWorkouts() {
        return Collections.unmodifiableList(plannedWorkouts);
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    // Helper function that adds workout to a list and bypasses JPA bidirectional rule that nulls FK if we don't set it at the owner's side
    public void addWorkout(PlannedWorkout plannedWorkout) {
         this.plannedWorkouts.add(plannedWorkout);
         plannedWorkout.setTrainingPlan(this);
    }
}
