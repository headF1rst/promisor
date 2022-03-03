import styled from "styled-components";

export const Logo = styled.div`
  width: 50vw;
  height: 20vh;
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: winkle;
  font-size: 3rem;
  color: ${(p) => p.theme.green};
  text-shadow: -2px 1px 0px #ffffff;
`;
