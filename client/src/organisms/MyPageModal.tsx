import { AnimatePresence, motion } from "framer-motion";
import React, { useRef, useState } from "react";
import styled from "styled-components";
import { Profile, ProfileImg } from "../atoms/Profile";
import { Input } from "../styles/Input";
import { Overlay } from "../styles/Modal";
import { AiTwotoneLock } from "react-icons/ai";
import { BsCheckCircle } from "react-icons/bs";
import { FaChevronCircleRight, FaChevronCircleLeft } from "react-icons/fa";
interface IReasonData {
  id: number;
  date: string;
  reason: string;
}
interface IDateStatusData {
  id: number;
  date: string;
  date_status: string;
}
interface IMyPageModal {
  state: { dateModal: boolean; setDateModal: Function; currentDate: string };
  data: { reasonData: IReasonData[]; statusData: IDateStatusData[] };
}
const DAYS_OF_WEEK = ["일", "월", "화", "수", "목", "금", "토"];

function MyPageModal({ state, data }: IMyPageModal) {
  const { dateModal, setDateModal, currentDate } = state;
  const { reasonData, statusData } = data;
  const [reason, setReason] = useState("");
  const [privateReason, setPrivateReason] = useState(false);
  const [selectedStatus, setSelectedStatus] = useState(false);
  const [changeStatus, setChangeStatus] = useState(false);
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
  const onStatusChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setSelectedStatus(e.target.checked);
  };
  const onOverlayClick = () => {
    setDateModal((p: boolean) => !p);
    setReason("");
    setPrivateReason(false);
    setSelectedStatus(false);
    setChangeStatus(false);
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
  const getColor = (date_status: string) => {
    if (date_status === "RED") {
      return "#ff7373";
    } else if (date_status === "YELLOW") {
      return "#ffd37a";
    } else {
      return "#85ba73";
    }
  };
  const getDateStatus = (date: string) => {
    for (let i = 0; i < statusData.length; i++) {
      if (statusData[i].date === date) {
        return statusData[i].date_status;
      }
    }
    return "GREEN";
  };
  const onStatusSubmit = (date_status: string) => {
    // 해당 날짜의 status 변경 api 호출
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
            <RowElement>
              {currentDate.slice(4, 6) +
                "월 " +
                currentDate.slice(6, 8) +
                "일 " +
                getDayFromCurrentDate() +
                "요일"}
              <span
                style={{ cursor: "pointer", marginTop: "0.1em" }}
                onClick={() => setChangeStatus((p) => !p)}
              >
                {changeStatus ? (
                  <FaChevronCircleLeft
                    color={getColor(getDateStatus(currentDate))}
                  />
                ) : (
                  <FaChevronCircleRight
                    color={getColor(getDateStatus(currentDate))}
                  />
                )}
              </span>

              {changeStatus && (
                <>
                  <Circle
                    onClick={() => onStatusSubmit("RED")}
                    color={getColor("RED")}
                  />
                  <Circle
                    onClick={() => onStatusSubmit("YELLOW")}
                    color={getColor("YELLOW")}
                  />
                  <Circle
                    onClick={() => onStatusSubmit("GREEN")}
                    color={getColor("GREEN")}
                  />
                </>
              )}
            </RowElement>
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
                placeholder="메모 추가..."
              />
            </form>
            <List>
              {reasonData.map(
                (value, idx) =>
                  value.date === currentDate &&
                  value.reason && (
                    <RowElement key={value.id}>
                      <span style={{ marginTop: "2px" }}>
                        <BsCheckCircle />
                      </span>
                      {value.reason}
                    </RowElement>
                  )
              )}
            </List>
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
  gap: 1em;
  position: fixed;
  top: 8em;
  border-radius: 1em;
  background-color: ${(p) => p.theme.smoke};
  color: ${(p) => p.theme.textColor};
  width: 80%;
  @media screen and (min-width: 900px) {
    width: 40%;
  }
  height: 50vh;
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
  margin-top: 0.5em;
  overflow-y: scroll;
  gap: 0.5em;
`;

const RowElement = styled.div`
  background: transparent;
  display: flex;
  flex-direction: row;
  gap: 0.5em;
`;
const Circle = styled.div<{ color: string }>`
  width: 1em;
  height: 1em;
  border-radius: 1em;
  background-color: ${(p) => p.color};
  margin-right: 0.3em;
  margin-top: 0.1em;
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
