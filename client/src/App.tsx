import { motion } from "framer-motion";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { useRecoilState } from "recoil";
import styled, { createGlobalStyle, ThemeProvider } from "styled-components";
import { darkModeState } from "./states/darkmode";
import Login from "./routes/Login";
import { darkTheme, lightTheme } from "./Theme";
import Test from "./Test";
const GlobalStyle = createGlobalStyle`
@font-face { 
  font-family: 'Winkle';
  src: url("/asset/WinkleRegular.ttf");
}
html, body, div, span, applet, object, iframe,
h1, h2, h3, h4, h5, h6, p, blockquote, pre,
a, abbr, acronym, address, big, cite, code,
del, dfn, em, img, ins, kbd, q, s, samp,
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
  font-family: 'Roboto', sans-serif;
  font: inherit;
  vertical-align: baseline;
  background-color: ${(p) => p.theme.bgColor};
  color: ${(p) => p.theme.textColor}
}
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
  const onToggleClick = () => {
    setDark((prev: boolean) => !prev);
  };
  return (
    <ThemeProvider theme={dark ? darkTheme : lightTheme}>
      <DarkmodeToggle dark={dark ? "dark" : "light"} onClick={onToggleClick}>
        {dark ? (
          <Circle layoutId="darkmode" dark={dark ? "dark" : "light"} />
        ) : (
          <Circle layoutId="darkmode" dark={dark ? "dark" : "light"} />
        )}
      </DarkmodeToggle>
      <GlobalStyle />
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Login />} />
          <Route path="/test" element={<Test />} />
        </Routes>
      </BrowserRouter>
    </ThemeProvider>
  );
}

export default App;

const DarkmodeToggle = styled(motion.div)<{ dark: string }>`
  background-color: ${(p) =>
    p.dark == "dark" ? p.theme.green : p.theme.bgColor};
  border: solid 2px ${(p) => (p.dark == "dark" ? "transparent" : p.theme.green)};
  position: fixed;
  left: 10px;
  top: 10px;
  width: 46px;
  height: 28px;
  border-radius: 20px;
  cursor: pointer;
`;
const Circle = styled(motion.div)<{ dark: string }>`
  position: relative;
  left: ${(p) => (p.dark == "dark" ? "20px" : "2px")};
  top: 2px;
  width: 20px;
  height: 20px;
  border-radius: 20px;
  background-color: ${(p) => (p.dark == "dark" ? "white" : p.theme.green)};
`;
