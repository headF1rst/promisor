import React from "react";
import { motion } from "framer-motion";
import styled from "styled-components";
interface IProfile {
  direction: string;
  type: string;
  value: string;
  imgSrc: string;
}
export function Profile({ direction, value, type, imgSrc }: IProfile) {
  return (
    <SProfile direction={direction}>
      <SProfileImg src={imgSrc} type={type} />
      {value}
    </SProfile>
  );
}

const SProfileImg = styled.img<{ type: string }>`
  width: ${(p) => (p.type === "group" ? 2.8 : 2)}em;
  height: ${(p) => (p.type === "group" ? 2.8 : 2)}em;
  border-radius: 1em;
  margin-right: 0.5em;
`;
const SProfile = styled(motion.div)<{ direction: string }>`
  display: flex;
  background-color: transparent;
  flex-direction: ${(p) => (p.direction === "column" ? "column" : "row")};
  span {
    position: relative;
    top: 0.1em;
  }
`;
