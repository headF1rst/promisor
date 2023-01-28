import { BrowserRouter, HashRouter, Route, Routes } from "react-router-dom";
import { useRecoilState } from "recoil";
import { createGlobalStyle, ThemeProvider } from "styled-components";
import { darkModeState } from "./states/darkmode";
import { darkTheme, lightTheme } from "./Theme";
import { Friend, Login, GroupMaker, Promise, GroupChatRoom, Group, Home, PromiseDate, PromisePlace } from "./pages/_index";
import KakaoLogin from "./pages/KakaoLogin";
import NLogin from "./pages/NLogin";
import { useEffect } from "react";
import { getCookie } from "./Cookie";
const GlobalStyle = createGlobalStyle`
@font-face { 
  font-family: 'Winkle';
  src: url("/asset/WinkleRegular.ttf");
}
@font-face {
    font-family: 'InfinitySans-RegularA1';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/InfinitySans-RegularA1.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}
html, body, div, span, applet, object, iframe,
h1, h2, h3, h4, h5, h6, p, blockquote, pre,
a, abbr, acronym, address, big, cite, code,
del, dfn, em, ins, kbd, q, s, samp,
small, strike, strong, sub, sup, tt, var,
b, u, i, center,
dl, dt, dd, menu, ol, ul, li,
fieldset, form, label, legend,
table, caption, tbody, tfoot, thead, tr, th, td,
article, aside, canvas, details, embed,
figure, figcaption, footer, header, hgroup,
main, menu, nav, output, ruby, section, summary,
time, mark, audio, video {
  margin: 0;
  padding: 0;
  border: 0;
  font-size: 100%;
  font-family: 'InfinitySans-RegularA1';
  font: inherit;
  font-weight: 400;
  vertical-align: baseline;
  background-color: ${p => p.theme.bgColor};
  color: ${p => p.theme.textColor}
}
img{
  background-color:rgba(0, 0, 0, 0);
}
::selection { background-color: rgba(4, 201, 148, 0.2)}
/* HTML5 display-role reset for older browsers */
article, aside, details, figcaption, figure,
footer, header, hgroup, main, menu, nav, section {
  display: block;
}
/* HTML5 hidden-attribute fix for newer browsers */
*[hidden] {
    display: none;
}
body {
  line-height: 1;
}
input{
  font-family: "Nanum Gothic";

}
menu, ol, ul {
  list-style: none;
}
blockquote, q {
  quotes: none;
}
blockquote:before, blockquote:after,
q:before, q:after {
  content: '';
  content: none;
}
table {
  border-collapse: collapse;
  border-spacing: 0;
}
* {
  box-sizing: border-box;
}
body {
  font-weight: 300;
  color:black;
  line-height: 1.2;
  overflow-x:hidden;
}
a {
  text-decoration:none;
  color:inherit;
}
`;
function App() {
  const [dark, setDark] = useRecoilState(darkModeState);
  return (
    <ThemeProvider theme={dark ? darkTheme : lightTheme}>
      <GlobalStyle />
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<NLogin />} />
          <Route path="/oauth/kakao" element={<KakaoLogin />} />
          <Route path="/register" element={<Login />} />
          <Route path="/team/:id/invite" element={<GroupMaker />} />
          <Route path="/team/:id/promise" element={<Promise />} />
          <Route path="/team/:id/promise/:pid/date" element={<PromiseDate />} />
          <Route path="/team/:id/promise/:pid/place" element={<PromisePlace />} />
          <Route path="/team/:id" element={<GroupChatRoom />} />
          <Route path="/" element={<Home />}>
            <Route path="/team" element={<Group />}></Route>
            <Route path="/friend" element={<Friend />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </ThemeProvider>
  );
}

export default App;
