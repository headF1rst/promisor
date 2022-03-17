import React, { useEffect, useState } from "react";
import { useRecoilState } from "recoil";
import styled from "styled-components";
import * as S from "../styles/_index";
import * as A from "../atoms/_index";
import { selectedState } from "../states/createGroup";
import { IFriendList } from "../pages/CreateGroup";
import { BsFillCheckSquareFill } from "react-icons/bs";

function FriendList({ select, friends_data }: IFriendList) {
  const [selected, setSelected] = useRecoilState(selectedState);
  console.log(selected);
  const onListClick = (id: number) => {
    if (!select) return;

    let newSelected;
    if (selected.includes(id)) {
      const selectedCopy = [...selected];
      newSelected = selectedCopy.filter((value) => value !== id);
    } else {
      newSelected = [...selected, id];
    }
    setSelected(newSelected);
  };
  return (
    <>
      {friends_data.map((value, idx) => (
        <S.ProfileList onClick={() => onListClick(value.id)} key={idx}>
          <A.Profile
            direction={"row"}
            value={value.title}
            type={"profile"}
            imgSrc={value.img}
          />
          {select && (
            <>
              {selected.includes(value.id) ? (
                <BsFillCheckSquareFill
                  color="#04C994"
                  style={{ marginRight: "0.2em" }}
                />
              ) : (
                "⬜"
              )}
            </>
          )}
        </S.ProfileList>
      ))}
    </>
  );
}

export default FriendList;

export const Profile = styled(S.Profile)`
  align-items: center;
  background-color: transparent;
`;
