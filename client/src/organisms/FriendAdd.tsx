import React, { useState } from "react";
import styled from "styled-components";
import * as A from "../atoms/_index";
import * as S from "../styles/_index";
import { BsFillTelephoneFill } from "react-icons/bs";
import { phoneNumberFormatter } from "../utils/phoneNumberFormatter";
import { useMutation, useQueryClient } from "react-query";
import api from "../auth/api";
import { RoundElement } from "./RoundElement";

interface IFriendAdd {
  onClick: React.MouseEventHandler;
  setModal: Function;
}
interface IProfileData {
  id: number;
  name: string;
  profileImage: string;
  status: string;
  telephone: string;
}

function FriendAdd({ setModal, onClick }: IFriendAdd) {
  const queryClient = useQueryClient();
  const [inputValue, setInputValue] = useState("");
  const [searchData, setSearchData] = useState<IProfileData>();

  const { mutate: getFriend } = useMutation(async () => {
    await api
      .get("/friends", {
        params: { findEmail: inputValue },
      })
      .then((res) => {
        setSearchData(res.data);
      })
      .catch((err) => {
        alert(err.response.data.message);
      });
  });
  const { mutate: addFriend } = useMutation(
    async () => {
      return await api.post(`/friends/${searchData.id}`).catch((err) => {
        alert(err.response.data.message);
      });
    },
    {
      onSuccess: () => {
        queryClient.invalidateQueries("friendList");
      },
    }
  );

  const onInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputValue(e.target.value);
  };

  const onBtnClick = async () => {
    if (!searchData) {
      getFriend();
    } else {
      addFriend();
      setModal(false);
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
        {!searchData && (
          <S.LabelInput>
            <S.Input
              placeholder="이메일을 입력하세요..."
              value={inputValue}
              onChange={onInputChange}
            />
          </S.LabelInput>
        )}
        {searchData && (
          <Container>
            {searchData && (
              <RoundElement
                head={
                  <A.ProfileImg
                    imgProps={{
                      type: "group",
                      imgSrc: searchData.profileImage,
                    }}
                  />
                }
                main={searchData.name}
                sub={
                  <A.IconText
                    icon={<BsFillTelephoneFill />}
                    text={phoneNumberFormatter(searchData.telephone)}
                  />
                }
              />
            )}
          </Container>
        )}
        <A.RoundBtn
          onClick={onBtnClick}
          center={true}
          value={!searchData ? "검색" : "추가"}
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
const Container = styled.div`
  div {
    border: none;
  }
`;
