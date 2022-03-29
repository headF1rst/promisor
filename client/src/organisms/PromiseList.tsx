import React from "react";
import { SFlatElement, SFlatList } from "./RoundList";
interface IRoundList {
  onClick?: React.MouseEventHandler;
  head: Object;
  main: Object;
  sub?: Object;
}

export const PromiseList = ({ onClick, head, main, sub }: IRoundList) => {
  return (
    <SFlatList onClick={onClick}>
      {head}
      <SFlatElement>
        {main}
        {sub}
      </SFlatElement>
    </SFlatList>
  );
};
