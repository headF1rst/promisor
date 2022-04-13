import React from "react";
import styled from "styled-components";
import { IoChevronDown, IoChevronUpSharp } from "react-icons/io5";
interface IThinBar {
  text: string;
  show?: boolean;
  onShowClick?: React.MouseEventHandler;
}
function ThinBar({ text, show, onShowClick }: IThinBar) {
  return (
    <Bar>
      <span>{text}</span>
      <Toggle>
        {show ? (
          <IoChevronUpSharp onClick={onShowClick} />
        ) : (
          <IoChevronDown onClick={onShowClick} />
        )}
      </Toggle>
    </Bar>
  );
}

export default ThinBar;

const Bar = styled.div`
  width: 100%;
  height: 3vh;
  @media screen and (min-width: 900px) {
    height: 5vh;
  }
  padding: 0.8rem;
  display: flex;
  justify-content: space-between;
  flex-direction: row;
  margin-bottom: 0.5em;
  border-radius: 0.2em;
  background-color: ${(p) => p.theme.opacity50};
  align-items: center;
  box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.1);
  font-size: 0.8em;
  span {
    background-color: transparent;
  }
`;
const Toggle = styled.span`
  cursor: pointer;
`;
