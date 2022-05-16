import React, { useEffect, useState } from "react";
import FriendList from "../organisms/FriendList";
import ArrowBack from "../atoms/ArrowBack";
import GroupMakerSelectedList from "../organisms/GroupMakerSelectedList";
import BasedTemplate from "../template/BasedTemplate";
import { ListContainer } from "../styles/Base";

export interface IFriendsData {
  id: number;
  profileImage: string;
  name: string;
  telephone: string;
  selected?: boolean;
}

export interface IGroupMaker {
  props: {
    state: IFriendsData[];
    setState?: Function;
  };
  select?: boolean;
}

function GroupMaker() {
  const [data, setData] = useState<IFriendsData[]>([]);
  useEffect(() => {
    const friendsCopy = [...data];
    for (let i = 0; i < friendsCopy.length; i++) {
      friendsCopy[i] = {
        ...friendsCopy[i],
        selected: false,
      };
    }
    setData(friendsCopy);
  }, []);
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
        <span>그룹 초대</span>
        <span>완료</span>
      </div>
    );
  };
  const Container = () => {
    return (
      <>
        <GroupMakerSelectedList props={{ state: data, setState: setData }} />
        <ListContainer>
          <FriendList
            props={{ state: data, setState: setData }}
            select={true}
          />
        </ListContainer>
      </>
    );
  };
  return <BasedTemplate header={<Header />} container={<Container />} />;
}

export default GroupMaker;
