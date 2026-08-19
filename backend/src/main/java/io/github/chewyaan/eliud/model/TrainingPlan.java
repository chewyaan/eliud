package io.github.chewyaan.eliud.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class TrainingPlan {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int numOfWeeks;

    @OneToMany(mappedBy = "trainingPlan", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<PlannedWorkout> plannedWorkouts;

    public TrainingPlan() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumOfWeeks() {
        return numOfWeeks;
    }

    public void setNumOfWeeks(int numOfWeeks) {
        this.numOfWeeks = numOfWeeks;
    }

    public List<PlannedWorkout> getWorkouts() {
        return plannedWorkouts;
    }

    public void setWorkouts(List<PlannedWorkout> plannedWorkouts) {
        this.plannedWorkouts = plannedWorkouts;
    }

    // Helper function that adds workout to a list and bypasses JPA bidirectional rule that nulls FK if we don't set it at the owner's side
    public void addWorkout(PlannedWorkout plannedWorkout) {
         this.plannedWorkouts.add(plannedWorkout);
         plannedWorkout.setTrainingPlan(this);
    }
}
