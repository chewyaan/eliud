package io.github.chewyaan.eliud.services;

import io.github.chewyaan.eliud.model.TrainingPlan;
import io.github.chewyaan.eliud.repository.RaceGoalRepository;
import io.github.chewyaan.eliud.repository.TrainingPlanRepository;
import org.springframework.stereotype.Service;

@Service
public class TrainingPlanService {
    private final TrainingPlanRepository trainingPlanRepository;
    private final PlanEngineService planEngineService;
    private final RaceGoalService raceGoalService;

    public TrainingPlanService(TrainingPlanRepository trainingPlanRepository, PlanEngineService planEngineService, RaceGoalService raceGoalService) {
        this.trainingPlanRepository = trainingPlanRepository;
        this.planEngineService = planEngineService;
        this.raceGoalService = raceGoalService;
    }

    public TrainingPlan createTrainingPlan(Long raceGoalId) {
        TrainingPlan trainingPlan = planEngineService.generateInitialPlan(raceGoalService.getRaceGoal(raceGoalId));
        return trainingPlanRepository.save(trainingPlan);
    }
}
