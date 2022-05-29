import React, { useState } from "react";
import { useLocation, useNavigate } from "react-router";
import { useRecoilState, useRecoilValue } from "recoil";
import styled from "styled-components";
import * as S from "../styles/_index";
import ArrowBack from "../atoms/ArrowBack";
import { darkModeState } from "../states/darkmode";
import BasedTemplate from "../template/BasedTemplate";
import { AiOutlinePlus } from "react-icons/ai";
import { HiOutlineLocationMarker } from "react-icons/hi";
import { selectedGroupState } from "../states/selectedGroup";
import { RoundElement } from "../organisms/RoundElement";
import * as A from "../atoms/_index";
import AddModal from "../organisms/AddModal";
import { AiOutlineMenu } from "react-icons/ai";
import { ListContainer } from "../styles/Base";
import { useMutation, useQuery } from "react-query";
import api from "../auth/api";

const TEST_PROMISE = [
  {
    id: 1,
    title: "정기회의",
    location: "단국대학교",
    date: "2022-02-26",
  },
  {
    id: 2,
    title: "정기회의",
    location: "단국대학교",
    date: "2022-02-27",
  },
  {
    id: 3,
    title: "정기회의",
    location: "단국대학교",
    date: "2022-02-28",
  },
  {
    id: 4,
    title: "정기회의",
    location: "단국대학교",
    date: "2022-03-01",
  },
];

interface IPromise {
  month?: string;
  date?: string;
  location?: string;
}

function Promise() {
  const [showModal, setShowModal] = useState(false);
  const [showPromises, setShowPromises] = useState(true);
  const selectedGroup = useRecoilValue(selectedGroupState);
  console.log(selectedGroup);
  const navigate = useNavigate();
  const dark = useRecoilValue(darkModeState);

  const { data: promiseListData } = useQuery("promiseList", async () => {
    const { data } = await api.get(`/promises/${selectedGroup.id}`);
    return data;
  });

  const { mutate: createPromise } = useMutation("createPromise", async () => {
    return await api
      .post(`/promises/${selectedGroup.id}`)
      .catch((err) => console.log(err));
  });

  console.log(promiseListData);
  const onListClick = (index: number) => {
    navigate(`${index}`);
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
    return <FlatDate>{month + "/" + date}</FlatDate>;
  };

  const Container = () => {
    return (
      <>
        {showPromises && promiseListData &&
            <ListContainer>
              {promiseListData.map((value: { id: React.Key; date: string; title: string; location: string; }, index: number) => (
                  <RoundElement
                      onClick={() => onListClick(index)}
                      key={value.id}
                      head={
                        <Head
                            month={value.date.split("-")[1]}
                            date={value.date.split("-")[2]}
                        />
                      }
                      main={value.title}
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
        }
        {!showModal && (
          <A.CreateBtn value={"약속 생성"} onClick={onCreateClick} />
        )}

        <AddModal
          props={{
            titleText: "약속 생성",
            placeholderText: "어떤 약속인가요?",
            btnText: "생성",
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
