import React from "react";
import ArrowBack from "../atoms/ArrowBack";
import * as A from "../atoms/_index";
import { useLocation, useNavigate } from "react-router-dom";
import BasedTemplate from "../template/BasedTemplate";
import { useRecoilValue } from "recoil";
import { selectedGroupState } from "../states/selectedGroup";

function GroupChatRoom() {
  const navigate = useNavigate();
  const selectedGroup = useRecoilValue(selectedGroupState);
  const onPromiseClick = () => {
    navigate("promise");
  };
  const Header = () => {
    return (
      <>
        <ArrowBack />
        <span>{selectedGroup.name}</span>
        <A.Logo onClick={onPromiseClick} value={"P"} />
      </>
    );
  };
  const Container = () => {
    return <></>;
  };
  return <BasedTemplate header={<Header />} container={<Container />} />;
}

export default GroupChatRoom;
