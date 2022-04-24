import { AnimatePresence, motion } from "framer-motion";
import React, { useRef, useState } from "react";
import styled from "styled-components";
import { Profile, ProfileImg } from "../atoms/Profile";
import { Input } from "../styles/Input";
import { Overlay } from "../styles/Modal";
import { BsCalendarCheck } from "react-icons/bs";
import { selectedState } from "../states/createGroup";
interface IBanData {
  id: number;
  date: string;
  reason: string;
  date_status: string;
}
interface IMyPageModal {
  state: { dateModal: boolean; setDateModal: Function; currentDate: string };
  data: IBanData[];
}
const DAYS_OF_WEEK = ["일", "월", "화", "수", "목", "금", "토"];
const RED = "RED";
const YELLOW = "YELLOW";
const GREEN = "GREEN";
const COLOR = { RED: "#ff7373", YELLOW: "#ffd37a", GREEN: "#85ba73" };

function MyPageModal({ state, data }: IMyPageModal) {
  const { dateModal, setDateModal, currentDate } = state;
  const [reason, setReason] = useState("");
  const [privateReason, setPrivateReason] = useState(false);
  const [selectedStatus, setSelectedStatus] = useState(GREEN);
  const onReasonSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log(selectedStatus, reason, privateReason);
    setReason("");
    setPrivateReason(false);
  };

  const onReasonChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setReason(e.target.value);
  };
  const onPrivateChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPrivateReason(e.target.checked);
  };
  const onOverlayClick = () => {
    setDateModal((p: boolean) => !p);
    setReason("");
    setPrivateReason(false);
    setSelectedStatus(GREEN);
  };
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

  const getStatus = () => {
    for (let i = 0; i < data.length; i++) {
      if (data[i].date === currentDate) {
        return data[i].date_status;
      }
    }
    return selectedStatus;
  };
  const onStatusClick = (status: string) => {
    if (!getReason()) {
      setSelectedStatus(status);
    }
  };

  const getReason = () => {
    for (let i = 0; i < data.length; i++) {
      if (data[i].date === currentDate && data[i].reason) {
        return data[i].reason;
      }
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
            <Elements>
              <Buttons>
                <Button
                  onClick={() => onStatusClick(RED)}
                  selected={getStatus() === RED && true}
                  color={RED}
                >
                  <Circle color={COLOR[RED]} />
                  불가능
                </Button>
                <Button
                  onClick={() => onStatusClick(YELLOW)}
                  selected={getStatus() === YELLOW && true}
                  color={YELLOW}
                >
                  <Circle color={COLOR[YELLOW]} />
                  애매함
                </Button>
                <Button
                  onClick={() => onStatusClick(GREEN)}
                  selected={getStatus() === GREEN && true}
                  color={GREEN}
                >
                  <Circle color={COLOR[GREEN]} />
                  가능
                </Button>
              </Buttons>
            </Elements>
            {getReason() ? (
              <div
                style={{
                  background: "transparent",
                  display: "flex",
                  flexDirection: "row",
                  gap: "0.5em",
                }}
              >
                <BsCalendarCheck />
                {getReason()}
              </div>
            ) : (
              <form
                style={{
                  background: "transparent",
                  display: "flex",
                  flexDirection: "column",
                  gap: "0.5em",
                }}
                onSubmit={onReasonSubmit}
              >
                <Input
                  onChange={onReasonChange}
                  value={reason}
                  placeholder="메모"
                />
                <label style={{ background: "transparent" }}>
                  <input onChange={onPrivateChange} type="checkbox" /> 나만 보기
                </label>
              </form>
            )}
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
            onClick={onOverlayClick}
          />
        </>
      )}
    </AnimatePresence>
  );
}

export default MyPageModal;
const Modal = styled(motion.div)`
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  position: fixed;
  top: 8em;
  border-radius: 1em;
  background-color: ${(p) => p.theme.smoke};
  color: ${(p) => p.theme.textColor};
  width: 80%;
  @media screen and (min-width: 900px) {
    width: 40%;
  }
  height: 30vh;
  padding: 2em;
  z-index: 3;
  span {
    background-color: transparent;
  }
`;
const Elements = styled.div`
  display: flex;
  flex-direction: row;
  width: 90%;
  justify-content: space-between;
  background-color: transparent;
`;
const Buttons = styled.div`
  display: flex;
  flex-direction: row;
  gap: 0.5em;
  width: 100%;
  background-color: transparent;
`;
const Button = styled.div<{ selected?: boolean }>`
  display: flex;
  flex-direction: row;
  cursor: pointer;
  background-color: transparent;
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
