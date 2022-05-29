import React, { useEffect, useState } from "react";
import { useMutation, useQuery, useQueryClient } from "react-query";
import { useRecoilValue } from "recoil";
import api from "../auth/api";
import { selectedGroupState } from "../states/selectedGroup";
import markerImg from "../image/marker.png";
import PromiseLocationMap from "../organisms/PromiseLocaionMap";
import PlaceList from "../organisms/PlaceList";
import styled from "styled-components";
interface ILocation {
  teamId: number;
  latitude: number;
  longitude: number;
}

interface IPlace {
  address_name: string;
  place_name: string;
  place_url: string;
  category_group_name: string;
}
declare var naver: any;
declare global {
  interface Window {
    kakao: any;
  }
}
const { kakao } = window;

const PromiseLocation = () => {
  const selectedGroup = useRecoilValue(selectedGroupState);
  const [myLat, setMyLat] = useState<number>();
  const [myLon, setMyLon] = useState<number>();
  const [places, setPlaces] = useState<IPlace[]>();
  const queryClient = useQueryClient();

  const { mutate: editLocation } = useMutation(
    "editLocation",
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
        queryClient.invalidateQueries("midLocation");
      },
    }
  );
  const { data: midLocation } = useQuery<ILocation>("midLocation", async () => {
    const { data } = await api.get(`/groups/mid-point/${selectedGroup.id}`);
    return data;
  });
  useEffect(() => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position: GeolocationPosition) => {
          setMyLat(position.coords.latitude);
          setMyLon(position.coords.longitude);
        }
      );
    }
  }, []);
  useEffect(() => {
    if (myLat && myLon) {
      editLocation();
    }
  }, [myLat, myLon]);
  useEffect(() => {
    if (midLocation) {
      const initMap = () => {
        const map = new naver.maps.Map("map", {
          center: new naver.maps.LatLng(
            midLocation.latitude,
            midLocation.longitude
          ),
          zoom: 13,
        });
        const marker = new naver.maps.Marker({
          position: new naver.maps.LatLng(
            midLocation.latitude,
            midLocation.longitude
          ),
          map: map,
          icon: {
            content: `
              <img alt="marker" style="width: 1em; height:1em;" src=${markerImg} />
            `,
          },
        });
      };

      initMap();
      searchDetailAddrFromCoords(null, function (result: any, status: any) {
        if (status === kakao.maps.services.Status.OK) {
          document.querySelector(".title").innerHTML =
            result[0].address.address_name;
          ps.keywordSearch(
            `${result[0].address.address_name} 맛집`,
            placesSearchCB
          );
        }
      });
    }
  }, [midLocation]);

  var geocoder = new kakao.maps.services.Geocoder();
  function searchDetailAddrFromCoords(coords: any, callback: Function) {
    geocoder.coord2Address(myLon, myLat, callback);
  }
  var ps = new kakao.maps.services.Places();

  const placesSearchCB = (data: any, status: any) => {
    if (status === kakao.maps.services.Status.OK) {
      setPlaces(data);
    }
  };
  return (
    <div>
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
        <span className="title"></span>
      </div>
      <PromiseLocationMap />
      <PlaceList places={places} />
    </div>
  );
};
export default PromiseLocation;
const Title = styled.span`
  color: ${(p) => p.theme.green};
`;
