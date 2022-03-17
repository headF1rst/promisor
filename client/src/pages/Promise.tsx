import React from "react";
import { useLocation, useNavigate } from "react-router";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import * as S from "../styles/_index";
import ArrowBack from "../organisms/ArrowBack";
import { darkModeState } from "../states/darkmode";
import BasedTemplate from "../template/BasedTemplate";
import { AiOutlinePlus } from "react-icons/ai";
import { HiOutlineLocationMarker } from "react-icons/hi";
import { selectedGroupState } from "../states/selectedGroup";
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

function Promise() {
  const selectedGroup = useRecoilValue(selectedGroupState);
  const navigate = useNavigate();
  const dark = useRecoilValue(darkModeState);
  const Header = () => {
    const onCreateClick = () => {
      navigate(`${Date.now()}`);
    };
    return (
      <>
        <ArrowBack />
        <PromiseTitle>
          약속<span>{selectedGroup.name}</span>
        </PromiseTitle>
        <AiOutlinePlus
          style={{ cursor: "pointer" }}
          color={dark ? "white" : "black"}
          onClick={onCreateClick}
        />
      </>
    );
  };
  const Container = () => {
    return (
      <>
        {" "}
        {TEST_PROMISE &&
          TEST_PROMISE.map((value, index) => (
            <S.FlatList key={value.id}>
              <FlatDate>
                {value.date.split("-")[1] + "/" + value.date.split("-")[2]}
              </FlatDate>
              <S.FlatElement>
                <div>{value.title}</div>
                {value.location && (
                  <span>
                    <HiOutlineLocationMarker
                      color={dark ? "#c4c4c4" : "#595959"}
                      style={{
                        position: "relative",
                        top: "0.1em",
                        marginRight: "0.2em",
                      }}
                    />
                    {value.location}
                  </span>
                )}
              </S.FlatElement>
            </S.FlatList>
          ))}
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
`;
