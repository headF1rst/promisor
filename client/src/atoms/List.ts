import styled from "styled-components";

export const FlatList = styled.div`
  width: 100%;
  height: 12vh;
  padding: 0.8rem;
  cursor: pointer;
  display: flex;
  flex-direction: row;
  margin-bottom: 0.5em;
  border-radius: 1em;
  background-color: ${(p) => p.theme.smoke};
  align-items: center;
`;
export const ProfileList = styled(FlatList)`
  height: 10vh;
  justify-content: space-between;
`;
