import React from "react";
import { useRecoilValue } from "recoil";
import ArrowBack from "../organisms/ArrowBack";
import { selectedGroupState } from "../states/selectedGroup";
import BasedTemplate from "../template/BasedTemplate";
import { PromiseTitle } from "./Promise";

function PromiseDetail() {
  const selectedGroup = useRecoilValue(selectedGroupState);
  const Header = () => {
    return (
      <>
        {" "}
        <ArrowBack />
        <PromiseTitle>
          약속<span>{selectedGroup.name}</span>
        </PromiseTitle>
        <span>저장</span>
      </>
    );
  };
  const Container = () => {
    return <></>;
  };
  return <BasedTemplate header={<Header />} container={<Container />} />;
}

export default PromiseDetail;
