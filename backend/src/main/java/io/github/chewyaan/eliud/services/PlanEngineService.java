package io.github.chewyaan.eliud.services;

import io.github.chewyaan.eliud.model.PlannedWorkout;
import io.github.chewyaan.eliud.model.RaceGoal;
import io.github.chewyaan.eliud.model.TrainingPlan;
import io.github.chewyaan.eliud.model.WorkoutType;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Service
public class PlanEngineService {

    public PlanEngineService() {}

    public TrainingPlan generateInitialPlan(RaceGoal raceGoal) {

        final int NUM_OF_WEEKS = 12;
        final int NUM_OF_DAYS = NUM_OF_WEEKS * 7;
        LocalDate startDate = raceGoal.getRaceDate().minusWeeks(NUM_OF_WEEKS);

        // [REVISIT] need to implement prev TrainingPlan version logic, and versioning
        TrainingPlan trainingPlan = new TrainingPlan(null, raceGoal, NUM_OF_WEEKS, 0);

        for (int day = 0; day < NUM_OF_DAYS; day++) {
            double distance = 0.0;
            LocalDate workoutDate = startDate.plusDays(day);
            WorkoutType workoutType = WorkoutType.REST;

            switch (workoutDate.getDayOfWeek()) {
                case MONDAY -> {
                    distance = 7.0;
                    workoutType = WorkoutType.EASY;
                }
                case TUESDAY, WEDNESDAY -> {
                    distance = 5.0;
                    workoutType = WorkoutType.EASY;
                }
                case THURSDAY -> {
                    distance = 9.0;
                    workoutType = WorkoutType.TEMPO;
                }
                case FRIDAY -> {}
                case SATURDAY -> {
                    distance = 2.0;
                    workoutType = WorkoutType.EASY;
                }
                case SUNDAY -> {
                    distance = 15.0;
                    workoutType = WorkoutType.LONG;
                }
            }

            trainingPlan.addWorkout(new PlannedWorkout(distance, workoutDate, workoutType));
        }

        return trainingPlan;
    }
}
