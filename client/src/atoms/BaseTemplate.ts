import styled from "styled-components";
export const Template = styled.div`
  padding-inline: 2rem;
  padding-top: 1em;
  @media screen and (min-width: 900px) {
    padding-inline: 20rem;
  }
`;
export const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 3rem;
  @media screen and (min-width: 900px) {
    padding: 2rem 20rem;
  }
`;
export const Header = styled.div<{ items: number }>`
  display: flex;
  flex-direction: row;
  align-items: center;
  position: fixed;
  padding-inline: 3rem;
  background-color: ${(p) => p.theme.bgColor};
  left: 0;
  top: 0;
  height: 10vh;
  width: 100%;
  @media screen and (min-width: 900px) {
    padding-inline: 20rem;
  }
  justify-content: ${(p) => (p.items === 1 ? "center" : "space-between")};
`;
