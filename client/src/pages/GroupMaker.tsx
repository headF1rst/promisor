import React, { useEffect } from "react";
import FriendList from "../organisms/FriendList";
import ArrowBack from "../atoms/ArrowBack";
import BasedTemplate from "../template/BasedTemplate";
import { ListContainer } from "../styles/Base";
import { useMutation } from "react-query";
import api from "../auth/api";
import styled from "styled-components";
import { useRecoilValue } from "recoil";
import { useParams } from "react-router";
import { useRecoilState, useSetRecoilState } from "recoil";
import { selectedFriendsAtom } from "../states/selectedFriends";

export interface IFriendsData {
  id: number;
  profileImage: string;
  name: string;
  telephone: string;
  selected?: boolean;
}

export interface IGroupMaker {
  props: {
    state: IFriendsData[];
    setState?: Function;
  };
  select?: string;
}

function GroupMaker() {
  const params = useParams();
  const selectedFriends = useRecoilValue(selectedFriendsAtom);
  useEffect(() => {
    mutate();
  }, []);
  const { mutate, data } = useMutation<IFriendsData[]>(
    "friendList2",
    async () => {
      const { data } = await api.get("/friends/list");
      return data;
    }
  );
  const { mutate: inviteFriends } = useMutation("inviteFriends", async () => {
    let arr: number[] = [];
    selectedFriends.forEach((element: number) => {
      arr.push(element);
    });
    const requestBody = { groupId: params.id, memberId: arr };
    console.log(requestBody);

    return await api
      .post(`/groups/invite`, requestBody)
      .catch((err) => console.log(err));
  });
  const onClick = () => {
    inviteFriends();
  };

  const Header = () => {
    return (
      <div
        style={{
          width: "100vw",
          display: "flex",
          flexDirection: "row",
          justifyContent: "space-around",
          alignItems: "center",
        }}
      >
        {" "}
        <ArrowBack />
        <span>그룹 초대</span>
        <Button type={"submit"} form={"inviteGroupMember"}>
          완료
        </Button>
      </div>
    );
  };
  const Container = () => {
    return (
      <>
        <ListContainer>
          <FriendList props={{ state: data }} select={"checkbox"} />
        </ListContainer>
      </>
    );
  };
  return <BasedTemplate header={<Header />} container={<Container />} />;
}

export default GroupMaker;

const Button = styled.button`
  background-color: transparent;
  border: none;
  cursor: pointer;
`;
