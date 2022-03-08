import { atom } from "recoil";

export const selectedState = atom<number[]>({
  key: "created",
  default: [],
});
