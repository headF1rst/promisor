import React, { useState } from "react";
import styled from "styled-components";
import Select from "react-select";
function PromiseDateRecommendation() {
  const [near, setNear] = useState(true);
  const onRadioChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    if (e.target.value === "far") {
      setNear(false);
    } else {
      setNear(true);
    }
  };
  return (
    <Container>
      <RowDiv>
        <span>추천 날짜</span>
        <ul>
          <RadioLabel isSelected={near}>
            <input
              style={{ display: "none" }}
              onChange={onRadioChange}
              type="radio"
              name="nearfar"
              value="near"
            />
            가까운 순
          </RadioLabel>
          <RadioLabel isSelected={!near}>
            <input
              style={{ display: "none" }}
              onChange={onRadioChange}
              type="radio"
              name="nearfar"
              value="far"
            />
            먼 순
          </RadioLabel>
        </ul>
      </RowDiv>
      <div></div>
    </Container>
  );
}

export default PromiseDateRecommendation;
const Container = styled.div`
  display: flex;
  flex-direction: column;
  width: 70vw;
`;
const List = styled.div`
  display: flex;
  flex-direction: column;
  background-color: transparent;
  margin-top: 0.5em;
  overflow-y: scroll;
`;
const RowDiv = styled.div`
  display: flex;
  flex-direction: row;
  gap: 1em;
  justify-content: flex-start;
`;

const RadioLabel = styled.label<{ isSelected: boolean }>`
  margin-right: 0.5em;
  font-weight: ${(p) => (p.isSelected ? 700 : 500)};
  font-size: 0.9em;
  text-decoration: underline;
`;
