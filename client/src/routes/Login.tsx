import React, { useEffect } from "react";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { useMatch } from "react-router-dom";
import styled from "styled-components";

interface ILoginForm {
  email: string;
  password: string;
  passwordConfirm: string;
}
function Login() {
  const { register, setValue, handleSubmit } = useForm<ILoginForm>();
  const navigate = useNavigate();
  const loginMatch = useMatch("/login");
  const onValid = ({ email, password }: ILoginForm) => {
    console.log(email, password);
  };
  const onInvalid = ({ email, password }: any) => {
    console.log(email, password);
  };
  const onPushClick = () => {
    if (loginMatch) {
      navigate("/register");
    } else {
      navigate("/login");
    }
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
        <span>{loginMatch ? "LOGIN" : "REGISTER"}</span>
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
          {!loginMatch && (
            <LoginInput>
              <div>PASSWORD CONFIRM</div>
              <input
                {...register("passwordConfirm", {
                  required: "Password Confirm is required.",
                })}
                type="password"
              />
            </LoginInput>
          )}
          <LoginButton>{loginMatch ? "Login" : "Join"}</LoginButton>
        </LoginForm>
        <Tab>
          {loginMatch ? "아직 계정이 없으십니까?" : "계정이 있으십니까?"}
          <span onClick={onPushClick}>
            {loginMatch ? "가입하기" : "로그인하기"}
          </span>
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
`;
const Logo = styled.div<{ window: number }>`
  width: 90vw;
  height: ${(p) => (p.window >= 1000 ? "15vw" : "30vw")};
  display: flex;
  justify-content: center;
  align-items: center;
  font-family: winkle;
  font-size: 3rem;
  color: ${(p) => p.theme.green};
  text-shadow: -2px 1px 0px #ffffff;
`;
const LoginForm = styled.form`
  width: 60vw;
  height: 45vh;
`;
const LoginInput = styled.div`
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
  input {
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
  }
`;
const LoginButton = styled.button`
  width: 100%;
  border: none;
  text-align: center;
  background-color: ${(p) => p.theme.textColor};
  color: ${(p) => p.theme.bgColor};
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
    font-weight: 600;
    cursor: pointer;
  }
`;
