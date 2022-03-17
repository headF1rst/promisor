import React from "react";
import { BoxInput } from "../styles/Input";
import Calendar from "../organisms/PromiseDateCalendar";

function PromiseDate() {
  return (
    <>
      <BoxInput type="date" />
      <Calendar />
    </>
  );
}

export default PromiseDate;
