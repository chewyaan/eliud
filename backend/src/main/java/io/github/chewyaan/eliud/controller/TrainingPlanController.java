package io.github.chewyaan.eliud.controller;

import io.github.chewyaan.eliud.dto.TrainingPlanDto;
import io.github.chewyaan.eliud.services.TrainingPlanService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class TrainingPlanController {
    private final TrainingPlanService trainingPlanService;
    public TrainingPlanController(TrainingPlanService trainingPlanService) { this.trainingPlanService = trainingPlanService; }

    @PostMapping("/raceGoals/{raceGoalId}/plan")
    public TrainingPlanDto createPlanForGoal(@PathVariable Long raceGoalId) { return trainingPlanService.createPlanForGoal(raceGoalId); }
}

