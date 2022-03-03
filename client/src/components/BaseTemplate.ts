import styled from "styled-components";
export const Template = styled.div`
  padding: 2rem;
  padding-top: 1rem;
  @media screen and (min-width: 900px) {
    padding: 2rem 20rem;
  }
`;
export const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  margin-top: 3rem;
`;
export const Header = styled.div<{ items: number }>`
  display: flex;
  flex-direction: row;
  height: 5vh;
  align-items: center;
  @media screen and (min-width: 900px) {
    padding-inline: 20rem;
  }
  justify-content: ${(p) => (p.items == 1 ? "center" : "space-between")};
`;
