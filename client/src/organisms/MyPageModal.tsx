import { AnimatePresence, motion } from "framer-motion";
import React, { useRef, useState } from "react";
import styled from "styled-components";
import { Input } from "../styles/Input";
import { Overlay } from "../styles/Modal";
import { BsCheckCircle } from "react-icons/bs";
import { FaChevronCircleRight, FaChevronCircleLeft } from "react-icons/fa";
import { dateFormatter } from "../utils/dateFormatter";
import { useMutation, useQuery, useQueryClient } from "react-query";
import api from "../auth/api";
interface IBanDateInfo {
  id: number;
  date: string;
  dateStatus: string;
  reason: string[];
}
interface IMyPageModal {
  state: { dateModal: boolean; setDateModal: Function; currentDate: string };
}
const DAYS_OF_WEEK = ["일", "월", "화", "수", "목", "금", "토"];

function MyPageModal({ state }: IMyPageModal) {
  const { dateModal, setDateModal, currentDate } = state;
  const [reason, setReason] = useState("");
  const [changeStatus, setChangeStatus] = useState(false);
  const queryClient = useQueryClient();
  const tokenId = localStorage.getItem("refreshTokenId");

  const { mutate: createReason } = useMutation(
    ["createReason", currentDate],
    async () => {
      const requestBody = {
        date: dateFormatter(currentDate),
        reason,
      };
      return await api.post("/bandate/personal/reason", requestBody);
    },
    {
      onSuccess: () => {
        queryClient.invalidateQueries(["getBandateInfo"]);
      },
    }
  );
  const { data: bandateInfo } = useQuery<IBanDateInfo>(
    ["getBandateInfo", currentDate],
    async () => {
      const { data } = await api.get(
        `/bandate/personal/reason/${dateFormatter(currentDate)}`
      );
      return data;
    }
  );
  const onReasonSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    createReason();
    setReason("");
  };

  const onReasonChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setReason(e.target.value);
  };

  const onOverlayClick = () => {
    setDateModal((p: boolean) => !p);
    setReason("");
    setChangeStatus(false);
  };
  const getDayFromCurrentDate = () => {
    const strDate = dateFormatter(currentDate);
    const day = new Date(strDate).getDay();
    return DAYS_OF_WEEK[day];
  };
  const getColor = (date_status: string) => {
    if (date_status === "IMPOSSIBLE") {
      return "#ff7373";
    } else if (date_status === "UNCERTAIN") {
      return "#ffd37a";
    } else {
      return "#85ba73";
    }
  };
  const onStatusSubmit = async (date_status: string) => {
    const requestBody = {
      date: dateFormatter(currentDate),
      status: date_status,
    };
    await api.post("/bandate/personal", requestBody).then((res) => {
      queryClient.invalidateQueries("getBandateInfo");
      queryClient.invalidateQueries("personalBandateStatus");
    });
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
                {changeStatus && bandateInfo ? (
                  <FaChevronCircleLeft
                    color={getColor(bandateInfo?.dateStatus)}
                  />
                ) : (
                  <FaChevronCircleRight
                    color={getColor(bandateInfo?.dateStatus)}
                  />
                )}
              </span>

              {changeStatus && (
                <>
                  <Circle
                    onClick={() => onStatusSubmit("IMPOSSIBLE")}
                    color={getColor("IMPOSSIBLE")}
                  />
                  <Circle
                    onClick={() => onStatusSubmit("UNCERTAIN")}
                    color={getColor("UNCERTAIN")}
                  />
                  <Circle
                    onClick={() => onStatusSubmit("POSSIBLE")}
                    color={getColor("POSSIBLE")}
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
              {bandateInfo?.reason?.map((value, idx) => (
                <RowElement key={idx}>
                  <span style={{ marginTop: "2px" }}>
                    <BsCheckCircle />
                  </span>
                  {value}
                </RowElement>
              ))}
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
