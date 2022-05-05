import React from "react";
import styled from "styled-components";

interface IBtn {
  value: string;
  center?: boolean;
  onClick?: React.MouseEventHandler<HTMLDivElement>;
  fontSize?: number;
}
export function RoundBtn({ value, center, onClick, fontSize }: IBtn) {
  return (
    <SRoundBtn fontSize={fontSize} center={center} onClick={onClick}>
      {value}
    </SRoundBtn>
  );
}

export function CreateBtn({ value, onClick }: IBtn) {
  return <SCreateBtn onClick={onClick}>{value}</SCreateBtn>;
}

const SRoundBtn = styled.div<{ center?: boolean; fontSize?: number }>`
  background-color: ${(p) => p.theme.btnColor};
  color: ${(p) => p.theme.bgColor};
  display: flex;
  flex-direction: column;
  justify-content: center;
  border-radius: 1em;
  text-align: center;
  cursor: pointer;
  font-size: ${(p) => (p.fontSize ? `${p.fontSize}em` : "0.9em")};
  width: 8em;
  height: 2em;
  margin: ${(p) => p.center && "0 auto"};
`;

const SCreateBtn = styled(SRoundBtn)`
  font-size: 0.9em;

  margin-bottom: 0.5em;
  /* right: 10vw; */
  bottom: 11vh;
  position: fixed;
`;
