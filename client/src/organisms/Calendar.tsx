import React, { useEffect, useState } from "react";
const RED = "#ff5454";
const YELLOW = "#ffc654";
const GREEN = "#a1d690";
interface IColors {
  id: string;
  color: string;
}
function Calendar() {
  const now = new Date();
  const [year, setYear] = useState(now.getFullYear());
  const [month, setMonth] = useState(now.getMonth());
  const [weeks, setWeeks] = useState<any[][]>([]);
  const [currentColor, setCurrentColor] = useState(RED);
  const [colors, setColors] = useState<IColors[]>([]);
  useEffect(() => {
    makeCalendar(year, month, colors);
  }, [year, month, colors]);
  const makeCalendar = (year: number, month: number, colors: IColors[]) => {
    const FEB =
      (year % 4 === 0 && year % 100 !== 0) || year % 400 === 0 ? 29 : 28;
    const LASTDATE = [31, FEB, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
    const firstDay = new Date(year, month, 1).getDay();
    const lastDate = LASTDATE[month];
    let date = 1;
    let newWeeks = [];
    for (let week = 0; week < 6; week++) {
      let newWeek = [];
      for (let day = 0; day < 7; day++) {
        if (date > lastDate || (week == 0 && firstDay > day)) {
          newWeek.push({ id: null, value: "", color: "whitesmoke" });
        } else {
          let thisColor = GREEN;
          const id =
            String(year) +
            String(month).padStart(2, "0") +
            String(date).padStart(2, "0");
          colors &&
            colors.map((elem) => {
              elem.id === id && (thisColor = elem.color);
            });
          newWeek.push({
            id: id,
            value: date,
            color: thisColor,
          });
          date++;
        }
      }
      newWeeks.push(newWeek);
    }
    console.log(newWeeks);
    setWeeks(newWeeks);
  };

  const onDatePress = (id: string) => {
    const newColors = [...colors, { id, color: currentColor }];
    setColors(newColors);
  };
  const onBtnClick = (title: string) => {
    const prevMonth = month;
    if (title === "prev") {
      setYear(prevMonth === 0 ? year - 1 : year);
      setMonth(prevMonth === 0 ? 11 : prevMonth - 1);
    } else if (title === "next") {
      setYear(prevMonth === 11 ? year + 1 : year);
      setMonth(prevMonth === 11 ? 0 : prevMonth + 1);
    }
  };
  return (
    <div>
      <div>
        <button onClick={() => onBtnClick("prev")}>Prev</button>
        <span>
          {year}-{String(month + 1).padStart(2, "0")}
        </span>
        <button onClick={() => onBtnClick("next")}>Next</button>
      </div>
      <div>
        <button onClick={() => setCurrentColor(RED)} color={RED}>
          불가능
        </button>
        <button onClick={() => setCurrentColor(YELLOW)} color={YELLOW}>
          애매함
        </button>
        <button onClick={() => setCurrentColor(GREEN)} color={GREEN}>
          가능
        </button>
      </div>
      <div>
        {weeks &&
          weeks.map((week, week_idx) => (
            <div key={week_idx}>
              {!(week[0].value === "" && week_idx === 5) &&
                week.map((date, day_idx) => (
                  <div>
                    <span>{date.value}</span>
                  </div>
                ))}
            </div>
          ))}
      </div>
    </div>
  );
}

export default Calendar;
