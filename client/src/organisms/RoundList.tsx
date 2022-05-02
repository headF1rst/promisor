import { motion } from "framer-motion";
import styled from "styled-components";
import { DotsVertical } from "../atoms/DotsVertical";
interface IRoundList {
  onClick?: React.MouseEventHandler;
  head: Object;
  main: string;
  sub?: Object;
  tail?: boolean;
}

export const RoundList = ({ onClick, head, main, sub, tail }: IRoundList) => {
  return (
    <SFlatList>
      <Row onClick={onClick}>
        {head}
        <SFlatElement>
          <div>{main}</div>
          {sub}
        </SFlatElement>
      </Row>
      {tail && <DotsVertical />}
    </SFlatList>
  );
};

export const SFlatList = styled(motion.div)`
  width: 100%;
  @media screen and (min-width: 900px) {
    height: 12vh;
  }
  padding: 0.8rem;
  display: flex;
  justify-content: space-between;
  flex-direction: row;
  margin-bottom: 0.1em;
  background-color: ${(p) => p.theme.opacity50};
  align-items: center;
  border-radius: 0.1em;
  box-shadow: 0px 1px 1px 1px rgba(0, 0, 0, 0.1);
  div,
  span {
    background-color: transparent;
  }
`;
const Row = styled.div`
  display: flex;
  flex-direction: row;
  background-color: ${(p) => p.theme.smoke};
  cursor: pointer;
`;
export const SFlatElement = styled.div`
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
