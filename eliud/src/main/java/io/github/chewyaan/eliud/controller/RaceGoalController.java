package io.github.chewyaan.eliud.controller;

import io.github.chewyaan.eliud.model.RaceGoal;
import io.github.chewyaan.eliud.services.RaceGoalService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/raceGoals")
public class RaceGoalController {

    private final RaceGoalService raceGoalService;

    public RaceGoalController(RaceGoalService raceGoalService) {
        this.raceGoalService = raceGoalService;
    }

    @PostMapping
    public RaceGoal createRaceGoal(@RequestBody RaceGoal raceGoal) {
        return raceGoalService.createRaceGoal(raceGoal);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RaceGoal> getRaceGoal(@PathVariable Long id) {
        return raceGoalService.getRaceGoal(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
