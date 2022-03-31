import React, { useState } from "react";
import { BsThreeDotsVertical } from "react-icons/bs";
import styled from "styled-components";
export function DotsVertical() {
  const [option, setOption] = useState(false);
  const onClick = () => {
    setOption((prev) => !prev);
  };

  return (
    <DotsOption>
      <BsThreeDotsVertical style={{ cursor: "pointer" }} onClick={onClick} />
    </DotsOption>
  );
}

const DotsOption = styled.div`
  display: flex;
  flex-direction: row;
  background-color: transparent;
`;
const Option = styled.div`
  position: relative;
  right: 1em;
  padding-inline: 0.5em;
  height: 1.5em;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 1em;
`;
