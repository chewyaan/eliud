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
        LocalDate startDate = raceGoal.getRaceDate().minusWeeks(NUM_OF_WEEKS);

        // [REVISIT] need to implement prev TrainingPlan version logic, and versioning
        TrainingPlan trainingPlan = new TrainingPlan(null, raceGoal, NUM_OF_WEEKS, 0);

        // Stream of dates
        startDate.datesUntil(raceGoal.getRaceDate()).forEach(workoutDate -> {

            PlannedWorkout plannedWorkout = switch (workoutDate.getDayOfWeek()) {
                case MONDAY -> new PlannedWorkout(7.0, workoutDate, WorkoutType.EASY);
                case TUESDAY, THURSDAY -> new PlannedWorkout(5.0, workoutDate, WorkoutType.EASY);
                case WEDNESDAY -> new PlannedWorkout(9.0, workoutDate, WorkoutType.TEMPO);
                case FRIDAY -> new PlannedWorkout(0.0, workoutDate, WorkoutType.REST);
                case SATURDAY -> new PlannedWorkout(2.0, workoutDate, WorkoutType.EASY);
                case SUNDAY -> new PlannedWorkout(15.0, workoutDate, WorkoutType.LONG);
            };

            trainingPlan.addWorkout(plannedWorkout);
        });

        return trainingPlan;
    }
}
