import styled from "styled-components";

export const FlatList = styled.div`
  width: 100%;
  height: 9vh;
  @media screen and (min-width: 900px) {
    height: 12vh;
  }
  padding: 0.8rem;
  cursor: pointer;
  display: flex;
  flex-direction: row;
  margin-bottom: 0.5em;
  border-radius: 1em;
  background-color: ${(p) => p.theme.smoke};
  align-items: center;
`;

export const FlatElement = styled.div`
  display: flex;
  flex-direction: column;
  background-color: transparent;

  div,
  span {
    background-color: transparent;
    height: 1.2em;
    overflow: hidden;
  }
  div {
    font-weight: 500;
    font-size: 0.9rem;
  }
  span {
    font-size: 0.8rem;
    margin-top: 0.5rem;
    color: ${(p) => p.theme.grey};
  }
`;

export const ProfileList = styled(FlatList)`
  height: 10vh;
  justify-content: space-between;
`;
