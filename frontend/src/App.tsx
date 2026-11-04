import { useState } from 'react'
import type { TrainingPlan } from './types/TrainingPlan'
import GoalForm from './components/GoalForm'
import type { PlannedWorkout } from './types/PlannedWorkout';
import {
  Carousel,
  CarouselContent,
  CarouselItem,
  CarouselNext,
  CarouselPrevious,
} from "@/components/ui/carousel"
import {
  Card,
  CardContent
} from "@/components/ui/card"

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
      {trainingPlan && (
        <Card className="max-w-md mx-auto my-5">
          <CardContent className="max-w-sm mx-auto">
            <Carousel>
              <CarouselContent>
                {Array.from(workoutMap.entries()).map(([week, workouts]) => (
                <CarouselItem key={week}>
                  <div className="flex flex-col items-center justify-center">
                    <h2>{week}</h2>
                    <ul>
                      {workouts.map((workout) => (
                        <li key={workout.date}>
                          Date: {workout.date}, Distance: {workout.distance}, Type: {workout.workoutType}
                        </li>
                      ))}
                    </ul>
                  </div>
                </CarouselItem>
              ))}
            </CarouselContent>
            <CarouselPrevious />
            <CarouselNext />
          </Carousel>
        </CardContent>
      </Card>
      )}
    </>
  )
}

export default App
