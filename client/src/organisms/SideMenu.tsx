import { AnimatePresence, motion } from "framer-motion";
import React, { useState } from "react";
import { useNavigate } from "react-router";
import { useRecoilState } from "recoil";
import styled from "styled-components";
import { darkModeState } from "../states/darkmode";
import * as S from "../styles/_index";
import * as A from "../atoms/_index";

interface ISideMenu {
  match: {
    group: object | null;
    friend: object | null;
    setting: object | null;
  };
  side: boolean;
  setSide: Function;
}
function SideMenu({ match, side, setSide }: ISideMenu) {
  const [dark, setDark] = useRecoilState(darkModeState);
  const sideVariants = {
    init: { scaleX: 0 },
    animate: { scaleX: 1 },
    exit: { scaleX: 0 },
  };
  const navigate = useNavigate();

  const onTabClick = (tab: string) => {
    navigate(`/${tab}`);
    setSide(false);
  };
  const onToggleClick = () => {
    setDark((prev: boolean) => !prev);
  };
  const onSideClick = () => {
    setSide((prev: boolean) => !prev);
  };

  return (
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
            <A.Logo value={"Promiser"} />
            <SideList
              style={{
                color:
                  match.group || (!match.friend && !match.setting)
                    ? "#04C994"
                    : "none",
              }}
              onClick={() => onTabClick("group")}
            >
              그룹
            </SideList>
            <SideList
              style={{ color: match.friend ? "#04C994" : "none" }}
              onClick={() => onTabClick("friend")}
            >
              친구
            </SideList>
            <SideList
              style={{ color: match.setting ? "#04C994" : "none" }}
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
  );
}

export default SideMenu;

const SideBar = styled(motion.div)`
  position: fixed;
  top: 0;
  right: 0;
  width: 75%;
  @media screen and (min-width: 900px) {
    width: 30%;
  }
  height: 100%;
  z-index: 10;
  background-color: ${(p) => p.theme.smoke};
  display: flex;
  flex-direction: column;
  transform-origin: right center;
`;

const SideList = styled.div`
  background-color: transparent;
  font-weight: bold;
  margin-left: 1em;
  width: 100%;
  height: 9vh;
  @media screen and (min-width: 900px) {
    height: 12vh;
  }
  padding: 0.8rem;
  cursor: pointer;
  display: flex;
  flex-direction: row;
  margin-bottom: 0.5em;
  border-radius: 1em;
  background-color: ${(p) => p.theme.smoke};
  align-items: center;
`;

const DarkmodeToggle = styled(motion.div)<{ dark: string }>`
  background-color: ${(p) =>
    p.dark === "dark" ? p.theme.green : p.theme.smoke};
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
