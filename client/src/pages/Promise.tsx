import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import ArrowBack from "../atoms/ArrowBack";
import { darkModeState } from "../states/darkmode";
import BasedTemplate from "../template/BasedTemplate";
import { HiOutlineLocationMarker } from "react-icons/hi";
import { selectedGroupState } from "../states/selectedGroup";
import { RoundElement } from "../organisms/RoundElement";
import * as A from "../atoms/_index";
import { ListContainer } from "../styles/Base";
import { useMutation, useQuery, useQueryClient } from "react-query";
import api from "../auth/api";
import { useParams } from "react-router";
import { BsFillCalendarMinusFill } from "react-icons/bs";
import { useForm } from "react-hook-form";
import * as S from "../styles/_index";
import { motion } from "framer-motion";
interface IPromise {
  month?: string;
  date?: string;
  location?: string;
}
interface IPromiseList {
  id: number;
  name: string;
  time: string;
  location: string;
}
interface IPromiseNameForm {
  promiseName: string;
}
interface IPromiseNameInvalid {
  promiseName?: {
    message?: string;
  };
}
function Promise() {
  const selectedGroup = useRecoilValue(selectedGroupState);
  const params = useParams();
  const [showModal, setShowModal] = useState(false);

  const dark = useRecoilValue(darkModeState);
  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const { register, setValue, handleSubmit } = useForm<IPromiseNameForm>();

  const { data: promiseListData, error } = useQuery<IPromiseList[]>(
    ["promiseList", params.id],
    async () => {
      const { data } = await api.get(`/promises/list/${params.id}`);
      return data;
    }
  );
  const { mutate: createPromise } = useMutation(
    async (promiseName: string) => {
      return await api.post(`/promises/${params.id}`, {
        name: promiseName,
      });
    },
    {
      onSuccess: () => {
        queryClient.invalidateQueries("promiseList");
      },
    }
  );

  const onListClick = (index: number) => {
    navigate(`${index}/date`);
  };

  const onCreateClick = () => {
    setShowModal(true);
    setValue("promiseName", "");
  };

  const onPromiseValid = ({ promiseName }: IPromiseNameForm) => {
    createPromise(promiseName);
    setShowModal(false);
  };
  const onPromiseInvalid = ({ promiseName }: IPromiseNameInvalid) => {
    alert(promiseName.message);
  };
  const onOverlayClick = () => {
    setShowModal(false);
  };
  const opacityVariants = {
    initial: {
      opacity: 0,
    },
    animate: {
      opacity: 1,
    },
    exit: {
      opacity: 0,
    },
  };
  const Header = () => {
    return (
      <div
        style={{
          width: "100vw",
          display: "flex",
          flexDirection: "row",
          justifyContent: "space-around",
          alignItems: "center",
        }}
      >
        <ArrowBack />
        <PromiseTitle>
          약속<span>{selectedGroup.name}</span>
        </PromiseTitle>
        <span></span>
      </div>
    );
  };

  const Head = ({ month, date }: IPromise) => {
    if (month && date) {
      return <FlatDate>{month + "/" + date}</FlatDate>;
    } else {
      return (
        <FlatDate>
          <BsFillCalendarMinusFill />
        </FlatDate>
      );
    }
  };

  const Container = () => {
    return (
      <>
        {promiseListData && (
          <ListContainer>
            {promiseListData &&
              promiseListData.map((value, index) => (
                <RoundElement
                  onClick={() => onListClick(value.id)}
                  key={value.id}
                  head={
                    <Head
                      month={value.time ? value.time.split("-")[1] : ""}
                      date={value.time ? value.time.split("-")[2] : ""}
                    />
                  }
                  main={value.name}
                  sub={
                    value.location && (
                      <A.IconText
                        icon={
                          <HiOutlineLocationMarker
                            color={dark ? "#c4c4c4" : "#595959"}
                          />
                        }
                        text={value.location}
                      />
                    )
                  }
                />
              ))}
          </ListContainer>
        )}
        {!showModal && (
          <A.CreateBtn value={"약속 생성"} onClick={onCreateClick} />
        )}

        {showModal && (
          <>
            <Modal
              key="promiseModal"
              initial="initial"
              animate="animate"
              exit="exit"
              variants={opacityVariants}
            >
              <span>약속 생성</span>
              <Form onSubmit={handleSubmit(onPromiseValid, onPromiseInvalid)}>
                <S.LabelInput>
                  <S.Input
                    placeholder={"어떤 약속인가요?"}
                    {...register("promiseName", {
                      required: "약속 이름을 입력해주세요",
                      maxLength: {
                        value: 20,
                        message: "약속 이름은 20자 이하여야 합니다.",
                      },
                    })}
                  />
                </S.LabelInput>
                <A.RoundBtn center={true} value={"생성"} />
              </Form>
            </Modal>
            <S.Overlay
              key={"promiseOverlay"}
              initial="initial"
              animate="animate"
              exit="exit"
              variants={opacityVariants}
              onClick={onOverlayClick}
            />
          </>
        )}
      </>
    );
  };
  return <BasedTemplate header={<Header />} container={<Container />} />;
}

export default Promise;
export const PromiseTitle = styled.span`
  display: flex;
  flex-direction: column;
  align-items: center;
  span {
    color: ${(p) => p.theme.grey};
    font-size: 0.8em;
    margin-top: 0.1em;
  }
`;
const FlatDate = styled.span`
  font-size: 0.9em;
  margin-right: 1em;
  background-color: transparent;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const Modal = styled(motion.div)`
  display: flex;
  flex-direction: column;
  position: fixed;
  border-radius: 1em;
  background-color: ${(p) => p.theme.smoke};
  color: ${(p) => p.theme.textColor};
  width: 80%;
  @media screen and (min-width: 900px) {
    width: 40%;
  }
  height: 14em;
  padding: 2em;
  justify-content: space-around;
  z-index: 3;
  span {
    background-color: transparent;
  }
  input {
    margin-bottom: 1em;
  }
`;
const Form = styled.form`
  flex-direction: column;
  display: flex;
  justify-content: space-between;
`;
