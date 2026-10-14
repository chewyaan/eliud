import { useState } from 'react'
import type { TrainingPlan } from './types/TrainingPlan'
import './App.css'
import GoalForm from './GoalForm'

function App() {
  const [trainingPlan, setTrainingPlan] = useState<TrainingPlan | null>(null);

  return (
    <>
     <GoalForm trainingPlan={trainingPlan} setTrainingPlan={setTrainingPlan} />
    </>
  )
}

export default App
