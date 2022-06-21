import React, { useEffect, useState } from "react";
import { useRecoilValue } from "recoil";
import { selectedGroupState } from "../states/selectedGroup";
import ArrowBack from "../atoms/ArrowBack";
import { PromiseTitle } from "./Promise";
import BasedTemplate from "../template/BasedTemplate";
import PromiseLocationMap from "../organisms/PromiseLocationMap";
import PromiseDetailTitle from "../organisms/PromiseDetailTitle";
import PromiseDetailNav from "../organisms/PromiseDetailNav";
import { useMutation, useQuery, useQueryClient } from "react-query";
import api from "../auth/api";
import { IPromiseDetail } from "./PromiseDate";
import { useParams } from "react-router";
import { HiLocationMarker } from "react-icons/hi";
import PlaceList from "../organisms/PlaceList";
import styled from "styled-components";
import { useForm } from "react-hook-form";

interface ILocation {
  teamId: number;
  latitude: number;
  longitude: number;
}

export interface IPlaceForm {
  place: string;
}
const PromisePlace = () => {
  const selectedGroup = useRecoilValue(selectedGroupState);
  const params = useParams();
  const [myLat, setMyLat] = useState<number>();
  const [myLon, setMyLon] = useState<number>();
  const [editing, setEditing] = useState(false);

  const queryClient = useQueryClient();

  const { register, handleSubmit, setValue } = useForm<IPlaceForm>();

  const midLocation = queryClient.getQueryData<ILocation>([
    "midLocation",
    params.id,
  ]);

  const { data: promiseDetailData } = useQuery<IPromiseDetail>(
    ["promiseDetailData", params.pid],
    async () => {
      const { data } = await api.get(`/promises/${params.pid}`);
      return data;
    }
  );

  const { mutate: editLocation } = useMutation(
    async () => {
      const requestBody = {
        teamId: selectedGroup.id,
        latitude: myLat,
        longitude: myLon,
      };
      return await api.patch("/groups/location", requestBody);
    },
    {
      onSuccess: () => {
        queryClient.invalidateQueries(["getMidLocation", params.id]);
      },
    }
  );
  const { mutate: patchPromiseInfo } = useMutation(
    async (location: string) => {
      const requestBody = {
        name: promiseDetailData.name,
        date: promiseDetailData.time,
        location,
      };
      return await api.patch(`promises/${params.pid}`, requestBody);
    },
    {
      onSuccess: () => {
        queryClient.invalidateQueries([
          "promiseDetailData",
          String(params.pid),
        ]);
      },
      mutationKey: ["patchPromiseInfoPlace", params?.pid],
    }
  );
  // useEffect(() => {
  //   if (navigator.geolocation) {
  //     navigator.geolocation.getCurrentPosition(
  //       async (position: GeolocationPosition) => {
  //         setMyLat(position.coords.latitude);
  //         setMyLon(position.coords.longitude);
  //       }
  //     );
  //   }
  // }, []);

  const onPlaceValid = ({ place }: IPlaceForm) => {
    patchPromiseInfo(place);
    setEditing(false);
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

        {editing ? (
          <Form onSubmit={handleSubmit(onPlaceValid)}>
            <LineInput
              placeholder="약속 장소"
              defaultValue={
                promiseDetailData?.location
                  ? promiseDetailData.location
                  : "미정"
              }
              {...register("place")}
            />
            <button type={"submit"}>완료</button>
          </Form>
        ) : (
          <RowDiv>
            <span>
              <HiLocationMarker
                size={20}
                style={{ paddingTop: "0.2em", marginRight: "0.3em" }}
              />
              {promiseDetailData?.location
                ? promiseDetailData.location
                : "미정"}
            </span>
            <button type={"button"} onClick={() => setEditing(true)}>
              직접입력
            </button>
          </RowDiv>
        )}

        <PromiseLocationMap midLocation={midLocation} />
        <PlaceList props={{ promiseDetailData, setValue }} />
      </>
    );
  };

  return <BasedTemplate header={<Header />} container={<Container />} />;
};

export default React.memo(PromisePlace);
const RowDiv = styled.div`
  display: flex;
  flex-direction: row;
  margin-bottom: 1em;
  width: 80%;
  justify-content: space-between;
  button {
    border: none;
    background: none;
    color: ${(p) => p.theme.grey};
    text-decoration: underline;
    cursor: pointer;
  }
`;
const LineInput = styled.input`
  width: 80%;
  height: 5vh;
  border: none;
  border-bottom: solid 1px ${(p) => p.theme.grey};
  &:focus {
    outline: none;
  }
  background-color: transparent;
`;
const Form = styled.form`
  display: flex;
  flex-direction: row;
  width: 80%;
  margin-bottom: 1em;
  justify-content: space-between;
  button {
    border: none;
    background: none;
    cursor: pointer;
    color: grey;
    &:hover {
      text-decoration: underline;
    }
  }
`;
