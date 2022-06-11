import React, { useEffect, useState } from "react";
import styled from "styled-components";
import { VscTriangleLeft, VscTriangleRight } from "react-icons/vsc";
import PromiseDateModal from "./PromiseDateModal";
import { useQuery } from "react-query";
import api from "../auth/api";
import { useParams } from "react-router";
import { dateFormatter } from "../utils/dateFormatter";

interface ITeamCalender {
  date: string;
  dateStatus: string;
}

const RED = "RED";
const YELLOW = "YELLOW";
const GREEN = "GREEN";
const COLOR = { RED: "#ff7373", YELLOW: "#ffd37a", GREEN: "#85ba73" };
const DAYS_OF_WEEK = ["일", "월", "화", "수", "목", "금", "토"];

function PromiseDateCalendar() {
  const [dateModal, setDateModal] = useState(false);
  const now = new Date();
  const [year, setYear] = useState(now.getFullYear());
  const [month, setMonth] = useState(now.getMonth());
  const [weeks, setWeeks] = useState<any[][]>([]);
  const [currentDate, setCurrentDate] = useState("");
  const params = useParams();
  const { data: teamCalendarData } = useQuery<ITeamCalender[]>(
    [
      "teamCalendarData",
      params.id,
      String(year) + "-" + String(month + 1).padStart(2, "0"),
    ],
    async () => {
      const { data } = await api.get(
        `/bandate/team/${params.id}/${
          String(year) + "-" + String(month + 1).padStart(2, "0")
        }`
      );
      return data;
    }
  );
  useEffect(() => {
    if (!teamCalendarData) {
      return;
    }
    let newColors = [];

    for (let i = 0; i < teamCalendarData.length; i++) {
      const colorObj = {
        id: teamCalendarData[i].date,
        color: teamCalendarData[i].dateStatus,
      };
      newColors.push(colorObj);
    }
    makeCalendar(year, month);
  }, [teamCalendarData, month]);
  const makeCalendar = (year: number, month: number) => {
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
          newWeek.push({
            id: String(week) + String(day),
            value: "",
            color: "null",
          });
        } else {
          let thisColor = GREEN;
          const id =
            String(year) +
            String(month + 1).padStart(2, "0") +
            String(date).padStart(2, "0");

          if (teamCalendarData[date - 1].dateStatus === "IMPOSSIBLE") {
            thisColor = "RED";
          } else if (teamCalendarData[date - 1].dateStatus === "UNCERTAIN") {
            thisColor = "YELLOW";
          }
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
    setWeeks(newWeeks);
  };

  const onDateClick = (id: string) => {
    setDateModal((prev) => !prev);
    setCurrentDate(id);
  };
  const onBtnClick = (isPrev: string) => {
    const prevMonth = month;
    if (isPrev === "prev") {
      setYear(prevMonth === 0 ? year - 1 : year);
      setMonth(prevMonth === 0 ? 11 : prevMonth - 1);
    } else if (isPrev === "next") {
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
                week.map((date, date_idx) => (
                  <DateBox
                    key={String(week_idx) + String(date_idx)}
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
      <Buttons>
        <Button>
          <Circle color={COLOR[RED]} />
          불가능
        </Button>
        <Button>
          <Circle color={COLOR[YELLOW]} />
          애매함
        </Button>
        <Button>
          <Circle color={COLOR[GREEN]} />
          가능
        </Button>
      </Buttons>
      {dateModal && (
        <PromiseDateModal state={{ dateModal, setDateModal, currentDate }} />
      )}
    </Container>
  );
}

export default PromiseDateCalendar;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 60vh;
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
  height: ${(props) => (props.height ? props.height : "6vh")};
  background-color: ${(p) =>
    p.isDay || !p.color || p.color === "null"
      ? `${p.theme.smoke}`
      : p.color === GREEN
      ? p.theme.cgreen
      : p.color === RED
      ? p.theme.cred
      : p.theme.cyellow};
  padding: 2px;
  border: solid 0.1em ${(p) => p.theme.smoke};
  display: flex;
  flex-direction: column;
  align-items: center;
  font-size: 0.9em;
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
  margin: 1em;
`;
const Button = styled.div`
  display: flex;
  flex-direction: row;
  cursor: pointer;
  font-size: 0.7em;
`;
const Circle = styled.div<{ color: string }>`
  width: 1em;
  height: 1em;
  border-radius: 1em;
  background-color: ${(p) => p.color};
  margin-right: 0.3em;
  margin-top: 0.1em;
`;
