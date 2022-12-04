import React from "react";
import "./App.css";
import { ThemeProvider } from "styled-components";
import {
  BrowserRouter as Router,
  Route,
  Routes,
  Navigate,
} from "react-router-dom";
import Welcome from "./pages/Welcome";
import LogIn from "./pages/LogIn";
import SignUp from "./pages/SignUp";
import MainLayout from "./components/layouts";
import ForgotPassword from "./pages/ForgotPassword";
import AuditionsPage from "./pages/Auditions/Auditions";

const theme = {
  colors: {
    white: "#ffffff",
  }
}
function App() {
  return (

    <ThemeProvider theme={theme}>
    <Router basename={process.env.PUBLIC_URL}>
      <Routes>
        <Route path="/" element={<MainLayout />}>
          <Route index element={<Navigate to={"/welcome"} />} />
          <Route path="welcome" element={<Welcome />} />
          <Route path="login" element={<LogIn />} />
        </Route>
        <Route path="/signup" element={<SignUp />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />
        <Route path="/" element={<MainLayout />}>
          <Route index element={<Navigate to={"/auditions"} />} />
          <Route path="auditions" element={<AuditionsPage />} />
        </Route>
        {/* <Route path="/auditions" element={<AuditionsPage></AuditionsPage>} /> */}
      </Routes>
    </Router>
    </ThemeProvider>
  );
}

export default App;
