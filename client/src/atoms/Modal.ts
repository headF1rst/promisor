import { motion } from "framer-motion";
import styled from "styled-components";

export const Overlay = styled(motion.div)`
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
`;

export const BoxModal = styled(motion.div)`
  display: flex;
  flex-direction: column;
  position: fixed;
  border-radius: 1em;
  background-color: ${(p) => p.theme.bgColor};
  color: ${(p) => p.theme.textColor};
`;
