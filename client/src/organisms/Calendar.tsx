import React, { useEffect, useState } from "react";
import styled from "styled-components";
import { VscTriangleLeft, VscTriangleRight } from "react-icons/vsc";

import * as S from "../atoms/_index";
const RED = "RED";
const YELLOW = "YELLOW";
const GREEN = "GREEN";
const DAYS_OF_WEEK = ["일", "월", "화", "수", "목", "금", "토"];

interface IColors {
  id: string;
  color: string;
}

function Calendar() {
  const [groupView, setGroupView] = useState(true);
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
          newWeek.push({ id: null, value: "", color: "null" });
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

  const onDateClick = (id: string) => {
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
    <Container>
      <Elements>
        <VscTriangleLeft
          style={{ cursor: "pointer" }}
          onClick={() => onBtnClick("prev")}
        />
        <span>
          {year}-{String(month + 1).padStart(2, "0")}
        </span>
        <VscTriangleRight
          style={{ cursor: "pointer" }}
          onClick={() => onBtnClick("next")}
        />
      </Elements>
      <Elements>
        <Buttons>
          <Button
            selected={currentColor === RED ? true : false}
            onClick={() => setCurrentColor(RED)}
            color={RED}
          >
            <Circle color={"#ff5454"} />
            불가능
          </Button>
          <Button
            selected={currentColor === YELLOW ? true : false}
            onClick={() => setCurrentColor(YELLOW)}
            color={YELLOW}
          >
            <Circle color={"#ffc654"} />
            애매함
          </Button>
          <Button
            selected={currentColor === GREEN ? true : false}
            onClick={() => setCurrentColor(GREEN)}
            color={GREEN}
          >
            <Circle color={"#a1d690"} />
            가능
          </Button>
        </Buttons>
        <S.RoundBtn
          style={{ fontSize: "0.8em" }}
          onClick={() => setGroupView((prev) => !prev)}
        >
          {groupView ? "내 일정 등록" : "완료"}
        </S.RoundBtn>
      </Elements>
      <Month>
        <Week>
          {DAYS_OF_WEEK.map((day, idx) => (
            <DateBox key={idx} height={"5vh"} isDay={true}>
              {day}
            </DateBox>
          ))}
        </Week>
        {weeks &&
          weeks.map((week, week_idx) => (
            <Week key={week_idx}>
              {!(week[0].value === "" && week_idx === 5) &&
                week.map((date, day_idx) => (
                  <DateBox
                    key={date.id}
                    color={date.color}
                    onClick={() => onDateClick(date.id)}
                    style={{ color: "black" }}
                  >
                    {date.value}
                  </DateBox>
                ))}
            </Week>
          ))}
      </Month>
    </Container>
  );
}

export default Calendar;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 80vh;
`;

const Month = styled.div`
  display: flex;
  flex-direction: column;
  border-radius: 10px;
  border: solid 10px ${(p) => p.theme.smoke};
  overflow: hidden;
  cursor: pointer;
`;
const Week = styled.div`
  display: grid;
  grid-template-columns: repeat(7, 1fr);
`;
const DateBox = styled.div<{
  height?: string;
  color?: string;
  isDay?: boolean;
}>`
  width: 10vw;
  @media screen and (min-width: 900px) {
    width: 5vw;
  }
  height: ${(props) => (props.height ? props.height : "10vh")};
  background-color: ${(p) =>
    p.isDay || !p.color || p.color === "null"
      ? `${p.theme.smoke}`
      : p.color === GREEN
      ? p.theme.cgreen
      : p.color === RED
      ? p.theme.cred
      : p.theme.cyellow};
  padding: 2px;
`;
const Elements = styled.div`
  display: flex;
  flex-direction: row;
  width: 90%;
  justify-content: space-between;
  margin: 0.8em;
`;
const Buttons = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  width: 55%;
`;
const Button = styled.div<{ selected: boolean }>`
  display: flex;
  flex-direction: row;
  cursor: pointer;
  font-size: 0.9em;
  font-weight: ${(p) => (p.selected ? 600 : 500)};
  text-shadow: ${(p) => p.selected && `0px 0px 10px ${p.theme.highlight}`};
`;
const Circle = styled.div<{ color: string }>`
  width: 1em;
  height: 1em;
  border-radius: 1em;
  background-color: ${(p) => p.color};
  margin-right: 0.3em;
  margin-top: 0.1em;
`;
