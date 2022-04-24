import { AnimatePresence, motion } from "framer-motion";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Link, useMatch } from "react-router-dom";
import { useRecoilState } from "recoil";
import styled from "styled-components";
import * as A from "../atoms/_index";
import { darkModeState } from "../states/darkmode";
import Friend from "./Friend";
import Group from "./Group";
import { AiOutlineMenu } from "react-icons/ai";
import BasedTemplate from "../template/BasedTemplate";
import SideMenu from "../organisms/SideMenu";
import MyPage from "./MyPage";

function Home() {
  const groupMatch = useMatch("team");
  const friendMatch = useMatch("friend");
  const [side, setSide] = useState(false);
  const onSideClick = () => {
    setSide((prev) => !prev);
  };

  const routeMatch = {
    group: groupMatch,
    friend: friendMatch,
  };

  const Header = () => {
    return (
      <>
        <A.Logo value={"P"} />
        {friendMatch ? "친구" : groupMatch && "그룹"}
        <AiOutlineMenu style={{ cursor: "pointer" }} onClick={onSideClick} />
      </>
    );
  };
  const HomeHeader = () => {
    return (
      <>
        <span></span>
        <A.Logo value={"Promisor"} />
        <AiOutlineMenu style={{ cursor: "pointer" }} onClick={onSideClick} />
      </>
    );
  };
  const Container = () => {
    return friendMatch ? <Friend /> : groupMatch ? <Group /> : <MyPage />;
  };
  return (
    <>
      <SideMenu match={routeMatch} side={side} setSide={setSide} />
      <BasedTemplate
        header={groupMatch || friendMatch ? <Header /> : <HomeHeader />}
        container={<Container />}
      />
    </>
  );
}

export default React.memo(Home);
