package io.github.chewyaan.eliud.services;

import io.github.chewyaan.eliud.model.PlannedWorkout;
import io.github.chewyaan.eliud.model.RaceGoal;
import io.github.chewyaan.eliud.model.TrainingPlan;
import io.github.chewyaan.eliud.model.WorkoutType;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PlanEngineService {

    public PlanEngineService() {}

    public TrainingPlan generateInitialPlan(RaceGoal raceGoal) {

        final int NUM_OF_WEEKS = 12;
        final int DAYS_IN_WEEK = 7;
        LocalDate startDate = raceGoal.getRaceDate().minusWeeks(NUM_OF_WEEKS);

        // [REVISIT] need to implement prev TrainingPlan version logic, and versioning
        TrainingPlan trainingPlan = new TrainingPlan(null, raceGoal, NUM_OF_WEEKS, 0);

        for (int w = 0; w < NUM_OF_WEEKS; w++) {
            for (int d = 0; d < DAYS_IN_WEEK; d++) {
                double distance = 0.0;
                LocalDate workoutDate = startDate.plusWeeks(w).plusDays(d);
                WorkoutType workoutType = WorkoutType.REST;

                switch (d) {
                    case 0:
                        distance = 7.0;
                        workoutType = WorkoutType.EASY;
                        break;
                    case 1:
                    case 3:
                        distance = 5.0;
                        workoutType = WorkoutType.EASY;
                        break;
                    case 2:
                        distance = 9.0;
                        workoutType = WorkoutType.TEMPO;
                        break;
                    case 4:
                        break;
                    case 5:
                        distance = 2.0;
                        workoutType = WorkoutType.EASY;
                        break;
                    case 6:
                        distance = 15.0;
                        workoutType = WorkoutType.LONG;
                        break;
                    default:
                        throw new IllegalStateException("Something went wrong. Please try again.");
                }

                trainingPlan.addWorkout(new PlannedWorkout(distance, workoutDate, workoutType));
            }
        }

        return trainingPlan;
    }
}
