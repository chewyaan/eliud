import { useState } from "react";
import type { RaceGoal } from "../types/RaceGoal";
import type { TrainingPlan } from "../types/TrainingPlan";
import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card"
import {
  Alert,
  AlertDescription,
  AlertTitle,
} from "@/components/ui/alert"
import { DatePicker } from "./DatePicker";
import { CheckCircle2Icon, AlertCircleIcon } from "lucide-react"


export default function GoalForm({ trainingPlan, setTrainingPlan }: { trainingPlan: TrainingPlan | null; setTrainingPlan: React.Dispatch<React.SetStateAction<TrainingPlan | null>> }) {
  const [name, setName] = useState("");
  const [distance, setDistance] = useState("");
  const [time, setTime] = useState("");
  const [raceDate, setRaceDate] = useState<Date | null>(null);
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
    <>
      <Card className="max-w-md mx-auto my-5">
        <CardHeader>
          <CardTitle>When is your next race?</CardTitle>
          <CardDescription>
            Enter the details of your next race to generate a personalized training plan.
          </CardDescription>
        </CardHeader>
        <CardContent>
          <form onSubmit={handleGenerateRaceGoal}>
            <div className="flex flex-col gap-6">
              <div className="grid gap-2">
                <Label>Race Name</Label>
                <Input type="text" value={name} onChange={(e) => setName(e.target.value)} placeholder="e.g. Boston Marathon, TCS New York City Marathon, etc..." />
              </div>
              <div className="grid grid-cols-3 gap-2">
                <div className="grid gap-2">
                  <Label>Distance (in km)</Label>
                  <Input className="w-[50%]" type="number" value={distance} onChange={(e) => setDistance(e.target.value)} />
                </div>
                <div className="grid gap-2">
                  <Label htmlFor="date-picker-simple">Date</Label>
                  <DatePicker raceDate={raceDate} setRaceDate={setRaceDate} />
                </div>
                <div className="grid gap-2">
                  <Label>Goal Time</Label>
                  <Input type="text" value={time} onChange={(e) => setTime(e.target.value)} placeholder="PTxxHxxM" />
                </div>
              </div>
              {/* <label>
            Race Date
            <input
              type="date"
              value={raceDate}
              onChange={(e) => setRaceDate(e.target.value)}
            />
          </label> */}
              <Button type="submit">Create Goal</Button>
            </div>
          </form>
        </CardContent>
        {raceGoalError && (
          <Alert variant="destructive" className="max-w-md mx-auto my-5">
            <AlertCircleIcon />
            <AlertTitle>Error</AlertTitle>
            <AlertDescription>
              <p style={{ color: "red" }}>{raceGoalError}</p>
            </AlertDescription>
          </Alert>
        )}
      </Card>
      {raceGoal && (
        <Alert className="max-w-md mx-auto my-5">
          <CheckCircle2Icon />
          <AlertTitle>Race Goal Successfully Created</AlertTitle>
          <AlertDescription>
            <div>
              <div>ID: {raceGoal.id}</div>
              <div>Name: {raceGoal.name}</div>
              <div>Distance: {raceGoal.distance}</div>
              <div>Race Date: {raceGoal.raceDate}</div>
              <div>Time: {raceGoal.time}</div>
            </div>
            {raceGoal && !planPostInProgress && !trainingPlan && (
              <Button type="button" onClick={handleGenerateTrainingPlan}>
                Generate Training Plan
              </Button>
            )}
          </AlertDescription>
        </Alert>
      )}

      {trainingPlanError && (
        <Alert variant="destructive" className="max-w-md mx-auto my-5">
          <AlertCircleIcon />
          <AlertTitle>Error</AlertTitle>
          <AlertDescription>
            <p style={{ color: "red" }}>{trainingPlanError}</p>
          </AlertDescription>
        </Alert>
      )}
      {trainingPlan && !planPostInProgress && (
        <Alert className="max-w-md mx-auto my-5">
          <CheckCircle2Icon />
          <AlertTitle>Training Plan Generated</AlertTitle>
          <AlertDescription>
            <p>Training plan generated! Check the Network tab in your browser's developer tools for more details.</p>
          </AlertDescription>
        </Alert>
      )}
    </>
  );
}
