import { useState } from "react";

export default function Form() {
  const [name, setName] = useState("");
  const [distance, setDistance] = useState("");
  const [time, setTime] = useState("");

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
  };

  return (
    <form method="post" onSubmit={handleSubmit}>
      <label>
        Name: <input type="text" value={name} onChange={e => setName(e.target.value)}/>
      </label>
      <label>
        Distance: <input type="number" value={distance} onChange={e => setDistance(e.target.value)}/>
      </label>
      <label>
        Time: <input type="text" value={time} onChange={e => setTime(e.target.value)}/>
      </label>
      <button type="submit">Enter</button>
    </form>
  );
};
