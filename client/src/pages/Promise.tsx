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
import AddModal from "../organisms/AddModal";
import { ListContainer } from "../styles/Base";
import { useMutation, useQuery, useQueryClient } from "react-query";
import api from "../auth/api";
import { getCookie } from "../Cookie";
import { useParams } from "react-router";
import { BsFillCalendarMinusFill } from "react-icons/bs";
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

function Promise() {
  const selectedGroup = useRecoilValue(selectedGroupState);
  const params = useParams();
  const [promiseName, setPromiseName] = useState("");
  const [showModal, setShowModal] = useState(false);

  const dark = useRecoilValue(darkModeState);
  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const { data: promiseListData, error } = useQuery<IPromiseList[]>(
    ["promiseList", params.id],
    async () => {
      const { data } = await api.get(`/promises/list/${params.id}`);
      return data;
    }
  );
  const { mutate: createPromise } = useMutation(
    async () => {
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

  const onBtnClick = () => {
    createPromise();
    setShowModal(false);
  };
  const onCreateClick = () => {
    setShowModal(true);
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

        <AddModal
          props={{
            titleText: "약속 생성",
            placeholderText: "어떤 약속인가요?",
            btnText: "생성",
            inputValue: promiseName,
            setInputValue: setPromiseName,
            onBtnClick,
            showModal,
            setShowModal,
          }}
        />
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
