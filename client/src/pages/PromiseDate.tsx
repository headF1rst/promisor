import React from "react";
import { BoxInput } from "../styles/Input";
import PromiseDateCalendar from "../organisms/PromiseDateCalendar";
import PromiseDateRecommendation from "../organisms/PromiseDateRecommendation";

function PromiseDate() {
  return (
    <>
      <BoxInput type="date" />
      <PromiseDateCalendar />
      <PromiseDateRecommendation />
    </>
  );
}

export default PromiseDate;
