import { motion } from "framer-motion";
import styled from "styled-components";

export const ProfileImg = styled.img<{ type: string }>`
  width: ${(p) => (p.type === "group" ? 2.8 : 2)}em;
  height: ${(p) => (p.type === "group" ? 2.8 : 2)}em;
  border-radius: 1em;
  margin-right: 0.5em;
`;
export const Profile = styled(motion.div)<{ direction: string }>`
  display: flex;
  background-color: transparent;
  flex-direction: ${(p) => (p.direction === "column" ? "column" : "row")};
  span {
    position: relative;
    top: 0.1em;
  }
`;
