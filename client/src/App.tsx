import { BrowserRouter, Route, Routes } from "react-router-dom";
import Login from "./routes/Login";
function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
