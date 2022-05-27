import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { useMatch } from "react-router-dom";
import styled from "styled-components";
import BasedTemplate from "../template/BasedTemplate";
import * as S from "../styles/_index";
import * as A from "../atoms/_index";
import { setCookie } from "../Cookie";
import api from "../auth/api";
import axios from "axios";
interface ILoginForm {
  name: string;
  email: string;
  password: string;
  passwordConfirm: string;
  telephone: string;
}
function Login() {
  const { register, setValue, handleSubmit } = useForm<ILoginForm>();
  const navigate = useNavigate();
  const loginMatch = useMatch("/login");

  const onLoginValid = ({ email, password }: ILoginForm) => {
    const requestBody = { email, password };
    api
      .post("/members/login", requestBody)
      .then((res) => {
        const {
          data: { accessToken, refreshTokenId },
        } = res;
        setCookie("accessToken", accessToken);
        localStorage.setItem("refreshTokenId", refreshTokenId);
        console.log(res);
        navigate("/");
      })
      .catch((err) => {
        console.log(err);
        alert(err.response.data.message);
      });
  };
  const onRegisterValid = ({
    name,
    email,
    password,
    passwordConfirm,
    telephone,
  }: ILoginForm) => {
    if (password !== passwordConfirm) {
      alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
      return;
    }
    const requestBody = {
      name,
      email,
      password,
      telephone,
      memberRole: "USER",
    };
    api
      .post("/members", requestBody)
      .then((res) => {
        alert(`${email} 로 인증 메일을 전송하였습니다.`);
        navigate("/login");
      })
      .catch((err) => {
        alert(err.response.data.message);
      });
  };
  const onRegisterInvalid = (v: any) => {
    console.log(v);
  };
  const onPushClick = () => {
    if (loginMatch) {
      navigate("/register");
    } else {
      navigate("/login");
    }
  };
  const Header = () => {
    return (
      <>
        <span></span>
        <span>{loginMatch ? "LOGIN" : "REGISTER"}</span>
        <span></span>
      </>
    );
  };
  const Container = () => {
    return (
      <>
        <A.Logo value={"Promisor"} />
        <LoginForm
          page={loginMatch ? "login" : "register"}
          onSubmit={
            loginMatch
              ? handleSubmit(onLoginValid)
              : handleSubmit(onRegisterValid, onRegisterInvalid)
          }
        >
          {!loginMatch && (
            <S.LabelInput>
              <div>NAME</div>
              <S.Input
                {...register("name", {
                  required: "Name is required.",
                })}
              />
            </S.LabelInput>
          )}
          <S.LabelInput>
            <div>EMAIL</div>
            <S.Input
              type="email"
              {...register("email", {
                required: "Email is required.",
              })}
            />
          </S.LabelInput>
          <S.LabelInput>
            <div>PASSWORD</div>
            <S.Input
              {...register("password", {
                required: "Password is required.",
              })}
              type="password"
            />
          </S.LabelInput>
          {!loginMatch && (
            <>
              <S.LabelInput>
                <div>PASSWORD CONFIRM</div>
                <S.Input
                  {...register("passwordConfirm", {
                    required: "Password Confirm is required.",
                  })}
                  type="password"
                />
              </S.LabelInput>
              <S.LabelInput>
                <div>PHONE NUMBER</div>
                <S.Input
                  {...register("telephone", {
                    required: "Phone number is required.",
                  })}
                />
              </S.LabelInput>
            </>
          )}
          <LoginButton>{loginMatch ? "Login" : "Join"}</LoginButton>
        </LoginForm>
        <Tab>
          {loginMatch ? "아직 계정이 없으십니까?" : "계정이 있으십니까?"}
          <span onClick={onPushClick}>
            {loginMatch ? "가입하기" : "로그인하기"}
          </span>
        </Tab>
      </>
    );
  };
  return <BasedTemplate header={<Header />} container={<Container />} />;
}
export default Login;

const LoginForm = styled.form<{ page: string }>`
  width: 60vw;
  height: ${(p) => (p.page === "login" ? "45vh" : "60vh")};
  margin-top: 1em;
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
  span {
    padding-inline: 8px;
    font-weight: 600;
    cursor: pointer;
  }
  z-index: 1;
`;
