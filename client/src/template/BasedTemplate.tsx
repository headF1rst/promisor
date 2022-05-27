import React, { useEffect } from "react";
import {
  motion,
  AnimatePresence,
  useViewportScroll,
  useAnimation,
} from "framer-motion";
import styled from "styled-components";
import { useRecoilValue } from "recoil";
import { darkModeState } from "../states/darkmode";
import BottomMenu from "../organisms/BottomMenu";
import { useMatch } from "react-router";
import { getCookie } from "../Cookie";
interface IBasedTemplate {
  header: object;
  container: object;
}

function BasedTemplate({ header, container }: IBasedTemplate) {
  const loginMatch = useMatch("login");
  const registerMatch = useMatch("register");
  const headerAnimation = useAnimation();
  const { scrollY } = useViewportScroll();
  const isDark = useRecoilValue(darkModeState);
  useEffect(() => {
    scrollY.onChange(() => {
      if (scrollY.get() < 50) {
        headerAnimation.start("init");
      } else {
        headerAnimation.start("scroll");
      }
    });
  }, [scrollY, headerAnimation]);
  const headerVariants = {
    init: {
      backgroundColor: "rgba(0, 0, 0, 0)",
      boxShadow: "0px 2px 2px rgba(0, 0, 0, 0)",
    },
    scroll: {
      boxShadow: "0px 2px 2px rgba(0, 0, 0, 0.1)",
      backgroundColor: isDark ? "rgba(0, 0, 0, 1)" : "rgba(255, 255, 255, 1)",
      transition: {
        duration: 0.3,
        type: "linear",
      },
    },
  };
  return (
    <AnimatePresence>
      <>
        <STemplate
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          exit={{ opacity: 0 }}
        >
          <SHeader variants={headerVariants} animate={headerAnimation}>
            {header}
          </SHeader>
          <SContainer>{container}</SContainer>
        </STemplate>
      </>
      {!loginMatch && !registerMatch && (
        <SBottomMenu>
          <BottomMenu />
        </SBottomMenu>
      )}
    </AnimatePresence>
  );
}

export default BasedTemplate;

export const STemplate = styled(motion.div)`
  padding-inline: 1rem;
  @media screen and (min-width: 1100px) {
    padding-inline: 5rem;
  }
`;
export const SContainer = styled(motion.div)`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-inline: 1rem;
  @media screen and (min-width: 1100px) {
    padding: 2rem 20rem;
  }
  margin-top: 12vh;
`;
export const SHeader = styled(motion.div)`
  display: flex;
  flex-direction: row;
  align-items: center;
  position: fixed;
  background-color: transparent;
  left: 0;
  top: 0;
  height: 10vh;
  width: 100vw;
  z-index: 1;

  justify-content: center;
  span,
  div {
    background-color: transparent;
  }
`;
export const SBottomMenu = styled(motion.div)`
  display: flex;
  position: fixed;
  left: 0;
  bottom: 0;
  height: 10vh;
  width: 100vw;
`;
