import React, { useState } from "react";
import { useRecoilValue } from "recoil";
import styled from "styled-components";
import ArrowBack from "../atoms/ArrowBack";
import { selectedGroupState } from "../states/selectedGroup";
import BasedTemplate from "../template/BasedTemplate";
import { PromiseTitle } from "./Promise";
import PromiseDate from "./PromiseDate";

function PromiseDetail() {
  const selectedGroup = useRecoilValue(selectedGroupState);
  const [navDate, setNavDate] = useState(true);
  const onNavClick = (state: boolean) => {
    setNavDate(state);
  };
  const Header = () => {
    return (
      <>
        {" "}
        <ArrowBack />
        <PromiseTitle>
          약속<span>{selectedGroup.name}</span>
        </PromiseTitle>
        <span>저장</span>
      </>
    );
  };
  const Container = () => {
    return (
      <>
        <LineInput placeholder="제목" />
        <NavBar>
          <NavItem
            onClick={() => onNavClick(true)}
            style={{ fontWeight: navDate ? "600" : "500" }}
          >
            일시
          </NavItem>
          <NavItem
            onClick={() => onNavClick(false)}
            style={{ fontWeight: navDate ? "500" : "600" }}
          >
            장소
          </NavItem>
        </NavBar>
        {navDate && <PromiseDate />}
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
