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

  const routeMatch = {
    group: groupMatch,
    friend: friendMatch,
  };

  const HomeHeader = () => {
    return <A.Logo value={"Promisor"} />;
  };
  const Container = () => {
    return friendMatch ? <Friend /> : groupMatch ? <Group /> : <MyPage />;
  };
  return (
    <>
      <BasedTemplate header={<HomeHeader />} container={<Container />} />
    </>
  );
}

export default React.memo(Home);
