import { atom } from "recoil";
import { recoilPersist } from "recoil-persist";

const { persistAtom } = recoilPersist();
export const darkModeState = atom({
  key: "darkmode",
  default: false,
  effects_UNSTABLE: [persistAtom],
});
