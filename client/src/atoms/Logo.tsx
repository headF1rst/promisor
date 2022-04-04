import styled from "styled-components";
interface ILogo {
  value: string;
  size?: number;
  onClick?: React.MouseEventHandler;
}
export const Logo = ({ size, value, onClick }: ILogo) => {
  return (
    <SLogo size={size} onClick={onClick}>
      {value}
    </SLogo>
  );
};

const SLogo = styled.div<{ size?: number }>`
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: winkle !important;
  font-size: 2.3em;
  color: ${(p) => p.theme.green};
  text-shadow: -2px 1px 0px #ffffff;
  margin-block: 0.5em;
  background-color: transparent;
  cursor: pointer;
`;
