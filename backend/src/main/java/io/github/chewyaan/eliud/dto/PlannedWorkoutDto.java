package io.github.chewyaan.eliud.dto;

import io.github.chewyaan.eliud.model.WorkoutType;

import java.time.LocalDate;

public record PlannedWorkoutDto(Double distance, LocalDate date, WorkoutType workoutType) {
}
