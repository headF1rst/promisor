import { AnimatePresence, motion } from "framer-motion";
import React, { useEffect, useState } from "react";
import { useRecoilState } from "recoil";
import styled from "styled-components";
import { IFriendList, IFriendsData } from "../pages/GroupMaker";
import { selectedState } from "../states/createGroup";
import * as S from "../styles/_index";
import * as A from "../atoms/_index";

function GroupMakerSelectedList({ friendsData }: IFriendList) {
  const [friendsDict, setFriendsDict] = useState<IFriendsData[]>();
  const [selected, setSelected] = useState<number[]>([]);
  useEffect(() => {
    const dict = new Array();
    for (let i = 0; i < friendsData.length; i++) {
      dict[friendsData[i].id] = friendsData[i];
    }
    setFriendsDict(friendsDict);
  }, []);
  useEffect(() => {
    const selectedString = localStorage.getItem("groupMaker");
    if (selectedString) {
      const arr = selectedString.split(",").map((value) => parseInt(value));
    }
  }, []);
  const onListClick = (id: number) => {
    const selectedCopy = [...selected];
    const newSelected = selectedCopy.filter((value) => value !== id);
    setSelected(newSelected);
  };
  const animationProps = {
    initial: { scale: 0 },
    animate: { scale: 1 },
    exit: { scale: 0 },
    transition: { type: "linear" },
  };
  return (
    <RowList>
      {selected?.map((value, index) => (
        <span key={index}>
          {friendsDict && friendsDict[value] && (
            <A.Profile
              profileProps={{
                direction: "column",
                value: `${friendsDict[value].title}`,
              }}
              imgProps={{
                type: "profile",
                imgSrc: `${friendsDict[value].img}`,
              }}
              animateProps={animationProps}
            />
          )}
        </span>
      ))}
    </RowList>
  );
}

export default GroupMakerSelectedList;

const HoverImg = styled(motion.div)`
  position: absolute;
  width: 2em;
  height: 2em;
  border-radius: 2em;
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.5);
  cursor: pointer;
`;

const RowList = styled.div`
  position: fixed;
  padding-inline: 1em;
  padding-top: 1em;
  @media screen and (min-width: 900px) {
    padding-inline: 25rem;
  }
  bottom: 0;
  width: 100%;
  height: 5em;
  display: flex;
  flex-direction: row;
  overflow-x: scroll;
  ::-webkit-scrollbar {
    height: 0.5em;
  }
  ::-webkit-scrollbar-track {
    background-color: ${(p) => p.theme.bgColor};
  }
  ::-webkit-scrollbar-thumb {
    background-color: ${(p) => p.theme.green};
    border-radius: 10px;
  }
  ::-webkit-scrollbar-thumb:hover {
    background: ${(p) => p.theme.green};
  }
  ::-webkit-scrollbar-button:start:decrement,
  ::-webkit-scrollbar-button:end:increment {
    width: 16px;
    height: 16px;
    background: ${(p) => p.theme.bgColor};
  }
`;
