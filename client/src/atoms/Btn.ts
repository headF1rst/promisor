import styled from "styled-components";

export const RoundBtn = styled.div`
  background-color: ${(p) => p.theme.textColor};
  color: ${(p) => p.theme.bgColor};
  display: flex;
  flex-direction: column;
  justify-content: center;
  border-radius: 1em;
  text-align: center;
  cursor: pointer;
  font-size: 0.9em;
  height: 1.5em;
`;

export const FixedRoundBtn = styled(RoundBtn)`
  position: fixed;
  bottom: 3em;
  font-size: 0.9em;
  width: 6em;
  height: 1.5em;
`;
