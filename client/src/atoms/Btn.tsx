import React from "react";
import * as S from "../styles/_index";
interface IBtn {
  value: string;
  center?: boolean;
  onClick?: React.MouseEventHandler<HTMLDivElement>;
}
export function RoundBtn({ value, center, onClick }: IBtn) {
  return (
    <S.RoundBtn center={center} onClick={onClick}>
      {value}
    </S.RoundBtn>
  );
}

export function FixedRoundBtn({ value, onClick }: IBtn) {
  return <S.FixedRoundBtn onClick={onClick}>{value}</S.FixedRoundBtn>;
}
