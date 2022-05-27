import { atom } from "recoil";

export const selectedFriendsAtom = atom({
  key: "selectedFriends",
  default: new Set<number>(),
});
