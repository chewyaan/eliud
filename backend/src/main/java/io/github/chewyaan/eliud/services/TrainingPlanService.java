package io.github.chewyaan.eliud.services;

import io.github.chewyaan.eliud.model.TrainingPlan;
import io.github.chewyaan.eliud.repository.RaceGoalRepository;
import io.github.chewyaan.eliud.repository.TrainingPlanRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

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
    public TrainingPlan createPlanForGoal(Long raceGoalId) {
        TrainingPlan trainingPlan = planEngineService.generateInitialPlan(
                raceGoalRepository.findById(raceGoalId).orElseThrow(() -> new IllegalArgumentException("Invalid ID Provided"))
        );
        return trainingPlanRepository.save(trainingPlan);
    }
}
