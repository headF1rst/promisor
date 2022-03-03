import React from "react";
import { Link, useMatch } from "react-router-dom";
import styled from "styled-components";
import * as S from "../components/_index";
import Group from "./Group";

function Home() {
  const groupMatch = useMatch("group");
  const friendMatch = useMatch("friend");
  return (
    <>
      <HomeHeader items={1}>
        {groupMatch ? "그룹" : friendMatch ? "친구" : "설정"}
      </HomeHeader>
      <S.Template>{groupMatch && <Group />}</S.Template>
    </>
  );
}

export default Home;

const HomeHeader = styled(S.Header)`
  position: fixed;
  height: 10vh;
  width: 100%;
  background-color: ${(p) => p.theme.bgColor};
  padding-inline: 3rem;
  font-weight: 500;
`;
