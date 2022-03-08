import React from "react";
import { BoxInput } from "../atoms/Input";
import Calendar from "../organisms/Calendar";

function PromiseDate() {
  return (
    <>
      <BoxInput type="date" />
      <Calendar />
    </>
  );
}

export default PromiseDate;
