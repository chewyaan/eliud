package io.github.chewyaan.eliud.controller;

import io.github.chewyaan.eliud.model.TrainingPlan;
import io.github.chewyaan.eliud.services.TrainingPlanService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trainingPlans")
@CrossOrigin(origins = "http://localhost:5173")
public class TrainingPlanController {
    private final TrainingPlanService trainingPlanService;
    public TrainingPlanController(TrainingPlanService trainingPlanService) { this.trainingPlanService = trainingPlanService; }

    @PostMapping
    public TrainingPlan createTrainingPlan(@RequestBody Long raceGoalId) { return trainingPlanService.createTrainingPlan(raceGoalId); }
}

