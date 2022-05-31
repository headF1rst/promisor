import React, { useRef } from "react";
import { BoxInput } from "../styles/Input";
import PromiseDateCalendar from "../organisms/PromiseDateCalendar";

function PromiseDate() {
  const fixedDateRef = useRef();
  return (
    <>
      <BoxInput type="date" ref={fixedDateRef} />
      <PromiseDateCalendar />
    </>
  );
}

export default PromiseDate;
