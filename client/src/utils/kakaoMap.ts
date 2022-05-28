import { useQuery } from "react-query";
import { useRecoilValue } from "recoil";
import api from "../auth/api";
import { selectedGroupState } from "../states/selectedGroup";

declare global {
  interface Window {
    kakao: any;
  }
}

const { kakao } = window;

export default function KakaoMapScript() {
  const selectedGroup = useRecoilValue(selectedGroupState);

  const { data: midLocation } = useQuery("midLocation", async () => {
    const { data } = await api.get(`/groups/mid-point/${selectedGroup.id}`);
    return data;
  });

  const container = document.getElementById("myMap");
  const options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  };
  const map = new kakao.maps.Map(container, options);
  var positions = [
    {
      title: "카카오",
      latlng: new kakao.maps.LatLng(33.450705, 126.570677),
    },
    {
      title: "생태연못",
      latlng: new kakao.maps.LatLng(33.450936, 126.569477),
    },
    {
      title: "텃밭",
      latlng: new kakao.maps.LatLng(33.450879, 126.56994),
    },
    {
      title: "근린공원",
      latlng: new kakao.maps.LatLng(33.451393, 126.570738),
    },
  ];
  var ps = new kakao.maps.services.Places();

  // 키워드로 장소를 검색합니다
  ps.keywordSearch("맛집");
  function placesSearchCB(data: any, status: any) {
    if (status === kakao.maps.services.Status.OK) {
      for (var i = 0; i < data.length; i++) {
        displayMarker(data[i]);
      }
    }
  }
  var infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });

  // 지도에 마커를 표시하는 함수입니다
  function displayMarker(place: any) {
    // 마커를 생성하고 지도에 표시합니다
    var marker = new kakao.maps.Marker({
      map: map,
      position: new kakao.maps.LatLng(place.y, place.x),
    });

    // 마커에 클릭이벤트를 등록합니다
    kakao.maps.event.addListener(marker, "click", function () {
      // 마커를 클릭하면 장소명이 인포윈도우에 표출됩니다
      infowindow.setContent(
        '<div style="padding:5px;font-size:12px;">' +
          place.place_name +
          "</div>"
      );
      infowindow.open(map, marker);
    });
  }
  // 마커 이미지의 이미지 주소입니다
  var imageSrc =
    "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";

  for (var i = 0; i < positions.length; i++) {
    // 마커 이미지의 이미지 크기 입니다
    var imageSize = new kakao.maps.Size(24, 35);

    // 마커 이미지를 생성합니다
    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

    // 마커를 생성합니다
    var marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: positions[i].latlng, // 마커를 표시할 위치
      title: positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
      image: markerImage, // 마커 이미지
    });
  }
}
