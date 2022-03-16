import React from "react";
import ArrowBack from "../organisms/ArrowBack";
import * as S from "../styles/_index";
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
        <S.Logo
          onClick={onPromiseClick}
          style={{ fontSize: "2.5em", cursor: "pointer" }}
        >
          P
        </S.Logo>
      </>
    );
  };
  const Container = () => {
    return <></>;
  };
  return <BasedTemplate header={<Header />} container={<Container />} />;
}

export default GroupChatRoom;
