package io.github.chewyaan.eliud.services;

import io.github.chewyaan.eliud.dto.PlannedWorkoutDto;
import io.github.chewyaan.eliud.dto.TrainingPlanDto;
import io.github.chewyaan.eliud.model.PlannedWorkout;
import io.github.chewyaan.eliud.model.TrainingPlan;
import io.github.chewyaan.eliud.repository.RaceGoalRepository;
import io.github.chewyaan.eliud.repository.TrainingPlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainingPlanService {
    private final TrainingPlanRepository trainingPlanRepository;
    private final PlanEngineService planEngineService;
    private final RaceGoalRepository raceGoalRepository;

    public TrainingPlanService(TrainingPlanRepository trainingPlanRepository, PlanEngineService planEngineService, RaceGoalRepository raceGoalRepository) {
        this.trainingPlanRepository = trainingPlanRepository;
        this.planEngineService = planEngineService;
        this.raceGoalRepository = raceGoalRepository;
    }

    @Transactional
    public TrainingPlanDto createPlanForGoal(Long raceGoalId) {
        TrainingPlan trainingPlan = planEngineService.generateInitialPlan(
                raceGoalRepository.findById(raceGoalId).orElseThrow(() -> new IllegalArgumentException("Invalid ID Provided"))
        );

        // Needs to save to repo first - so that the training plan id is properly generated for the .getId() call in toDto()
        trainingPlanRepository.save(trainingPlan);

        return toDto(trainingPlan);
    }

    private TrainingPlanDto toDto(TrainingPlan trainingPlan) {

        List<PlannedWorkoutDto> plannedWorkoutDtos = new ArrayList<>();
        for (PlannedWorkout workout : trainingPlan.getWorkouts()) {
            plannedWorkoutDtos.add(new PlannedWorkoutDto(workout.getDistance(), workout.getDate(), workout.getWorkoutType()));
        }

        return new TrainingPlanDto(
                trainingPlan.getId(),
                null,
                null,
                plannedWorkoutDtos,
                trainingPlan.getNumOfWeeks(),
                trainingPlan.getVersionNumber()
        );
    }
}
