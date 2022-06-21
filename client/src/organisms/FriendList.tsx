import styled from "styled-components";
import * as A from "../atoms/_index";
import { BsFillTelephoneFill } from "react-icons/bs";
import { AnimatePresence, motion } from "framer-motion";
import { IGroupMaker } from "../pages/GroupMaker";
import { phoneNumberFormatter } from "../utils/phoneNumberFormatter";
import { RoundElement } from "./RoundElement";
import { useForm } from "react-hook-form";
import React from "react";
import { useMutation } from "react-query";
import { useParams } from "react-router";
import api from "../auth/api";
import { useNavigate } from "react-router-dom";

function FriendList({ select, props }: IGroupMaker) {
  const { register, handleSubmit } = useForm();
  const params = useParams();
  const navigate = useNavigate();
  const { mutate: inviteGroupMember } = useMutation(
    async (members: Array<number>) => {
      const requestBody = {
        groupId: params.id,
        memberId: members,
      };
      return await api.post(`/groups/invite`, requestBody);
    },
    {
      onSuccess: () => {
        navigate(`/team`);
      },
    }
  );

  const onSubmit = (e: any) => {
    e.preventDefault();
    let members = [];
    for (let i = 1; i < props.state.length + 1; i++) {
      if (e.target[i].checked) {
        members.push(Number(e.target[i].name));
      }
    }
    inviteGroupMember(members);
  };
  return (
    <form id={"inviteGroupMember"} onSubmit={onSubmit}>
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
              tail={
                select === "checkbox" && (
                  <Checkbox name={`${value.id}`} type={"checkbox"} />
                )
              }
            />
          ))}
        </AnimationContainer>
      )}
    </form>
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
const Checkbox = styled.input`
  accent-color: #04c994;
  zoom: 1.3;
`;
