package io.github.chewyaan.eliud.services;

import io.github.chewyaan.eliud.model.PlannedWorkout;
import io.github.chewyaan.eliud.model.RaceGoal;
import io.github.chewyaan.eliud.model.TrainingPlan;
import io.github.chewyaan.eliud.model.WorkoutType;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PlanEngineServiceTest {

    private static final LocalDate RACE_DATE = LocalDate.of(2026, 10, 11);

    private final PlanEngineService planEngineService = new PlanEngineService();

    private RaceGoal raceGoalWithRaceDate(LocalDate raceDate) {
        return new RaceGoal("Test Marathon", 26.2, Duration.ofHours(4), raceDate);
    }

    @Test
    void generateInitialPlan_producesEightyFourWorkouts() {
        TrainingPlan plan = planEngineService.generateInitialPlan(raceGoalWithRaceDate(RACE_DATE));

        assertThat(plan.getWorkouts()).hasSize(84);
    }

    @Test
    void generateInitialPlan_firstWorkoutDateEqualsRaceDateMinusTwelveWeeks() {
        TrainingPlan plan = planEngineService.generateInitialPlan(raceGoalWithRaceDate(RACE_DATE));

        assertThat(plan.getWorkouts().get(0).getDate()).isEqualTo(RACE_DATE.minusWeeks(12));
    }

    @Test
    void generateInitialPlan_lastWorkoutDateIsOneDayBeforeRaceDate() {
        TrainingPlan plan = planEngineService.generateInitialPlan(raceGoalWithRaceDate(RACE_DATE));
        List<PlannedWorkout> workouts = plan.getWorkouts();

        assertThat(workouts.get(workouts.size() - 1).getDate()).isEqualTo(RACE_DATE.minusDays(1));
    }

    @Test
    void generateInitialPlan_datesAreSequentialAcrossPlan() {
        TrainingPlan plan = planEngineService.generateInitialPlan(raceGoalWithRaceDate(RACE_DATE));
        List<PlannedWorkout> workouts = plan.getWorkouts();

        for (int i = 1; i < workouts.size(); i++) {
            assertThat(workouts.get(i).getDate())
                    .isEqualTo(workouts.get(i - 1).getDate().plusDays(1));
        }
    }

    // Documents current placeholder behavior tied to the "[REVISIT]" comment in
    // PlanEngineService — versioning/previous-plan logic isn't implemented yet, so
    // these values are hardcoded rather than derived. Expect this test to change
    // once that logic lands.
    @Test
    void generateInitialPlan_setsNumOfWeeksVersionAndPreviousVersion() {
        TrainingPlan plan = planEngineService.generateInitialPlan(raceGoalWithRaceDate(RACE_DATE));

        assertThat(plan.getNumOfWeeks()).isEqualTo(12);
        assertThat(plan.getVersionNumber()).isEqualTo(0);
        assertThat(plan.getPreviousVersion()).isNull();
    }

    @Test
    void generateInitialPlan_setsRaceGoalReference() {
        RaceGoal raceGoal = raceGoalWithRaceDate(RACE_DATE);

        TrainingPlan plan = planEngineService.generateInitialPlan(raceGoal);

        assertThat(plan.getRaceGoal()).isSameAs(raceGoal);
    }

    @Test
    void generateInitialPlan_dayZeroIsEasySevenMiles() {
        assertFirstWeekWorkout(0, 7.0, WorkoutType.EASY);
    }

    @Test
    void generateInitialPlan_dayOneIsEasyFiveMiles() {
        assertFirstWeekWorkout(1, 5.0, WorkoutType.EASY);
    }

    @Test
    void generateInitialPlan_dayTwoIsTempoNineMiles() {
        assertFirstWeekWorkout(2, 9.0, WorkoutType.TEMPO);
    }

    @Test
    void generateInitialPlan_dayThreeIsEasyFiveMiles() {
        assertFirstWeekWorkout(3, 5.0, WorkoutType.EASY);
    }

    // The empty-body fallthrough case in the switch statement — easy to break silently.
    @Test
    void generateInitialPlan_dayFourIsRestDay() {
        assertFirstWeekWorkout(4, 0.0, WorkoutType.REST);
    }

    @Test
    void generateInitialPlan_dayFiveIsEasyTwoMiles() {
        assertFirstWeekWorkout(5, 2.0, WorkoutType.EASY);
    }

    @Test
    void generateInitialPlan_daySixIsLongFifteenMiles() {
        assertFirstWeekWorkout(6, 15.0, WorkoutType.LONG);
    }

    private void assertFirstWeekWorkout(int dayIndex, double expectedDistance, WorkoutType expectedType) {
        TrainingPlan plan = planEngineService.generateInitialPlan(raceGoalWithRaceDate(RACE_DATE));
        PlannedWorkout workout = plan.getWorkouts().get(dayIndex);

        assertThat(workout.getDistance()).isEqualTo(expectedDistance);
        assertThat(workout.getWorkoutType()).isEqualTo(expectedType);
    }

    // Documents current unguarded behavior — PlanEngineService has no null checks.
    // Expected to need updating if null-handling is later hardened.
    @Test
    void generateInitialPlan_nullRaceGoal_throwsNullPointerException() {
        assertThatThrownBy(() -> planEngineService.generateInitialPlan(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void generateInitialPlan_nullRaceDate_throwsNullPointerException() {
        RaceGoal raceGoal = raceGoalWithRaceDate(null);

        assertThatThrownBy(() -> planEngineService.generateInitialPlan(raceGoal))
                .isInstanceOf(NullPointerException.class);
    }

//    @Test
//    void generateInitialPlan_dayOfWeek() {
//        DayOfWeek raceDate = RACE_DATE.getDayOfWeek().plus(1);
//        String raceDateStr = raceDate.getDisplayName(TextStyle.SHORT, Locale.CANADA);
//
//        System.out.println(raceDateStr);
//    }

    @Test
    void generateInitialPlan_startDate() {
        LocalDate startDate = LocalDate.of(2026, 10, 11);
        TrainingPlan plan = planEngineService.generateInitialPlan(raceGoalWithRaceDate(startDate));
    }
}
