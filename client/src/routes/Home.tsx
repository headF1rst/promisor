import { AnimatePresence, motion } from "framer-motion";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Link, useMatch } from "react-router-dom";
import { useRecoilState } from "recoil";
import styled from "styled-components";
import * as S from "../atoms/_index";
import { darkModeState } from "../states/darkmode";
import Friend from "./Friend";
import Group from "./Group";
import { AiOutlineMenu } from "react-icons/ai";

function Home() {
  const [side, setSide] = useState(false);
  const [dark, setDark] = useRecoilState(darkModeState);

  const groupMatch = useMatch("group");
  const friendMatch = useMatch("friend");
  const settingMatch = useMatch("setting");
  const navigate = useNavigate();

  const sideVariants = {
    init: { scaleX: 0 },
    animate: { scaleX: 1 },
    exit: { scaleX: 0 },
  };

  const onSideClick = () => {
    setSide((prev) => !prev);
  };
  const onTabClick = (tab: string) => {
    navigate(`/${tab}`);
    setSide(false);
  };
  const onToggleClick = () => {
    setDark((prev: boolean) => !prev);
  };

  return (
    <S.Template>
      <S.Header items={3}>
        <span></span>
        {groupMatch ? "그룹" : friendMatch ? "친구" : settingMatch && "설정"}
        <AiOutlineMenu style={{ cursor: "pointer" }} onClick={onSideClick} />
      </S.Header>
      <AnimatePresence>
        {side && (
          <>
            <SideBar
              variants={sideVariants}
              initial="init"
              animate="animate"
              exit="exit"
              transition={{ type: "linear" }}
            >
              <S.Logo style={{ fontSize: "2.5em" }}>Promisor</S.Logo>
              <SideList
                style={{ color: groupMatch ? "#04C994" : "none" }}
                onClick={() => onTabClick("group")}
              >
                그룹
              </SideList>
              <SideList
                style={{ color: friendMatch ? "#04C994" : "none" }}
                onClick={() => onTabClick("friend")}
              >
                친구
              </SideList>
              <SideList
                style={{ color: settingMatch ? "#04C994" : "none" }}
                onClick={() => onTabClick("setting")}
              >
                설정
              </SideList>
              <DarkmodeToggle
                dark={dark ? "dark" : "light"}
                onClick={onToggleClick}
              >
                {dark ? (
                  <Circle layoutId="darkmode" dark={dark ? "dark" : "light"} />
                ) : (
                  <Circle layoutId="darkmode" dark={dark ? "dark" : "light"} />
                )}
              </DarkmodeToggle>
            </SideBar>
            <S.Overlay
              initial={{ opacity: 0 }}
              animate={{ opacity: 1 }}
              exit={{ opacity: 0 }}
              onClick={onSideClick}
            />
          </>
        )}
      </AnimatePresence>

      {groupMatch ? <Group /> : friendMatch && <Friend />}
    </S.Template>
  );
}

export default Home;

const SideBar = styled(motion.div)`
  position: fixed;
  top: 0;
  right: 0;
  width: 60%;
  @media screen and (min-width: 900px) {
    width: 30%;
  }
  height: 100%;
  z-index: 10;
  background-color: ${(p) => p.theme.bgColor};
  display: flex;
  flex-direction: column;
  transform-origin: right center;
`;

const SideList = styled(S.ProfileList)`
  background-color: transparent;
  font-weight: bold;
  margin-left: 1em;
`;

const DarkmodeToggle = styled(motion.div)<{ dark: string }>`
  background-color: ${(p) =>
    p.dark === "dark" ? p.theme.green : p.theme.bgColor};
  border: solid 2px
    ${(p) => (p.dark === "dark" ? "transparent" : p.theme.green)};
  left: 1em;
  bottom: 1em;
  position: absolute;
  width: 46px;
  height: 28px;
  border-radius: 20px;
  cursor: pointer;
`;
const Circle = styled(motion.div)<{ dark: string }>`
  position: relative;
  left: ${(p) => (p.dark === "dark" ? "20px" : "2px")};
  top: 2px;
  width: 20px;
  height: 20px;
  border-radius: 20px;
  background-color: ${(p) => (p.dark === "dark" ? "white" : p.theme.green)};
`;
