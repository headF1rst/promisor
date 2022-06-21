import { IconText } from "../atoms/IconText";
import { ListContainer } from "../styles/Base";
import { RoundElement } from "./RoundElement";
import { FaMapMarkerAlt } from "react-icons/fa";
import { IoMdCafe } from "react-icons/io";
import { MdFastfood } from "react-icons/md";
import { IPromiseDetail } from "../pages/PromiseDate";
import styled from "styled-components";
import { AiFillHeart } from "react-icons/ai";
import { useMutation, useQuery, useQueryClient } from "react-query";
import api from "../auth/api";
import React, { useEffect, useState } from "react";
import { useParams } from "react-router";
import { UseFormSetValue } from "react-hook-form";
import { IPlaceForm } from "../pages/PromisePlace";

interface ILocation {
  teamId: number;
  latitude: number;
  longitude: number;
}
declare global {
  interface Window {
    kakao: any;
  }
}
const { kakao } = window;
interface IPlace {
  address_name: string;
  place_name: string;
  place_url: string;
  category_group_name: string;
}

interface IProps {
  props: {
    promiseDetailData: IPromiseDetail;
    setValue: UseFormSetValue<IPlaceForm>;
  };
}
const PlaceList = ({ props }: IProps) => {
  const { promiseDetailData, setValue } = props;
  const params = useParams();
  const [places, setPlaces] = useState<IPlace[]>();
  const [midAddress, setMidAddress] = useState("");

  const queryClient = useQueryClient();
  const { mutate: patchPromiseInfo } = useMutation(
    async (location: string) => {
      const requestBody = {
        name: promiseDetailData?.name,
        date: promiseDetailData?.time,
        location,
      };
      setValue("place", location);
      return await api.patch(`promises/${promiseDetailData?.id}`, requestBody);
    },
    {
      onSuccess: () => {
        queryClient.invalidateQueries([
          "promiseDetailData",
          String(promiseDetailData?.id),
        ]);
      },
      mutationKey: ["patchPromiseInfoPlace", promiseDetailData?.id],
    }
  );
  const { data: midLocation } = useQuery<ILocation>(
    ["getMidLocation", params.id],
    async () => {
      const { data } = await api.get(`/groups/mid-point/${params.id}`);
      return data;
    },
    {
      onSuccess: (data) => {
        searchDetailAddrFromCoords(data, searchListFromKeyword);
      },
    }
  );

  var geocoder = new kakao.maps.services.Geocoder();
  const searchDetailAddrFromCoords = (
    midLocation: ILocation,
    callback: Function
  ) => {
    if (midLocation) {
      geocoder.coord2Address(
        // midLocation.longitude,
        // midLocation.latitude,
        127.1235932,
        37.3309138,
        callback
      );
    }
  };

  const searchListFromKeyword = (result: any, status: any) => {
    if (status === kakao.maps.services.Status.OK) {
      const address_name = result[0].address.address_name;
      setMidAddress(address_name);
      ps.keywordSearch(`${address_name} 맛집`, placesSearchCB);
    }
  };

  var ps = new kakao.maps.services.Places();

  const placesSearchCB = (data: IPlace[], status: string) => {
    if (status === kakao.maps.services.Status.OK) {
      setPlaces(data);
    }
  };
  const onPlaceClick = (place_name: string) => {
    patchPromiseInfo(place_name);
  };
  return (
    <ListContainer
      key={"places"}
      initial={{ opacity: 0 }}
      animate={{ opacity: 1 }}
    >
      <div
        style={{
          marginBlock: "1em",
          display: "flex",
          flexDirection: "row",
          gap: "0.5em",
          alignItems: "center",
          justifyContent: "center",
        }}
      >
        <Title>중간 지점</Title>
        <span className="title">{midAddress}</span>
      </div>
      {places &&
        places.map((value, index) => (
          <RowDiv key={index}>
            <RoundElement
              head={
                <span style={{ width: "2em", height: "2em" }}>
                  {value.category_group_name === "카페" ? (
                    <IoMdCafe size={20} style={{ marginTop: "0.3em" }} />
                  ) : (
                    <MdFastfood size={20} style={{ marginTop: "0.3em" }} />
                  )}
                </span>
              }
              main={value.place_name}
              sub={
                <IconText icon={<FaMapMarkerAlt />} text={value.address_name} />
              }
              tail={
                <AiFillHeart
                  style={{ cursor: "pointer" }}
                  onClick={() => onPlaceClick(value?.place_name)}
                  size={20}
                />
              }
              onClick={() => {
                window.open(value.place_url);
              }}
            />
          </RowDiv>
        ))}
    </ListContainer>
  );
};
export default PlaceList;

const RowDiv = styled.div`
  display: flex;
  flex-direction: row;
  button {
    border: none;
    background: none;
    width: 1.5em;
  }
`;
const Title = styled.span`
  color: ${(p) => p.theme.green};
`;
