import { IconText } from "../atoms/IconText";
import { ProfileImg } from "../atoms/Profile";
import { ListContainer } from "../styles/Base";
import { RoundElement } from "./RoundElement";
import { FaMapMarkerAlt } from "react-icons/fa";
import { IoMdCafe } from "react-icons/io";
import { MdFastfood } from "react-icons/md";
interface IPlaces {
  places: IPlace[];
}
interface IPlace {
  address_name: string;
  place_name: string;
  place_url: string;
  category_group_name: string;
}

const PlaceList = ({ places }: IPlaces) => {
  return (
    <ListContainer>
      {places &&
        places.map((value, index) => (
          <RoundElement
            key={index}
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
            tail={"no"}
          />
        ))}
    </ListContainer>
  );
};
export default PlaceList;
