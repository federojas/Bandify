import React from "react";
import logo from "./logo.svg";
import "./App.css";
import { ChakraProvider } from "@chakra-ui/react";
import {
  BrowserRouter as Router,
  Route,
  Routes,
  Navigate,
} from "react-router-dom";
import Welcome from "./pages/Welcome";
import customTheme from "./theme/index";
import NavBar from "./components/NavBar";
import SignIn from "./pages/SignIn";
import SignUp from "./pages/SignUp";
import MainLayout from "./components/layouts/MainLayout";
import ForgotPassword from "./pages/ForgotPassword";
import AuditionsPage from "./pages/Auditions";
function App() {
  return (
    <ChakraProvider theme={customTheme}>
      {/* <NavBar /> */}

      <Router basename={process.env.PUBLIC_URL}>
        <Routes>
          <Route path="/" element={<MainLayout />}>
            <Route index element={<Navigate to={"/welcome"} />} />
            <Route path="page" element={<Welcome />} />
          </Route>
          <Route path="/signup" element={<SignUp />} />
          <Route path="/login" element={<SignIn />} />
          <Route path="/welcome" element={<Welcome />} />
          <Route path="/forgot-password" element={<ForgotPassword />} />
          <Route path="/" element={<MainLayout />}>
            <Route index element={<Navigate to={"/auditions"} />} />
            <Route path="auditions" element={<AuditionsPage />} />
          </Route>
          {/* <Route path="/auditions" element={<AuditionsPage></AuditionsPage>} /> */}
        </Routes>
      </Router>
    </ChakraProvider>
  );
}

export default App;
