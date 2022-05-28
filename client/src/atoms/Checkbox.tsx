import React, { useState } from "react";
import { useRecoilState } from "recoil";
import styled from "styled-components";
import { selectedFriendsAtom } from "../states/selectedFriends";

interface ICheckbox {
  id: string;
}
const Checkbox = ({ id }: ICheckbox) => {
  const [checked, setChecked] = useState(false);
  const [selectedFriends, setSelectedFriends] =
    useRecoilState(selectedFriendsAtom);
  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    let copy = new Set(selectedFriends);
    const targetId = Number(e.target.id);
    try {
      if (!checked) {
        copy.add(targetId);
      } else {
        copy.delete(targetId);
      }
    } finally {
      setSelectedFriends(copy);
      setChecked((p) => !p);
    }
  };
  return (
    <SCheckbox checked={checked} onChange={onChange} type="checkbox" id={id} />
  );
};

export default Checkbox;

const SCheckbox = styled.input`
  -ms-transform: scale(1.4); /* IE */

  -moz-transform: scale(1.4); /* FF */

  -webkit-transform: scale(1.4); /* Safari and Chrome */

  -o-transform: scale(1.4); /* Opera */

  accent-color: ${(p) => p.theme.green};
`;
