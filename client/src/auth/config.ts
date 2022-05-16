import { getCookie } from "../Cookie";

const config = {
  headers: {
    Authorization: "Bearer " + getCookie("accessToken"),
  },
};

export default config;
