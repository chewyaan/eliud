package io.github.chewyaan.eliud.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class PlannedWorkout {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double distance;
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private WorkoutType workoutType;

    @ManyToOne(fetch = FetchType.LAZY)
    private TrainingPlan trainingPlan;

    public PlannedWorkout() {}
    public PlannedWorkout(Double distance, LocalDate date, WorkoutType workoutType) {
        this.distance = distance;
        this.date = date;
        this.workoutType = workoutType;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public WorkoutType getWorkoutType() {
        return workoutType;
    }

    public void setWorkoutType(WorkoutType workoutType) {
        this.workoutType = workoutType;
    }

    public TrainingPlan getTrainingPlan() {
        return trainingPlan;
    }

    public void setTrainingPlan(TrainingPlan trainingPlan) {
        this.trainingPlan = trainingPlan;
    }
}
