import axios, { AxiosRequestConfig } from "axios";
import { getCookie, setCookie } from "../Cookie";

const refresh = async (
  config: AxiosRequestConfig
): Promise<AxiosRequestConfig> => {
  const refreshToken = localStorage.getItem("refreshToken");
  let accessToken = getCookie("accessToken");
  const expiredAt = localStorage.getItem("tokenExpireTime");
  if (Number(expiredAt) - new Date().getTime() < 0 && refreshToken) {
    const body = {
      refreshId: refreshToken,
    };

    const { data } = await axios.get("/members/token/refresh", {
      params: { ...body },
    });

    accessToken = data.data.accessToken;
    setCookie("accessToken", accessToken);
    localStorage.setItem("refreshTokenId", data.data.refreshTokenId);
    localStorage.setItem("expiredAt", data.data.expiredAt);
  }

  config.headers["Authorization"] = `Bearer ${accessToken}`;

  return config;
};

const refreshErrorHandler = () => {
  localStorage.removeItem("refreshTokenId");
};

export { refresh, refreshErrorHandler };
