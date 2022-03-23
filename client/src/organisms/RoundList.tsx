import styled from "styled-components";
import { IProfile } from "../atoms/Profile";
import * as A from "../atoms/_index";

interface IRoundList extends IProfile {
  onClick: React.MouseEventHandler;
  elementProps: {
    title: string;
    chat?: string;
  };
}

export const RoundList = ({
  onClick,
  profileProps,
  imgProps,
  elementProps,
}: IRoundList) => {
  return (
    <SFlatList onClick={onClick}>
      <A.ProfileImg profileProps={profileProps} imgProps={imgProps} />
      <SFlatElement>
        <div>{elementProps.title}</div>
        {elementProps.chat && <span>{elementProps.chat}</span>}
      </SFlatElement>
    </SFlatList>
  );
};

const SFlatList = styled.div`
  width: 100%;
  height: 9vh;
  @media screen and (min-width: 900px) {
    height: 12vh;
  }
  padding: 0.8rem;
  cursor: pointer;
  display: flex;
  flex-direction: row;
  margin-bottom: 0.5em;
  border-radius: 1em;
  background-color: ${(p) => p.theme.smoke};
  align-items: center;
`;

const SFlatElement = styled.div`
  display: flex;
  flex-direction: column;
  background-color: transparent;

  div,
  span {
    background-color: transparent;
    height: 1.2em;
    overflow: hidden;
  }
  div {
    font-weight: 500;
    font-size: 1rem;
  }
  span {
    font-size: 0.8rem;
    margin-top: 0.5rem;
    color: ${(p) => p.theme.grey};
  }
`;
