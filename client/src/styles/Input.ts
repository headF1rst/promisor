import styled from "styled-components";

export const Input = styled.input`
  width: 100%;
  height: 40px;
  border: solid 1px ${(p) => p.theme.grey};
  border-radius: 5px;
  padding-inline: 20px;
  padding-top: 15px;
  padding-bottom: 10px;
  background-color: transparent;
  color: ${(p) => p.theme.textColor};
  &:focus {
    outline: none;
  }
  &:-webkit-autofill {
    -webkit-box-shadow: 0 0 0 1000px ${(p) => p.theme.bgColor} inset;
    -webkit-text-fill-color: ${(p) => p.theme.green};
  }
`;

export const LabelInput = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  div {
    position: relative;
    width: fit-content;
    top: 10px;
    left: 10px;
    padding-inline: 10px;
    font-size: 0.9rem;
    color: ${(p) => p.theme.grey};
  }
  background-color: ${(p) => p.theme.smoke};
`;

export const BoxInput = styled.input`
  width: 80%;
  height: 5vh;
  border: none;
  background-color: ${(p) => p.theme.smoke};
  border-radius: 8px;
  &:focus {
    outline: none;
  }
`;
