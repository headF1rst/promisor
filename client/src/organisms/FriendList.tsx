import React, { useEffect, useState } from "react";
import { useRecoilState } from "recoil";
import styled from "styled-components";
import * as S from "../styles/_index";
import * as A from "../atoms/_index";
import * as O from "../organisms/_index";
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
        <O.RoundList
          elementProps={{ title: value.title }}
          imgProps={{
            type: "group",
            imgSrc: `${value.img}`,
          }}
          onClick={() => onListClick(value.id)}
          key={idx}
        />
      ))}
    </>
  );
}

export default FriendList;
