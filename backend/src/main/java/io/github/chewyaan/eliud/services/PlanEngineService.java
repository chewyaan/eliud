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
        LocalDate startDate = raceGoal.getRaceDate().minusWeeks(NUM_OF_WEEKS);

        // [REVISIT] need to implement prev TrainingPlan version logic, and versioning
        TrainingPlan trainingPlan = new TrainingPlan(null, raceGoal, NUM_OF_WEEKS, 0);

        for (int week = 0; week < NUM_OF_WEEKS; week++) {
            for (DayOfWeek day : DayOfWeek.values()) {
                double distance = 0.0;
                LocalDate workoutDate = startDate.plusWeeks(week).plusDays(day.getValue()-1);
                WorkoutType workoutType = WorkoutType.REST;

                switch (workoutDate.getDayOfWeek()) {
                    case MONDAY:
                        distance = 7.0;
                        workoutType = WorkoutType.EASY;
                        break;
                    case TUESDAY:
                    case WEDNESDAY:
                        distance = 5.0;
                        workoutType = WorkoutType.EASY;
                        break;
                    case THURSDAY:
                        distance = 9.0;
                        workoutType = WorkoutType.TEMPO;
                        break;
                    case FRIDAY:
                        break;
                    case SATURDAY:
                        distance = 2.0;
                        workoutType = WorkoutType.EASY;
                        break;
                    case SUNDAY:
                        if (week != NUM_OF_WEEKS-1) {
                            distance = 15.0;
                            workoutType = WorkoutType.LONG;
                        }
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
