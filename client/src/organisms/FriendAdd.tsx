import React, { useState, useEffect } from "react";
import styled from "styled-components";
import * as A from "../atoms/_index";
import * as S from "../styles/_index";
import { BsFillTelephoneFill } from "react-icons/bs";
import { RoundList } from "./RoundList";
import { useQuery } from "react-query";
import axios from "axios";
import { config } from "../auth/config";

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
  const [inputValue, setInputValue] = useState("");
  const [searchData, setSearchData] = useState<IProfileData>();

  const onInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setInputValue(e.target.value);
  };

  const onBtnClick = async () => {
    if (!searchData) {
      await axios
        .get("/friends", {
          params: { findEmail: inputValue },
          ...config,
        })
        .then((res) => {
          setSearchData(res.data);
        })
        .catch((err) => {
          alert(err.response.data.message);
        });
    } else {
      await axios
        .post(`/friends/${searchData.id}`, null, config)
        .then((res) => {})
        .catch((err) => {
          alert(err.response.data.message);
        });
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
              <RoundList
                head={
                  <A.ProfileImg
                    imgProps={{
                      type: "group",
                      imgSrc:
                        "https://i.pinimg.com/474x/e9/5e/ca/e95ecaa2f1035bb5acf0d381cd0b3c79.jpg",
                    }}
                  />
                }
                main={searchData.name}
                sub={
                  <A.IconText
                    icon={<BsFillTelephoneFill />}
                    text={
                      searchData.telephone.slice(0, 3) +
                      "-" +
                      searchData.telephone.slice(3, 7) +
                      "-" +
                      searchData.telephone.slice(7, 11)
                    }
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
