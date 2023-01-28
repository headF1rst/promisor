import axios from "axios";
import React, { useEffect, useState } from "react";
import { useRecoilState } from "recoil";
import { Code } from "../atoms/Code";
import { KAKAO_AUTH_URL, REST_API_KEY, REDIRECT_URI } from "../auth/OAuth";
const CLIENT_SECRET = "zeIcDLHYSw9Yu4yUUCSkWJSanp8uD679";
function KakaoLogin() {
  let params = new URL(document.URL).searchParams;
  let KAKAO_CODE = params.get("code");

  const getKakaoCode = () => {
    localStorage.setItem("CODE", KAKAO_CODE);
  };

  useEffect(() => {
    let params = new URL(document.URL).searchParams;
    let KAKAO_CODE = params.get("code");
    console.log(KAKAO_CODE);
    getKakaoCode();
    let accessToken = "";
    axios
      .post(
        `https://kauth.kakao.com/oauth/token?grant_type=authorization_code&client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&code=${KAKAO_CODE}&client_secret=${CLIENT_SECRET}`,
        {
          headers: {
            "Content-type": "application/x-www-form-urlencoded;charset=utf-8",
          },
        },
      )
      .then(res => {
        axios
          .post("http://localhost:8000/oauth/kakao/login", {
            accessToken: res.data.access_token,
          })
          .then(res => {
            console.log(res);
          });
      });
  }, []);

  return <div>CODE: {KAKAO_CODE}</div>;
}

export default KakaoLogin;
