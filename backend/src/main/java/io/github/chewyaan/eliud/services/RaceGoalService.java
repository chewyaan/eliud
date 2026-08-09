package io.github.chewyaan.eliud.services;

import io.github.chewyaan.eliud.model.RaceGoal;
import io.github.chewyaan.eliud.repository.RaceGoalRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RaceGoalService {
    private final RaceGoalRepository raceGoalRepository;

    public RaceGoalService(RaceGoalRepository raceGoalRepository) {
        this.raceGoalRepository = raceGoalRepository;
    }

    public RaceGoal createRaceGoal(RaceGoal raceGoal) {
        return raceGoalRepository.save(raceGoal);
    }

    public Optional<RaceGoal> getRaceGoal(Long id) {
        return raceGoalRepository.findById(id);
    }
}
