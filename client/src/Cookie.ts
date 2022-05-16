import Cookies from "universal-cookie";

const cookies = new Cookies();

export const setCookie = (name: string, value: string, option?: Object) => {
  return cookies.set(name, value, option);
};

export const getCookie = (name: string) => {
  return cookies.get(name);
};
