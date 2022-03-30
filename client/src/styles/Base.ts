import { motion } from "framer-motion";
import styled from "styled-components";

export const Template = styled(motion.div)`
  padding-inline: 1rem;
  @media screen and (min-width: 1100px) {
    padding-inline: 5rem;
  }
`;
export const Container = styled(motion.div)`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-inline: 1rem;
  @media screen and (min-width: 1100px) {
    padding: 2rem 20rem;
  }
  margin-top: 15vh;
`;
export const Header = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  position: fixed;
  padding-inline: 3rem;
  background-color: ${(p) => p.theme.smoke};
  left: 0;
  top: 0;
  height: 10vh;
  width: 100vw;
  z-index: 1;

  justify-content: space-between;
  span,
  div {
    background-color: transparent;
  }
`;
