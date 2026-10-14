import { useState } from "react";
import type { RaceGoal } from "./types/RaceGoal";
import type { TrainingPlan } from "./types/TrainingPlan";

export default function GoalForm({ trainingPlan, setTrainingPlan }: { trainingPlan: TrainingPlan | null; setTrainingPlan: React.Dispatch<React.SetStateAction<TrainingPlan | null>> }) {
  const [name, setName] = useState("");
  const [distance, setDistance] = useState("");
  const [time, setTime] = useState("");
  const [raceDate, setRaceDate] = useState("");
  const [raceGoal, setRaceGoal] = useState<RaceGoal | null>(null);
  const [planPostInProgress, setPlanPostInProgress] = useState(false);
  const [raceGoalError, setRaceGoalError] = useState("");
  const [trainingPlanError, setTrainingPlanError] = useState("");

  const handleGenerateRaceGoal = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const url = "http://localhost:8081/api/raceGoals";
    try {
      const response = await fetch(url, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ name, distance: Number(distance), time, raceDate }),
      });
      if (response.ok) {
        setRaceGoal(await response.json());
        setTrainingPlan(null); // Clear any existing training plan
        setTrainingPlanError(""); // Clear any previous error messages
        setRaceGoalError(""); // Clear any previous error messages
      } else {
        setRaceGoalError(`Error Status: ${response.status}`);
        console.log(new Error(`Error Status: ${response.status}`));
      }
    } catch (error) {
      setRaceGoalError(`Error: ${error}`);
      console.log(new Error(`Error: ${error}`));
    }
  };

  const handleGenerateTrainingPlan = async () => {
    setPlanPostInProgress(true);
    const url = `http://localhost:8081/api/raceGoals/${raceGoal?.id}/plan`;
    try {
      const response = await fetch(url, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
      });
      if (response.ok) {
        setTrainingPlan(await response.json());
        setTrainingPlanError(""); // Clear any previous error messages
      } else {
        setTrainingPlanError(`Error Status: ${response.status}`);
        console.log(new Error(`Error Status: ${response.status}`));
      }
    } catch (error) {
      setTrainingPlanError(`Error: ${error}`);
      console.log(new Error(`Error: ${error}`));
    } finally {
      setPlanPostInProgress(false);
    }
  };

  return (
    <form onSubmit={handleGenerateRaceGoal}>
      <div>
        <label>
          Name:{" "}
          <input
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
        </label>
        <label>
          Distance:{" "}
          <input
            type="number"
            value={distance}
            onChange={(e) => setDistance(e.target.value)}
          />
        </label>
        <label>
          Race Date:{" "}
          <input
            type="date"
            value={raceDate}
            onChange={(e) => setRaceDate(e.target.value)}
          />
        </label>
        <label>
          Time:{" "}
          <input
            type="text"
            value={time}
            onChange={(e) => setTime(e.target.value)}
          />
        </label>
        <button type="submit">Create Goal</button>
      </div>
      {raceGoal && (
        <div>
          <div>ID: {raceGoal.id}</div>
          <div>Name: {raceGoal.name}</div>
          <div>Distance: {raceGoal.distance}</div>
          <div>Race Date: {raceGoal.raceDate}</div>
          <div>Time: {raceGoal.time}</div>
        </div>
      )}
      {raceGoalError && (
        <p style={{ color: "red" }}>{raceGoalError}</p>
      )}
      {raceGoal && !planPostInProgress && !trainingPlan && (
        <button type="button" onClick={handleGenerateTrainingPlan}>
          Generate Training Plan
        </button>
      )}
       {trainingPlanError && (
        <p style={{ color: "red" }}>{trainingPlanError}</p>
      )}
      {trainingPlan && !planPostInProgress && (
        <p>Training plan generated! Check the Network tab in your browser's developer tools for more details.</p>
      )}
    </form>
  );
}
