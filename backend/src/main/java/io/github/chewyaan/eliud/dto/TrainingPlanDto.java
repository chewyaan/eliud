package io.github.chewyaan.eliud.dto;

import java.util.List;

public record TrainingPlanDto(Long currentId, Long prevId, Long raceGoalId, List<PlannedWorkoutDto> plannedWorkouts, int numOfWeeks, int versionNumber) {}
