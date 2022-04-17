import React, { useEffect, useState } from "react";
import styled from "styled-components";
import { VscTriangleLeft, VscTriangleRight } from "react-icons/vsc";
import * as A from "../atoms/_index";
import * as S from "../styles/_index";
import { AnimatePresence, motion } from "framer-motion";
import PromiseDateModal from "./PromiseDateModal";
import { FaStickyNote } from "react-icons/fa";

const TEST_DATA = [
  {
    id: 0,
    member_id: 0,
    member_name: "김채은",
    member_img:
      "https://i.pinimg.com/474x/6e/a6/77/6ea6778a68920e993c33405a79a41ae5.jpg",

    promise_id: 0,
    date: "20220416",
    reason: "",

    date_status: "RED",
  },
  {
    id: 1,
    member_id: 1,
    member_name: "고산하",
    member_img:
      "https://i.pinimg.com/474x/6e/a6/77/6ea6778a68920e993c33405a79a41ae5.jpg",

    promise_id: 0,
    date: "20220417",
    reason: "가족 모임",

    date_status: "RED",
  },
  {
    id: 2,
    member_name: "고산하",
    member_id: 1,
    member_img:
      "https://i.pinimg.com/474x/6e/a6/77/6ea6778a68920e993c33405a79a41ae5.jpg",

    promise_id: 0,
    date: "20220416",
    reason: "",
    date_status: "YELLOW",
  },
  {
    id: 3,
    member_id: 2,
    member_img:
      "https://i.pinimg.com/474x/6e/a6/77/6ea6778a68920e993c33405a79a41ae5.jpg",
    member_name: "이준석",
    promise_id: 0,
    date: "20220416",
    reason: "",
    date_status: "GREEN",
  },
  {
    id: 4,
    member_id: 3,
    member_img:
      "https://i.pinimg.com/474x/6e/a6/77/6ea6778a68920e993c33405a79a41ae5.jpg",
    member_name: "황승환",
    promise_id: 0,
    date: "20220417",
    reason: "가족 모임",
    date_status: "RED",
  },
  {
    id: 5,
    member_img:
      "https://i.pinimg.com/236x/a4/ba/d8/a4bad8978517b1f10ddaf4d833a4fe78.jpg",
    member_id: 2,
    member_name: "이준석",
    promise_id: 0,
    date: "20220417",
    reason: "가족 모임",

    date_status: "YELLOW",
  },
];

const RED = "RED";
const YELLOW = "YELLOW";
const GREEN = "GREEN";
const COLOR = { RED: "#ff7373", YELLOW: "#ffd37a", GREEN: "#85ba73" };
const DAYS_OF_WEEK = ["일", "월", "화", "수", "목", "금", "토"];
interface IColors {
  id: string;
  color: string;
}

function PromiseDateCalendar() {
  const [groupView, setGroupView] = useState(true);
  const [dateModal, setDateModal] = useState(false);
  const now = new Date();
  const [year, setYear] = useState(now.getFullYear());
  const [month, setMonth] = useState(now.getMonth() + 1);
  const [weeks, setWeeks] = useState<any[][]>([]);
  const [currentColor, setCurrentColor] = useState(RED);
  const [currentDate, setCurrentDate] = useState("");
  const [colors, setColors] = useState<IColors[]>([]);
  useEffect(() => {
    makeCalendar(year, month, colors);
  }, [month, colors]);
  useEffect(() => {
    let newColors = [];
    if (groupView) {
      for (let i = 0; i < TEST_DATA.length; i++) {
        const colorObj = {
          id: TEST_DATA[i].date,
          color: TEST_DATA[i].date_status,
        };
        newColors.push(colorObj);
      }
    } else {
      for (let i = 0; i < TEST_DATA.length; i++) {
        if (TEST_DATA[i].member_id === 0) {
          const colorObj = {
            id: TEST_DATA[i].date,
            color: TEST_DATA[i].date_status,
          };
          newColors.push(colorObj);
        }
      }
    }
    setColors(newColors);
  }, [groupView]);
  const makeCalendar = (year: number, month: number, colors: IColors[]) => {
    const FEB =
      (year % 4 === 0 && year % 100 !== 0) || year % 400 === 0 ? 29 : 28;
    const LASTDATE = [0, 31, FEB, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31];
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
          for (let i = 0; i < colors.length; i++) {
            if (colors[i].id === id) {
              if (colors[i].color === "RED") {
                thisColor = "RED";
                break;
              } else if (colors[i].color === "YELLOW") {
                thisColor = "YELLOW";
              }
            }
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
    if (groupView) {
      setDateModal((prev) => !prev);
      setCurrentDate(id);
    } else {
      const newColors = [...colors, { id, color: currentColor }];
      setColors(newColors);
    }
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
    console.log(year, month);
  };

  const isNoted = (id: string) => {
    for (let i = 0; i < TEST_DATA.length; i++) {
      if (TEST_DATA[i].date === id && TEST_DATA[i].reason) {
        return true;
      }
    }
    return false;
  };

  return (
    <Container>
      <Elements>
        <VscTriangleLeft
          style={{ cursor: "pointer" }}
          onClick={() => onBtnClick("prev")}
        />
        <span>
          {year}-{String(month).padStart(2, "0")}
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
            <Circle color={COLOR[RED]} />
            불가능
          </Button>
          <Button
            selected={currentColor === YELLOW ? true : false}
            onClick={() => setCurrentColor(YELLOW)}
            color={YELLOW}
          >
            <Circle color={COLOR[YELLOW]} />
            애매함
          </Button>
          <Button
            selected={currentColor === GREEN ? true : false}
            onClick={() => setCurrentColor(GREEN)}
            color={GREEN}
          >
            <Circle color={COLOR[GREEN]} />
            가능
          </Button>
        </Buttons>
        <A.RoundBtn
          fontSize={0.7}
          value={groupView ? "내 일정 등록" : "완료"}
          onClick={() => setGroupView((prev) => !prev)}
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
                week.map((date, day_idx) => (
                  <DateBox
                    key={String(week_idx) + String(day_idx)}
                    color={date.color}
                    onClick={() => onDateClick(date.id)}
                    style={{ color: "black" }}
                  >
                    {date.value}
                    {groupView && isNoted(date.id) && (
                      <FaStickyNote color={"black"} />
                    )}
                  </DateBox>
                ))}
            </Week>
          ))}
      </Month>
      {dateModal && (
        <PromiseDateModal
          state={{ dateModal, setDateModal, currentDate }}
          data={TEST_DATA}
        />
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
  height: ${(props) => (props.height ? props.height : "8vh")};
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
`;
const Button = styled.div<{ selected: boolean }>`
  display: flex;
  flex-direction: row;
  cursor: pointer;
  font-size: 0.7em;
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
