import { AnimatePresence, motion } from "framer-motion";
import React from "react";
import styled from "styled-components";
import { Profile, ProfileImg } from "../atoms/Profile";
import { Input } from "../styles/Input";
import { Overlay } from "../styles/Modal";
interface IBanData {
  id: number;
  member_id: number;
  member_name: string;
  member_img: string;
  promise_id: number;
  date: string;
  reason: string;
  date_status: string;
}
interface IPromiseDateModal {
  state: { dateModal: boolean; setDateModal: Function; currentDate: string };
  data: IBanData[];
}
const DAYS_OF_WEEK = ["일", "월", "화", "수", "목", "금", "토"];

function PromiseDateModal({ state, data }: IPromiseDateModal) {
  const { dateModal, setDateModal, currentDate } = state;
  const getDayFromCurrentDate = () => {
    const strDate =
      currentDate.slice(0, 4) +
      "-" +
      currentDate.slice(4, 6) +
      "-" +
      currentDate.slice(6, 8);
    const day = new Date(strDate).getDay();
    return DAYS_OF_WEEK[day];
  };
  const getPeople = (color: string) => {
    let peopleStr = "";
    for (let i = 0; i < data.length; i++) {
      if (data[i].date === currentDate && data[i].date_status === color) {
        peopleStr += data[i].member_name + ", ";
      }
    }
    if (peopleStr) {
      return (
        <>
          <Flag color={color} />
          {peopleStr.slice(0, peopleStr.length - 2)}
        </>
      );
    }
    return;
  };
  return (
    <AnimatePresence>
      {dateModal && (
        <>
          <Modal
            initial={{
              opacity: 0,
            }}
            animate={{
              opacity: 1,
            }}
            exit={{
              opacity: 0,
            }}
          >
            <span>
              {currentDate.slice(4, 6) +
                "월 " +
                currentDate.slice(6, 8) +
                "일 " +
                getDayFromCurrentDate() +
                "요일"}
            </span>
            <List>
              {["RED", "YELLOW", "GREEN"].map((value, idx) => (
                <div key={idx} style={{ backgroundColor: "transparent" }}>
                  {getPeople(value) && (
                    <RowElement>{getPeople(value)}</RowElement>
                  )}
                </div>
              ))}
            </List>
            <Input placeholder="메모 추가..." />
            <List>
              {data.map((d, idx) => (
                <div style={{ background: "transparent" }} key={idx}>
                  {d.date === currentDate && d.reason && (
                    <RowElement>
                      <ProfileImg
                        imgProps={{ type: "group", imgSrc: d.member_img }}
                      />
                      <ColElement>
                        <span>{d.member_name}</span>
                        <span>{d.reason}</span>
                      </ColElement>
                    </RowElement>
                  )}
                </div>
              ))}
            </List>
            <ChooseButton>이 날짜 선택하기</ChooseButton>
          </Modal>
          <Overlay
            initial={{
              opacity: 0,
            }}
            animate={{
              opacity: 1,
            }}
            exit={{
              opacity: 0,
            }}
            onClick={() => setDateModal((p: boolean) => !p)}
          />
        </>
      )}
    </AnimatePresence>
  );
}

export default PromiseDateModal;
const Modal = styled(motion.div)`
  display: flex;
  flex-direction: column;
  position: fixed;
  top: 8em;
  border-radius: 1em;
  background-color: ${(p) => p.theme.smoke};
  color: ${(p) => p.theme.textColor};
  width: 80%;
  @media screen and (min-width: 900px) {
    width: 40%;
  }
  height: 70vh;
  padding: 2em;
  z-index: 3;
  span {
    background-color: transparent;
  }
`;
const List = styled.div`
  display: flex;
  flex-direction: column;
  background-color: transparent;
  margin-block: 0.5em;
  overflow-y: scroll;
`;
const Element = styled.div`
  display: flex;
  background-color: transparent;
  margin-top: 0.3em;
  height: 3em;
`;
const RowElement = styled(Element)`
  flex-direction: row;
  align-items: center;
`;
const ColElement = styled(Element)`
  flex-direction: column;
  padding-bottom: 0.5em;
  span:first-child {
    font-weight: 600;
  }
`;
const Flag = styled.div<{ color: string }>`
  width: 0.3em;
  height: 2em;
  background-color: ${(p) =>
    p.color === "RED"
      ? p.theme.flagRed
      : p.color === "YELLOW"
      ? p.theme.flagYellow
      : p.theme.flagGreen};
  margin-right: 0.5em;
`;
const ChooseButton = styled.button`
  background-color: ${(p) => p.theme.textColor};
  color: ${(p) => p.theme.bgColor};
  border: none;
  border-radius: 1em;
`;
