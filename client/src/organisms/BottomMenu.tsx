import React from "react";
import { AiTwotoneCalendar } from "react-icons/ai";
import { GiThreeFriends } from "react-icons/gi";
import { MdEmojiPeople } from "react-icons/md";
import { useMatch, useNavigate } from "react-router";
import styled from "styled-components";
function BottomMenu() {
  const groupMatch = useMatch("team/*");
  const friendMatch = useMatch("friend");
  console.log(groupMatch, friendMatch);
  const navigate = useNavigate();
  const onMenuClick = (route: string) => {
    navigate(`/${route}`);
  };
  return (
    <RowDiv>
      <ColDiv
        onClick={() => onMenuClick("")}
        routeMatch={!groupMatch && !friendMatch && true}
      >
        <AiTwotoneCalendar size={"2em"} />
        MY
      </ColDiv>
      <ColDiv
        onClick={() => onMenuClick("team")}
        routeMatch={groupMatch ? true : false}
      >
        <GiThreeFriends size={"2em"} />
        TEAM
      </ColDiv>
      <ColDiv
        onClick={() => onMenuClick("friend")}
        routeMatch={friendMatch ? true : false}
      >
        <MdEmojiPeople size={"2em"} />
        FRIEND
      </ColDiv>
    </RowDiv>
  );
}

export default BottomMenu;
const RowDiv = styled.div`
  padding-block: 0.5em;
  flex-direction: row;
  display: flex;
  justify-content: space-around;
  width: 100%;
`;
const ColDiv = styled.div<{ routeMatch: boolean }>`
  padding-top: 0.8em;
  width: 100%;
  flex-direction: column;
  display: flex;
  text-align: center;
  align-items: center;
  font-size: 0.8em;
  cursor: pointer;
  color: ${(p) => p.routeMatch && p.theme.green};
`;
