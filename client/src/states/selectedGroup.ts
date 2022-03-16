import { atom } from "recoil";
import { recoilPersist } from "recoil-persist";

const { persistAtom } = recoilPersist();
export const selectedGroupState = atom({
  key: "selected",
  default: { id: -1, name: "" },
  effects_UNSTABLE: [persistAtom],
});
