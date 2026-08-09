import { useState } from "react";
import type { RaceGoal } from "./RaceGoal";

export default function Form() {
  const [name, setName] = useState("");
  const [distance, setDistance] = useState("");
  const [time, setTime] = useState("");
  const [raceGoal, setRaceGoal] = useState<RaceGoal | null>(null);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const url = "http://localhost:8081/api/raceGoals";
    try {
      const response = await fetch(url, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ name, distance: Number(distance), time }),
      });
      if (response.ok) {
        setRaceGoal(await response.json());
      } else {
        console.log(new Error(`Error Status: ${response.status}`));
      }
    } catch (error) {
      console.log(new Error(`Error: ${error}`));
    }
  };

  return (
    <form onSubmit={handleSubmit}>
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
          Time:{" "}
          <input
            type="text"
            value={time}
            onChange={(e) => setTime(e.target.value)}
          />
        </label>
        <button type="submit">Enter</button>
      </div>
      {raceGoal && (
        <div>
          <div>ID: {raceGoal.id}</div>
          <div>Name: {raceGoal.name}</div>
          <div>Distance: {raceGoal.distance}</div>
          <div>Time: {raceGoal.time}</div>
        </div>
      )}
    </form>
  );
}
