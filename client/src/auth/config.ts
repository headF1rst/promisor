import { getCookie } from "../Cookie";

export const config = {
  headers: {
    Authorization: "Bearer " + getCookie("accessToken"),
  },
};
