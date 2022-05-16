import styled from "styled-components";
import * as A from "../atoms/_index";
import * as O from "../organisms/_index";
import { BsFillTelephoneFill } from "react-icons/bs";
import { AnimatePresence, motion } from "framer-motion";
import { IGroupMaker } from "../pages/GroupMaker";
import { phoneNumberFormatter } from "../utils/phoneNumberFormatter";
function FriendList({ select, props }: IGroupMaker) {
  const onListClick = (id: number) => {
    if (!select) return;
    if (props.state && props.setState) {
      const friendsCopy = [...props.state];
      friendsCopy[id] = {
        ...friendsCopy[id],
        selected: !friendsCopy[id].selected,
      };
      props.setState(friendsCopy);
    }
  };
  return (
    <AnimatePresence>
      <AnimationContainer>
        {props.state.map((value, idx) => (
          <O.RoundList
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
            tail={select ? false : true}
            onClick={() => onListClick(value.id)}
            key={idx}
          />
        ))}
      </AnimationContainer>
    </AnimatePresence>
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
