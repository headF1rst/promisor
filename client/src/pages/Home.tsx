import { AnimatePresence, motion } from "framer-motion";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Link, useMatch } from "react-router-dom";
import { useRecoilState } from "recoil";
import styled from "styled-components";
import * as S from "../styles/_index";
import { darkModeState } from "../states/darkmode";
import Friend from "./Friend";
import Group from "./Group";
import { AiOutlineMenu } from "react-icons/ai";
import Setting from "./Setting";
import BasedTemplate from "../template/BasedTemplate";
import SideMenu from "../organisms/SideMenu";

function Home() {
  const groupMatch = useMatch("group");
  const friendMatch = useMatch("friend");
  const settingMatch = useMatch("setting");
  const [side, setSide] = useState(false);
  const onSideClick = () => {
    setSide((prev) => !prev);
  };

  const routeMatch = {
    group: groupMatch,
    friend: friendMatch,
    setting: settingMatch,
  };

  const Header = () => {
    return (
      <>
        <span></span>
        {friendMatch ? "친구" : settingMatch ? "설정" : "그룹"}
        <AiOutlineMenu style={{ cursor: "pointer" }} onClick={onSideClick} />
      </>
    );
  };
  const Container = () => {
    return friendMatch ? <Friend /> : settingMatch ? <Setting /> : <Group />;
  };
  return (
    <>
      <SideMenu match={routeMatch} side={side} setSide={setSide} />
      <BasedTemplate header={<Header />} container={<Container />} />
    </>
  );
}

export default React.memo(Home);
