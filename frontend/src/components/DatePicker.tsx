"use client"

import * as React from "react"
import { format } from "date-fns"

import { Button } from "@/components/ui/button"
import { Calendar } from "@/components/ui/calendar"
import { Label } from "@/components/ui/label"
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover"

export function DatePicker({ raceDate, setRaceDate }: { raceDate: Date | null; setRaceDate: React.Dispatch<React.SetStateAction<Date | null>> }) {

  return (
    <div className="mx-auto w-44">
      <Popover>
        <PopoverTrigger render={<Button variant="outline" id="date-picker-simple" className="justify-start font-normal">{raceDate ? format(raceDate, "PPP") : <span>Pick a date</span>}</Button>} />
        <PopoverContent className="w-auto p-0" align="start">
          <Calendar
            mode="single"
            selected={raceDate}
            onSelect={setRaceDate}
            defaultMonth={raceDate}
          />
        </PopoverContent>
      </Popover>
    </div>
  )
}
