import { AnimatePresence, motion } from "framer-motion";
import React from "react";
import { useQuery } from "react-query";
import { useParams } from "react-router";
import styled from "styled-components";
import api from "../auth/api";
import { Overlay } from "../styles/Modal";
import { dateFormatter } from "../utils/dateFormatter";

interface IPromiseDateModal {
  id: number;
  memberId: number;
  name: string;
  personalBanDateId: number;
  date: string;
  dateStatus: string;
}

interface IProps {
  state: { dateModal: boolean; setDateModal: Function; currentDate: string };
}
const DAYS_OF_WEEK = ["일", "월", "화", "수", "목", "금", "토"];

function PromiseDateModal({ state }: IProps) {
  const { dateModal, setDateModal, currentDate } = state;
  const params = useParams();
  const onChooseClick = () => {};
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

  const { data: teamModalData } = useQuery<IPromiseDateModal[]>(
    ["teamModalData", params.id, currentDate],
    async () => {
      const { data } = await api.get(
        `/bandate/team/${params.id}/detail/${dateFormatter(currentDate)}`
      );
      return data;
    }
  );
  console.log(teamModalData);
  const getPeople = (color: string) => {
    if (!teamModalData) {
      return;
    }
    let visited = Array<number>();
    let peopleStr = "";
    for (let i = 0; i < teamModalData.length; i++) {
      if (
        teamModalData[i].date === dateFormatter(currentDate) &&
        teamModalData[i].dateStatus === color &&
        !visited.includes(teamModalData[i].memberId)
      ) {
        peopleStr += teamModalData[i].name + ", ";
        visited.push(teamModalData[i].memberId);
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
              {teamModalData &&
                ["IMPOSSIBLE", "UNCERTAIN", "POSSIBLE"].map((value, idx) => (
                  <div
                    key={idx}
                    style={{
                      backgroundColor: "transparent",
                    }}
                  >
                    {getPeople(value) && (
                      <RowElement>{getPeople(value)}</RowElement>
                    )}
                  </div>
                ))}
            </List>

            <ChooseButton onClick={onChooseClick}>
              이 날짜 선택하기
            </ChooseButton>
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
  height: 15em;
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
`;
const Element = styled.div`
  display: flex;
  background-color: transparent;
  margin-top: 0.3em;
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
    p.color === "IMPOSSIBLE"
      ? p.theme.flagRed
      : p.color === "UNCERTAIN"
      ? p.theme.flagYellow
      : p.theme.flagGreen};
  margin-right: 0.5em;
`;
const ChooseButton = styled.button`
  background-color: ${(p) => p.theme.textColor};
  color: ${(p) => p.theme.bgColor};
  border: none;
  border-radius: 1em;
  position: absolute;
  bottom: 1em;
  left: 50%;
  transform: translateX(-50%);
`;
