import { AnimatePresence, motion } from "framer-motion";
import React from "react";
import { useRecoilState } from "recoil";
import styled from "styled-components";
import { IFriendList } from "../pages/CreateGroup";
import { selectedState } from "../states/createGroup";
import * as S from "../styles/_index";

function SelectedList({ friends_data }: IFriendList) {
  const [selected, setSelected] = useRecoilState(selectedState);

  const onListClick = (id: number) => {
    const selectedCopy = [...selected];
    const newSelected = selectedCopy.filter((value) => value !== id);
    setSelected(newSelected);
  };
  return (
    <RowList>
      {friends_data.map((value) => (
        <span key={value.id}>
          {selected.includes(value.id) && (
            <S.Profile transition={{ type: "linear" }} direction={"column"}>
              <S.ProfileImg type={"profile"} src={value.img} />
              <HoverImg
                initial={{ opacity: 0 }}
                whileHover={{ opacity: 1 }}
                onClick={() => onListClick(value.id)}
              >
                X
              </HoverImg>
              <span>{value.title}</span>
            </S.Profile>
          )}
        </span>
      ))}
    </RowList>
  );
}

export default React.memo(SelectedList);

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
