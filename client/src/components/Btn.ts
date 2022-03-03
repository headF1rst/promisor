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
`;
