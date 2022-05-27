import React, { useEffect } from "react";
import { useMatch } from "react-router-dom";
import * as A from "../atoms/_index";
import Friend from "./Friend";
import Group from "./Group";
import BasedTemplate from "../template/BasedTemplate";
import MyPage from "./MyPage";
import { getCookie } from "../Cookie";
import { useNavigate } from "react-router";
function Home() {
  const groupMatch = useMatch("team");
  const friendMatch = useMatch("friend");
  const navigate = useNavigate();
  useEffect(() => {
    if (!getCookie("accessToken") || !localStorage.getItem("refreshTokenId")) {
      navigate("/login");
    }
  }, []);

  const HomeHeader = () => {
    return <A.Logo value={"Promisor"} />;
  };
  const Container = () => {
    return friendMatch ? <Friend /> : groupMatch ? <Group /> : <MyPage />;
  };
  return (
    <>
      <BasedTemplate header={<HomeHeader />} container={<Container />} />
    </>
  );
}

export default React.memo(Home);
