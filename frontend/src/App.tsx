import { useState } from 'react'
import type { TrainingPlan } from './types/TrainingPlan'
import './App.css'
import GoalForm from './components/GoalForm'
import type { PlannedWorkout } from './types/PlannedWorkout';

function App() {
  const [trainingPlan, setTrainingPlan] = useState<TrainingPlan | null>(null);
  const workoutMap: Map<string, PlannedWorkout[]> = new Map<string, PlannedWorkout[]>();

  const weekLabel = (workout: string) => {
    
    if (trainingPlan === null) {
      throw new Error("Training plan is null. Cannot determine week label.");
    }

    if (trainingPlan.plannedWorkouts.length === 0) {
      throw new Error("Training plan has no planned workouts. Cannot determine week label.");
    }
    
    const startDate = new Date(trainingPlan.plannedWorkouts[0].date);
    const workOutDate = new Date(workout);

    // Calculate the number of days between the start date and the workout date (from ms to days)
    const daysInBetween = (workOutDate.getTime() - startDate.getTime()) / 1000 / 60 / 60 / 24;
    const week = Math.floor(daysInBetween / 7) + 1;

    if (week <= trainingPlan.numOfWeeks) {
      return `Week ${week}`;
    } else {
      return "Race Week";
    }
    
  };

  // Group workouts by week
  trainingPlan?.plannedWorkouts.forEach((workout) => {
    const week: string = weekLabel(workout.date);
    workoutMap.set(week, [...(workoutMap.get(week) || []), workout]);
  });

  return (
    <>
     <GoalForm trainingPlan={trainingPlan} setTrainingPlan={setTrainingPlan} />
     {Array.from(workoutMap.entries()).map(([week, workouts]) => (
        <div key={week}>
          <h2>{week}</h2>
          <ul>
            {workouts.map((workout) => (
              <li key={workout.date}>
                Date: {workout.date}, Distance: {workout.distance}, Type: {workout.workoutType}
              </li>
            ))}
          </ul>
        </div>
      ))}
    </>
  )
}

export default App
