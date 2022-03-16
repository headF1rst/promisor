import { motion } from "framer-motion";
import styled from "styled-components";

export const Template = styled(motion.div)`
  padding-inline: 2rem;
  @media screen and (min-width: 900px) {
    padding-inline: 5rem;
  }
`;
export const Container = styled(motion.div)`
  display: flex;
  flex-direction: column;
  align-items: center;
  @media screen and (min-width: 900px) {
    padding: 2rem 20rem;
  }
`;
export const Header = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  position: sticky;
  padding-inline: 3rem;
  background-color: ${(p) => p.theme.bgColor};
  left: 0;
  top: 0;
  height: 10vh;
  width: 100%;
  z-index: 1;
  @media screen and (min-width: 900px) {
    padding-inline: 20rem;
  }
  justify-content: space-between;
`;
