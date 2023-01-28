import { KAKAO_AUTH_URL } from "../auth/OAuth";
import axios from "axios";
import { Code } from "../atoms/Code";
import { useRecoilState } from "recoil";
import React from "react";

function NLogin() {
  return (
    <a href={KAKAO_AUTH_URL}>
      <button>카카오로 로그인하기</button>
    </a>
  );
}

export default NLogin;
