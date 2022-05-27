import styled from "styled-components";
import * as A from "../atoms/_index";
import * as O from "../organisms/_index";
import { BsFillTelephoneFill } from "react-icons/bs";
import { AnimatePresence, motion } from "framer-motion";
import { IGroupMaker } from "../pages/GroupMaker";
import { phoneNumberFormatter } from "../utils/phoneNumberFormatter";
import { RoundElement } from "./RoundElement";
function FriendList({ select, props }: IGroupMaker) {
  return (
    <>
      {props.state && (
        <AnimationContainer
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          exit={{ opacity: 0 }}
          transition={{ type: "linear" }}
        >
          {props.state.map((value, idx) => (
            <RoundElement
              key={idx}
              head={
                <A.ProfileImg
                  imgProps={{ type: "group", imgSrc: value.profileImage }}
                />
              }
              main={value.name}
              sub={
                <A.IconText
                  icon={<BsFillTelephoneFill />}
                  text={phoneNumberFormatter(value.telephone)}
                />
              }
              tail={select === "checkbox" && "checkbox" + value.id}
            />
          ))}
        </AnimationContainer>
      )}
    </>
  );
}

export default FriendList;

const AnimationContainer = styled(motion.div)`
  transform-origin: top;
  div:last-child {
    border: none;
  }
  /* initial={{ scaleY: 0 }}
        animate={{ scaleY: 1 }}
        exit={{ scaleY: 0 }}
        transition={{ type: "linear" }} */
`;
