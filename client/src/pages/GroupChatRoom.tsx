import React from "react";
import ArrowBack from "../organisms/ArrowBack";
import * as S from "../atoms/_index";
import { useLocation, useNavigate } from "react-router-dom";
import BasedTemplate from "../template/BasedTemplate";
type IGroupChatRoom = {
  name: string;
};
function GroupChatRoom() {
  const location = useLocation();
  const navigate = useNavigate();
  const { name: groupName } = location.state as IGroupChatRoom;
  // 주소로 접근했을 때 처리
  const onPromiseClick = () => {
    navigate("promise");
  };
  const Header = () => {
    return (
      <>
        <ArrowBack route={"group"} />
        <span>{groupName}</span>
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
