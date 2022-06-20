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

interface ILocation {
  teamId: number;
  latitude: number;
  longitude: number;
}

export interface IPlace {
  address_name: string;
  place_name: string;
  place_url: string;
  category_group_name: string;
}
const PromisePlace = () => {
  const selectedGroup = useRecoilValue(selectedGroupState);
  const params = useParams();
  const [myLat, setMyLat] = useState<number>();
  const [myLon, setMyLon] = useState<number>();

  const queryClient = useQueryClient();

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
        <div style={{ width: "80%", marginBottom: "1em" }}>
          <HiLocationMarker
            size={20}
            style={{ paddingTop: "0.2em", marginRight: "0.3em" }}
          />
          {promiseDetailData?.location ? promiseDetailData.location : "미정"}
        </div>
        <PromiseLocationMap midLocation={midLocation} />
        <PlaceList {...promiseDetailData} />
      </>
    );
  };

  return <BasedTemplate header={<Header />} container={<Container />} />;
};

export default React.memo(PromisePlace);
