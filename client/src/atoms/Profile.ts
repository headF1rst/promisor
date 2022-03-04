import styled from "styled-components";

export const ProfileImg = styled.img<{ type: string }>`
  width: ${(p) => (p.type == "group" ? 2.8 : 2)}em;
  height: ${(p) => (p.type == "group" ? 2.8 : 2)}em;
  border-radius: 1em;
  margin-right: 0.8em;
`;
export const Profile = styled.div<{ direction: string }>`
  display: flex;
  flex-direction: ${(p) => (p.direction === "column" ? "column" : "row")};
  span {
    position: relative;
    top: 0.1em;
  }
`;
