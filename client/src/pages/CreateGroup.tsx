import { AnimatePresence, motion } from "framer-motion";
import React, { useEffect } from "react";
import { useRecoilState } from "recoil";
import styled from "styled-components";
import * as S from "../styles/_index";
import FriendList from "../organisms/FriendList";
import { selectedState } from "../states/createGroup";
import ArrowBack from "../atoms/ArrowBack";
import SelectedList from "../organisms/CreateGroupSelectedList";
import BasedTemplate from "../template/BasedTemplate";
const TEST_PROFILE = [
  {
    id: 0,
    img: "data:image/jpeg;base64,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",
    title: "승환",
    chat: "안녕",
  },

  {
    id: 1,
    img: "data:image/jpeg;base64,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",
    title: "준석",
    chat: "안녕",
  },

  {
    id: 2,
    img: "data:image/jpeg;base64,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",
    title: "산하",
    chat: "안녕",
  },

  {
    id: 3,
    img: "data:image/jpeg;base64,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",
    title: "채은",
    chat: "안녕",
  },
];
export interface IFriendsData {
  id: number;
  img: string;
  title: string;
}
export interface IFriendList {
  select?: boolean;
  friends_data: IFriendsData[];
}
function CreateGroup() {
  const [selected, setSelected] = useRecoilState(selectedState);
  useEffect(() => {
    setSelected([-1]);
  }, []);

  const Header = () => {
    return (
      <>
        {" "}
        <ArrowBack />
        <span>그룹 생성</span>
        <span>완료</span>
      </>
    );
  };
  const Container = () => {
    return (
      <>
        <FriendList friends_data={TEST_PROFILE} select={true} />
        <SelectedList friends_data={TEST_PROFILE} />
      </>
    );
  };
  return <BasedTemplate header={<Header />} container={<Container />} />;
}

export default CreateGroup;
