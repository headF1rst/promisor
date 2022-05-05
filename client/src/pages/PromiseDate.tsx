import React from "react";
import { BoxInput } from "../styles/Input";
import PromiseDateCalendar from "../organisms/PromiseDateCalendar";

function PromiseDate() {
  return (
    <>
      <BoxInput type="date" />
      <PromiseDateCalendar />
    </>
  );
}

export default PromiseDate;
