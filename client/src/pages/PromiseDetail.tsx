import React, { useRef, useState } from "react";
import { useMatch } from "react-router-dom";
import { useNavigate } from "react-router";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import ArrowBack from "../atoms/ArrowBack";
import { selectedGroupState } from "../states/selectedGroup";
import BasedTemplate from "../template/BasedTemplate";
import { PromiseTitle } from "./Promise";
import { useParams } from "react-router";
import PromiseDate from "./PromiseDate";
import PromiseLocation from "./PromiseLocation";
import { useQuery } from "react-query";
import api from "../auth/api";
interface IPromiseDetail {
  id: number;
  name: string;
  time: string;
  location: string;
}
function PromiseDetail() {
  const titleRef = useRef<HTMLInputElement>();
  const selectedGroup = useRecoilValue(selectedGroupState);
  const navigate = useNavigate();
  const params = useParams();
  const dateMatch = useMatch("/team/:id/promise/:pid/date");

  const { data: promiseDetailData } = useQuery<IPromiseDetail>(
    "promiseDetailData",
    async () => {
      const { data } = await api.get(`/promises/${params.pid}`);
      return data;
    },
    {
      onSuccess: (data) => {
        titleRef.current.value = data.name;
      },
    }
  );
  const onNavClick = (state: string) => {
    if (state === "place") {
      navigate(`/team/${params.id}/promise/${params.pid}/${state}`, {
        replace: true,
      });
    } else {
      navigate(`/team/${params.id}/promise/${params.pid}/${state}`);
    }
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
        {" "}
        <ArrowBack />
        <PromiseTitle>
          약속<span>{selectedGroup.name}</span>
        </PromiseTitle>
        <span>저장</span>
      </div>
    );
  };
  const Container = () => {
    return (
      <>
        <LineInput placeholder="제목" ref={titleRef} />
        <NavBar>
          <NavItem
            onClick={() => onNavClick("date")}
            style={{ fontWeight: dateMatch ? "600" : "500" }}
          >
            일시
          </NavItem>
          <NavItem
            onClick={() => onNavClick("place")}
            style={{ fontWeight: dateMatch ? "500" : "600" }}
          >
            장소
          </NavItem>
        </NavBar>
        {dateMatch ? (
          <PromiseDate date={promiseDetailData.time} />
        ) : (
          <PromiseLocation location={promiseDetailData.location} />
        )}
      </>
    );
  };
  return <BasedTemplate header={<Header />} container={<Container />} />;
}

export default PromiseDetail;

const LineInput = styled.input`
  width: 80%;
  height: 5vh;
  border: none;
  border-bottom: solid 1px ${(p) => p.theme.grey};
  &:focus {
    outline: none;
  }
  background-color: transparent;
`;
const NavBar = styled.div`
  display: flex;
  width: 80%;
  height: 6vh;
  background-color: transparent;
  margin-block: 1em;
  justify-content: space-between;
  align-items: center;
`;
const NavItem = styled.span`
  background-color: transparent;
  cursor: pointer;
`;
