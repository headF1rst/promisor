import React from "react";
import { useForm } from "react-hook-form";
import styled from "styled-components";
interface ILoginForm {
  email: string;
  password: string;
}
function Login() {
  const { register, setValue, handleSubmit } = useForm<ILoginForm>();
  const onValid = ({ email, password }: ILoginForm) => {
    console.log(email, password);
  };
  const onInvalid = ({ email, password }: any) => {
    console.log(email, password);
  };
  return (
    <div
      style={{
        display: "flex",
        flexDirection: "column",
        padding: "30px",
      }}
    >
      <Header>
        <span>LOGIN</span>
      </Header>
      <Container>
        <Logo window={window.innerWidth}>Promisor</Logo>
        <LoginForm onSubmit={handleSubmit(onValid, onInvalid)}>
          <LoginInput>
            <div>EMAIL</div>
            <input
              {...register("email", {
                required: "Email is required.",
              })}
            />
          </LoginInput>
          <LoginInput>
            <div>PASSWORD</div>
            <input
              {...register("password", {
                required: "Password is required.",
              })}
              type="password"
            />
          </LoginInput>
          <LoginButton>Login</LoginButton>
        </LoginForm>
        <Tab>
          아직 계정이 없으십니까?
          <span>가입하기</span>
        </Tab>
      </Container>
    </div>
  );
}
export default Login;
const Header = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;
const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;
const Logo = styled.div<{ window: number }>`
  width: 90vw;
  height: ${(p) => (p.window >= 1000 ? "20vw" : "30vw")};
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: winkle;
  font-size: 3rem;
  color: ${(p) => p.theme.green};
`;
const LoginForm = styled.form`
  width: 60vw;
  height: 30vh;
`;
const LoginInput = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  div {
    position: relative;
    background-color: white;
    width: fit-content;
    top: 10px;
    left: 10px;
    padding-inline: 10px;
    font-size: 0.9rem;
    color: ${(p) => p.theme.grey};
  }
  input {
    width: 100%;
    height: 40px;
    border: solid 1px ${(p) => p.theme.grey};
    border-radius: 5px;
    padding-inline: 20px;
    padding-top: 15px;
    padding-bottom: 10px;
    &:focus {
      outline: none;
    }
  }
`;
const LoginButton = styled.button`
  width: 100%;
  border: none;
  text-align: center;
  background-color: black;
  color: ${(p) => p.theme.white};
  border-radius: 5px;
  margin-top: 15px;
  padding: 10px;
  font-size: 0.8rem;
  cursor: pointer;
`;
const Tab = styled.div`
  color: ${(p) => p.theme.grey};
  font-size: 0.9rem;
  margin-top: 30px;
  span {
    padding-inline: 8px;
    color: black;
    font-weight: 600;
  }
`;
