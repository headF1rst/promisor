import React, { useEffect } from "react";
import { BoxInput } from "../styles/Input";
import PromiseDateCalendar from "../organisms/PromiseDateCalendar";
import ArrowBack from "../atoms/ArrowBack";
import { PromiseTitle } from "./Promise";
import BasedTemplate from "../template/BasedTemplate";
import { useRecoilValue } from "recoil";
import { selectedGroupState } from "../states/selectedGroup";
import PromiseDetailNav from "../organisms/PromiseDetailNav";
import PromiseDetailTitle from "../organisms/PromiseDetailTitle";
import { useMutation, useQuery, useQueryClient } from "react-query";
import api from "../auth/api";
import { useParams } from "react-router";
export interface IPromiseDetail {
  id: number;
  name: string;
  time: string;
  location: string;
}
function PromiseDate() {
  const selectedGroup = useRecoilValue(selectedGroupState);
  const params = useParams();
  const queryClient = useQueryClient();
  const { data: promiseDetailData } = useQuery<IPromiseDetail>(
    ["promiseDetailData", params.pid],
    async () => {
      const { data } = await api.get(`/promises/${params.pid}`);
      return data;
    }
  );
  const { mutate: patchPromiseInfo } = useMutation(
    async (time: string) => {
      const requestBody = {
        name: promiseDetailData.name,
        date: time,
        location: promiseDetailData.location,
      };
      return await api.patch(`promises/${params.pid}`, requestBody);
    },
    {
      onSuccess: () => {
        queryClient.invalidateQueries(["promiseDetailData", params.pid]);
      },
      mutationKey: ["patchPromiseInfoDate", params.pid],
    }
  );
  const onDateChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const {
      target: { value },
    } = e;
    patchPromiseInfo(value);
  };
  const Header = () => {
    return (
      <div
        style={{
          width: "100vw",
          display: "flex",
          flexDirection: "row",
          justifyContent: "space-around",
          alignItems: "center",
        }}
      >
        {" "}
        <ArrowBack />
        <PromiseTitle>
          약속<span>{selectedGroup.name}</span>
        </PromiseTitle>
        <span></span>
      </div>
    );
  };

  const Container = () => {
    return (
      <>
        <PromiseDetailTitle {...promiseDetailData} />
        <PromiseDetailNav />
        <BoxInput
          type="date"
          value={promiseDetailData?.time}
          onChange={onDateChange}
        />
        <PromiseDateCalendar />
      </>
    );
  };

  return <BasedTemplate header={<Header />} container={<Container />} />;
}

export default PromiseDate;
