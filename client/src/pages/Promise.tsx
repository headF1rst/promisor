import React from "react";
import * as S from "../atoms/_index";
import ArrowBack from "../organisms/ArrowBack";
import BasedTemplate from "../template/BasedTemplate";

function Promise() {
  const Header = () => {
    return (
      <>
        <ArrowBack route={"group"} />
        <span>약속</span>
        <span>+</span>
      </>
    );
  };
  const Container = () => {
    return <></>;
  };
  return <BasedTemplate header={<Header />} container={<Container />} />;
}

export default Promise;
