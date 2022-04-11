import React from "react";
import { FriendModal } from "./FriendAdd";
import * as S from "../styles/_index";
import * as A from "../atoms/_index";
import { useNavigate } from "react-router";
interface IGroupAdd {
  props: {
    state: boolean;
    setState: Function;
  };
}
function GroupAdd({ props }: IGroupAdd) {
  const navigate = useNavigate();
  const onBtnClick = (id: number) => {
    // api 요청 -> id 받고 navigate
    navigate(`/group/${id}/invite`);
  };
  const onOverlayClick = () => {
    props.setState((prev: boolean) => !prev);
  };
  return (
    <>
      {props.state && (
        <>
          <FriendModal
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
          >
            <span>그룹 생성</span>
            <S.LabelInput>
              <S.Input placeholder="그룹 이름" />
            </S.LabelInput>
            <A.RoundBtn
              onClick={() => onBtnClick(1)}
              center={true}
              value={"추가"}
            />
          </FriendModal>
          <S.Overlay
            onClick={onOverlayClick}
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            exit={{ opacity: 0 }}
          />
        </>
      )}
    </>
  );
}

export default GroupAdd;
