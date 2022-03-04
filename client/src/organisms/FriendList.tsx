import React, { useEffect, useState } from "react";
import { useRecoilState } from "recoil";
import * as S from "../atoms/_index";
import { selectedState } from "../states/createGroup";
interface IFriendsData {
  id: number;
  img: string;
  title: string;
}
interface IFriendList {
  select: boolean;
  friends_data: IFriendsData[];
}

function FriendList({ select, friends_data }: IFriendList) {
  const [selected, setSelected] = useRecoilState(selectedState);
  const onListClick = (id: number) => {
    if (!select) return;

    const newSelected = [...selected, id];

    setSelected(newSelected);
  };
  return (
    <>
      {friends_data.map((value, idx) => (
        <S.ProfileList onClick={() => onListClick(value.id)} key={idx}>
          <S.ProfileImg type={"profile"} src={value.img} />
          {value.title}
        </S.ProfileList>
      ))}
    </>
  );
}

export default FriendList;
