import type { PlannedWorkout } from "./PlannedWorkout";

export type TrainingPlan = {
    currentId: number,
    prevId: null | number,
    raceGoalId: number,
    plannedWorkouts: PlannedWorkout[],
    numOfWeeks: number,
    versionNumber: number
}