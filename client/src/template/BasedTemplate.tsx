import React from "react";
import { motion, AnimatePresence } from "framer-motion";
import styled from "styled-components";
interface IBasedTemplate {
  header: object;
  container: object;
}
function BasedTemplate({ header, container }: IBasedTemplate) {
  return (
    <AnimatePresence>
      <STemplate
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        exit={{ opacity: 0 }}
      >
        <SHeader>{header}</SHeader>
        <SContainer>{container}</SContainer>
      </STemplate>
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
  margin-top: 15vh;
`;
export const SHeader = styled.div`
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
  box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.1);

  justify-content: space-between;
  span,
  div {
    background-color: transparent;
  }
`;
