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
