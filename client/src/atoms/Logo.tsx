import styled from "styled-components";
interface ILogo {
  value: string;
  onClick?: React.MouseEventHandler;
}
export const Logo = ({ value, onClick }: ILogo) => {
  return <SLogo onClick={onClick}>{value}</SLogo>;
};

const SLogo = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: winkle !important;
  font-size: 3rem;
  color: ${(p) => p.theme.green};
  text-shadow: -2px 1px 0px #ffffff;
  margin-block: 0.5em;
  background-color: transparent;
  cursor: pointer;
`;
