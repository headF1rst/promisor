import React from "react";
import { BsFillTelephoneFill } from "react-icons/bs";
import * as A from "./_index";
import { RoundList } from "../organisms/RoundList";
import * as S from "../styles/_index";
interface IFriendProfileSearch {
  email?: string;
  phoneNumber?: string;
}
function FriendProfileSearch({ email, phoneNumber }: IFriendProfileSearch) {
  // API 요청
  return (
    <RoundList
      head={
        <A.ProfileImg
          imgProps={{
            type: "group",
            imgSrc:
              "https://i.pinimg.com/474x/e9/5e/ca/e95ecaa2f1035bb5acf0d381cd0b3c79.jpg",
          }}
        />
      }
      main={"김채은"}
      sub={<A.IconText icon={<BsFillTelephoneFill />} text={"010-0000-0000"} />}
    />
  );
}

export default FriendProfileSearch;
