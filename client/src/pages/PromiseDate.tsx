import React, { useEffect, useRef } from "react";
import { BoxInput } from "../styles/Input";
import PromiseDateCalendar from "../organisms/PromiseDateCalendar";
import { useQueryClient } from "react-query";
interface PromiseDate {
  props: {
    date: string;
    setDate: Function;
    patchPromiseInfo: Function;
  };
}
function PromiseDate({ props }: PromiseDate) {
  const fixedDateRef = useRef<HTMLInputElement>();
  const { date, setDate, patchPromiseInfo } = props;
  const queryClient = useQueryClient();
  useEffect(() => {
    return () => {
      patchPromiseInfo();
    };
  });

  return (
    <>
      <BoxInput type="date" value={date} />
      <PromiseDateCalendar />
    </>
  );
}

export default PromiseDate;
