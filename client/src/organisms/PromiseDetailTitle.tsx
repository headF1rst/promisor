import React, { useState } from "react";
import styled from "styled-components";
import { useForm } from "react-hook-form";
import { useMutation, useQueryClient } from "react-query";
import { AiFillEdit } from "react-icons/ai";
import api from "../auth/api";
interface IPromiseTitle {
  promiseTitle: string;
}
interface IPromiseDetailData {
  id: number;
  name: string;
  time: string;
  location: string;
}
const PromiseDetailTitle = (props: IPromiseDetailData) => {
  const [editing, setEditing] = useState(false);
  const { register, setValue, handleSubmit } = useForm<IPromiseTitle>();
  const { id, name, time, location } = props;
  const queryClient = useQueryClient();

  const { mutate: patchPromiseInfo } = useMutation(
    async (title: string) => {
      const requestBody = {
        name: title,
        date: time,
        location,
      };
      return await api.patch(`promises/${id}`, requestBody);
    },
    {
      onSuccess: () => {
        console.log(id);
        queryClient.invalidateQueries(["promiseDetailData", String(id)]);
      },
      mutationKey: ["patchPromiseInfoTitle", id],
    }
  );

  const onEditingClick = () => {
    setEditing(true);
  };
  const onTitleValid = ({ promiseTitle }: IPromiseTitle) => {
    patchPromiseInfo(promiseTitle);
    setValue("promiseTitle", promiseTitle);
    setEditing(false);
  };
  return (
    <>
      {editing ? (
        <Form onSubmit={handleSubmit(onTitleValid)}>
          <LineInput
            placeholder="제목"
            defaultValue={name}
            {...register("promiseTitle")}
          />
          <button type={"submit"}>완료</button>
        </Form>
      ) : (
        <RowDiv>
          <div>{name}</div>
          <button onClick={onEditingClick}>
            <AiFillEdit />
          </button>
        </RowDiv>
      )}
    </>
  );
};

export default PromiseDetailTitle;

const LineInput = styled.input`
  width: 80%;
  height: 5vh;
  border: none;
  border-bottom: solid 1px ${(p) => p.theme.grey};
  &:focus {
    outline: none;
  }
  background-color: transparent;
`;
const RowDiv = styled.div`
  display: flex;
  flex-direction: row;
  width: 80%;
  justify-content: space-between;
  div {
    font-size: 1.3em;
  }
  button {
    border: none;
    background: none;
    cursor: pointer;
    color: grey;
    &:hover {
      text-decoration: underline;
    }
  }
`;
const Form = styled.form`
  display: flex;
  flex-direction: row;
  width: 80%;
  justify-content: space-between;
  button {
    border: none;
    background: none;
    cursor: pointer;
    color: grey;
    &:hover {
      text-decoration: underline;
    }
  }
`;
