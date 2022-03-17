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

export function FixedRoundBtn({ value, onClick }: IBtn) {
  return <SFixedRoundBtn onClick={onClick}>{value}</SFixedRoundBtn>;
}

const SRoundBtn = styled.div<{ center?: boolean; fontSize?: number }>`
  background-color: ${(p) => p.theme.textColor};
  color: ${(p) => p.theme.bgColor};
  display: flex;
  flex-direction: column;
  justify-content: center;
  border-radius: 1em;
  text-align: center;
  cursor: pointer;
  font-size: ${(p) => (p.fontSize ? `${p.fontSize}em` : "0.9em")};
  height: 1.5em;
  width: 6em;
  margin: ${(p) => p.center && "0 auto"};
`;

const SFixedRoundBtn = styled(SRoundBtn)`
  position: fixed;
  bottom: 3em;
  font-size: 0.9em;
  width: 6em;
  height: 1.5em;
`;
