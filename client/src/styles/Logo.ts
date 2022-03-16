import styled from "styled-components";

export const Logo = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: winkle !important;
  font-size: 3rem;
  color: ${(p) => p.theme.green};
  text-shadow: -2px 1px 0px #ffffff;
  margin-block: 0.5em;
`;
