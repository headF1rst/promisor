import React, { useState } from "react";
import styled from "styled-components";
import * as A from "../atoms/_index";
import * as S from "../styles/_index";
import { RoundList } from "./RoundList";
import { BsFillTelephoneFill } from "react-icons/bs";
import FriendProfileSearch from "../atoms/FriendProfileSearch";
interface IFriendAdd {
  onClick: React.MouseEventHandler;
  setModal: Function;
}
function FriendAdd({ setModal, onClick }: IFriendAdd) {
  const [search, setSearch] = useState(true);
  const [error, setError] = useState(false);
  const onBtnClick = () => {
    if (!search && !error) {
      // alert("이미 친구로 등록된 사용자입니다.")
      setModal((prev: boolean) => !prev);
    } else {
      setSearch((prev) => !prev);
    }
  };
  return (
    <>
      <FriendModal
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        exit={{ opacity: 0 }}
      >
        <span>친구 추가</span>
        {search && (
          <S.LabelInput>
            <S.Input placeholder="그룹 이름" />
          </S.LabelInput>
        )}
        {!search && !error && (
          <FriendProfileSearch email={"kce3615@naver.com"} />
        )}
        <A.RoundBtn
          onClick={onBtnClick}
          center={true}
          value={search ? "검색" : error ? "재검색" : "추가"}
        />
      </FriendModal>
      <S.Overlay
        onClick={onClick}
        initial={{ opacity: 0 }}
        animate={{ opacity: 1 }}
        exit={{ opacity: 0 }}
      />
    </>
  );
}

export default FriendAdd;

export const FriendModal = styled(S.BoxModal)`
  width: 80%;
  @media screen and (min-width: 900px) {
    width: 40%;
  }
  height: 30vh;
  padding: 2em;
  justify-content: space-between;
  z-index: 3;
  span {
    background-color: transparent;
  }
`;
