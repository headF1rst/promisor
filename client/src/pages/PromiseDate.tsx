import React, { useEffect, useRef } from "react";
import { BoxInput } from "../styles/Input";
import PromiseDateCalendar from "../organisms/PromiseDateCalendar";

function PromiseDate({ date }: { date: string }) {
  const fixedDateRef = useRef<HTMLInputElement>();
  useEffect(() => {
    fixedDateRef.current.value = date;
  }, []);
  return (
    <>
      <BoxInput type="date" ref={fixedDateRef} />
      <PromiseDateCalendar />
    </>
  );
}

export default PromiseDate;
