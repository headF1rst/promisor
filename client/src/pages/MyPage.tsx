import React, { useEffect, useState } from "react";
import styled from "styled-components";
import { VscTriangleLeft, VscTriangleRight } from "react-icons/vsc";
import MyPageModal from "../organisms/MyPageModal";
const TEST_DATA = [
  { id: 1, date: "20220425", reason: "산하 생일", date_status: "RED" },
];

const RED = "RED";
const YELLOW = "YELLOW";
const GREEN = "GREEN";
const DAYS_OF_WEEK = ["일", "월", "화", "수", "목", "금", "토"];

function MyPage() {
  const now = new Date();

  const [year, setYear] = useState(now.getFullYear());
  const [month, setMonth] = useState(now.getMonth());
  const [weeks, setWeeks] = useState<any[][]>([]);
  const [dateModal, setDateModal] = useState(false);
  const [currentDate, setCurrentDate] = useState("");

  useEffect(() => {
    makeCalendar(year, month);
  }, [month]);
  const makeCalendar = (year: number, month: number) => {
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
            String(month + 1).padStart(2, "0") +
            String(date).padStart(2, "0");
          for (let i = 0; i < TEST_DATA.length; i++) {
            if (TEST_DATA[i].date === id && TEST_DATA[i].date_status) {
              thisColor = TEST_DATA[i].date_status;
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
  const onDateClick = (id: string) => {
    if (!id) return;
    setCurrentDate(id);
    setDateModal(true);
  };
  return (
    <div>
      <ProfileContainer>
        <ProfileImg
          src={
            "https://i.pinimg.com/originals/52/79/56/527956555b06e74f0bcf9a55601c1c76.jpg"
          }
        />
        <span>김채은</span>
      </ProfileContainer>
      <CalendarContainer>
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
                  week.map((date, day_idx) => (
                    <DateBox
                      key={String(week_idx) + String(day_idx)}
                      color={date.color}
                      style={{ color: "black" }}
                      onClick={() => onDateClick(date.id)}
                    >
                      {date.value}
                    </DateBox>
                  ))}
              </Week>
            ))}
        </Month>
      </CalendarContainer>
      <MyPageModal
        state={{ dateModal, setDateModal, currentDate }}
        data={TEST_DATA}
      />
    </div>
  );
}

export default MyPage;

const ProfileContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1em;
  span {
    font-size: 1.2em;
  }
  margin-bottom: 3em;
`;
const ProfileImg = styled.img`
  width: 5em;
  height: 5em;
  border-radius: 7em;
`;

const CalendarContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
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
  height: ${(props) => (props.height ? props.height : "5vh")};
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
