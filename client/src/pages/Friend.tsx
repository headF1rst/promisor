import React, { useState } from "react";
import FriendList from "../organisms/FriendList";
import { AnimatePresence } from "framer-motion";
import * as A from "../atoms/_index";
import FriendAdd from "../organisms/FriendAdd";
import { ListContainer } from "../styles/Base";
import ThinBar from "../atoms/ThinBar";
import { useQuery } from "react-query";
import api from "../auth/api";

function Friend() {
  const [modal, setModal] = useState(false);
  const [showFriends, setShowFriends] = useState(true);
  const onToggleClick = () => {
    setModal((prev) => !prev);
  };

  const { data } = useQuery("friendList", async () => {
    const { data } = await api.get("/friends/list");
    return data;
  });
  return (
    <>
      <ListContainer>
        <ThinBar
          visible={String(showFriends)}
          onShowClick={() => setShowFriends((p) => !p)}
          text={"내 친구"}
        />
        {showFriends && data && <FriendList props={{ state: data }} />}
      </ListContainer>
      {!modal && <A.CreateBtn value={"친구 추가"} onClick={onToggleClick} />}
      <AnimatePresence>
        {modal && <FriendAdd setModal={setModal} onClick={onToggleClick} />}
      </AnimatePresence>
    </>
  );
}

export default Friend;
