import React from "react";
import styled from "styled-components";
import { useMatch, useNavigate } from "react-router-dom";
import { useParams } from "react-router";

function PromiseDetailNav() {
  const navigate = useNavigate();
  const params = useParams();
  const dateMatch = useMatch("/team/:id/promise/:pid/date");
  const onNavClick = (state: string) => {
    if (state === "place") {
      navigate(`/team/${params.id}/promise/${params.pid}/${state}`, {
        replace: true,
      });
    } else {
      navigate(`/team/${params.id}/promise/${params.pid}/${state}`);
    }
  };
  return (
    <NavBar>
      <NavItem
        onClick={() => onNavClick("date")}
        dateMatch={dateMatch ? true : false}
      >
        일시
      </NavItem>
      <NavItem
        onClick={() => onNavClick("place")}
        dateMatch={dateMatch ? false : true}
      >
        장소
      </NavItem>
    </NavBar>
  );
}

export default PromiseDetailNav;

const NavBar = styled.div`
  display: flex;
  width: 80%;
  height: 6vh;
  background-color: transparent;
  margin-block: 1em;
  justify-content: space-between;
  align-items: center;
`;
const NavItem = styled.span<{ dateMatch: boolean }>`
  background-color: transparent;
  cursor: pointer;
  font-size: ${(p) => (p.dateMatch ? "1em" : "0.9em")};
  font-weight: ${(p) => (p.dateMatch ? 700 : 300)};
`;
