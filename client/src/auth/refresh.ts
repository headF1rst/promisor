import axios, { AxiosRequestConfig } from "axios";
import { getCookie, setCookie } from "../Cookie";

const refresh = async (
  config: AxiosRequestConfig
): Promise<AxiosRequestConfig> => {
  const refreshTokenId = localStorage.getItem("refreshTokenId");
  let accessToken = getCookie("accessToken");
  const expiredAt = localStorage.getItem("tokenExpireTime");

  if (Number(expiredAt) - new Date().getTime() < 0 && refreshTokenId) {
    const body = {
      refreshId: refreshTokenId,
    };

    await axios
      .get("/members/token/refresh", {
        params: { ...body },
      })
      .then((res) => {
        accessToken = res.data.data.accessToken;
        setCookie("accessToken", accessToken);
        localStorage.setItem("refreshTokenId", res.data.data.refreshTokenId);
        localStorage.setItem("tokenExpireTime", res.data.data.expiredAt);
      });
  }

  config.headers["Authorization"] = `Bearer ${accessToken}`;

  return config;
};

const refreshError = () => {
  localStorage.removeItem("refreshTokenId");
};

export { refresh, refreshError };
