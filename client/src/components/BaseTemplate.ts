import styled from "styled-components";

export const Template = styled.div`
  padding: 2rem;
`;
export const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;
export const Header = styled.div<{ items: number }>`
  display: flex;
  flex-direction: row;
  height: 5vh;
  align-items: center;
  justify-content: ${(p) => (p.items == 1 ? "center" : "space-between")};
`;
