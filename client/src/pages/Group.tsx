import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useSetRecoilState } from "recoil";
import { selectedGroupState } from "../states/selectedGroup";
import * as A from "../atoms/_index";
import { GoPerson } from "react-icons/go";
import { ListContainer } from "../styles/Base";
import AddModal from "../organisms/AddModal";
import styled from "styled-components";
import { useMutation, useQuery, useQueryClient } from "react-query";
import api from "../auth/api";
import defaultImage from "../image/out.jpg";
import { RoundElement } from "../organisms/RoundElement";

interface IGroup {
  groupId: number;
  groupName: string;
  imageURL: string;
  membersNames: string[];
}

function Group() {
  const setSelectedGroup = useSetRecoilState(selectedGroupState);
  const [showModal, setShowModal] = useState(false);
  const [groupName, setGroupName] = useState("");

  const navigate = useNavigate();
  const queryClient = useQueryClient();

  const { data: groupListData } = useQuery<IGroup[]>(
    ["groupList"],
    async () => {
      const { data } = await api.get("/groups");
      return data;
    }
  );

  const { mutate } = useMutation(
    async () => {
      const requestBody = {
        groupName,
      };
      return api
        .post("/groups", requestBody)
        .then((res) => {
          navigate(`/team/${res.data}/invite`);
        })
        .catch((err) => {
          alert(err.response.data.message);
        });
    },
    {
      onSuccess: () => {
        queryClient.invalidateQueries("groupList");
      },
    }
  );

  const onBtnClick = () => {
    mutate();
  };
  const onGroupClick = (id: number, name: string) => {
    navigate(`/team/${id}/promise`);
    setSelectedGroup({ id, name });
  };
  const onToggleClick = () => {
    setShowModal((prev) => !prev);
  };

  const makeMembersString = (members: string[]) => {
    if (members) {
      let result = "";
      members.forEach((value, idx) => {
        result += value;
        if (idx != members.length - 1) {
          result += ", ";
        }
      });
      return result;
    }
  };

  return (
    <>
      {!showModal && <A.CreateBtn value={"팀 생성"} onClick={onToggleClick} />}
      {groupListData && (
        <ListContainer
          initial={{ opacity: 0 }}
          animate={{ opacity: 1 }}
          exit={{ opacity: 0 }}
          transition={{ type: "linear" }}
        >
          {groupListData &&
            groupListData?.map((value, index) => (
              <RoundElement
                key={value.groupId}
                onClick={() => onGroupClick(value.groupId, value.groupName)}
                head={
                  <A.ProfileImg
                    imgProps={{
                      type: "group",
                      imgSrc: `${
                        value.imageURL ? value.imageURL : defaultImage
                      }`,
                    }}
                  />
                }
                main={value.groupName}
                sub={
                  <A.IconText
                    icon={<GoPerson />}
                    text={makeMembersString(value.membersNames)}
                  />
                }
              />
            ))}
        </ListContainer>
      )}
      <AddModal
        props={{
          titleText: "팀 생성",
          placeholderText: "팀 이름",
          btnText: "생성",
          onBtnClick,
          showModal,
          setShowModal,
          inputValue: groupName,
          setInputValue: setGroupName,
        }}
      />
    </>
  );
}

export default Group;
