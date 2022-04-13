import React from "react";
import { motion } from "framer-motion";
import styled from "styled-components";
interface IProfileAnimate {
  initial: {
    scale: number;
  };
  animate: {
    scale: number;
  };
  exit: {
    scale: number;
  };
  transition: { type: string };
}
export interface IProfile {
  profileProps?: {
    direction?: string;
    value?: string;
  };
  imgProps: {
    type: string;
    imgSrc: string;
  };
  animateProps?: IProfileAnimate;
}

export function ProfileImg({ imgProps }: IProfile) {
  return <SProfileImg src={imgProps.imgSrc} type={imgProps.type} />;
}

export function Profile({ profileProps, imgProps, animateProps }: IProfile) {
  return (
    <SProfile
      // initial={animateProps?.initial}
      // animate={animateProps?.animate}
      // exit={animateProps?.exit}
      // transition={animateProps?.transition}
      direction={profileProps?.direction}
      type={imgProps.type}
    >
      <ProfileImg imgProps={imgProps} />
      {profileProps?.value}
    </SProfile>
  );
}

const SProfileImg = styled.img<{ type: string }>`
  width: ${(p) => (p.type === "group" ? 2.8 : 2)}em;
  height: ${(p) => (p.type === "group" ? 2.8 : 2)}em;
  border-radius: 1em;
  margin-right: 0.5em;
`;
const SProfile = styled(motion.div)<{ direction?: string; type?: string }>`
  display: flex;
  background-color: transparent;
  flex-direction: ${(p) => (p.direction === "column" ? "column" : "row")};
`;
