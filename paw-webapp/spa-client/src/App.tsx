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
import SignIn from "./pages/SignIn";
import SignUp from "./pages/SignUp";
import MainLayout from "./components/layouts";
import ForgotPassword from "./pages/ForgotPassword";
import AuditionsPage from "./pages/Auditions";
function App() {
  return (


    <Router basename={process.env.PUBLIC_URL}>
      <Routes>
        <Route path="/" element={<MainLayout />}>
          <Route index element={<Navigate to={"/welcome"} />} />
          <Route path="welcome" element={<Welcome />} />
        </Route>
        <Route path="/signup" element={<SignUp />} />
        <Route path="/login" element={<SignIn />} />
        <Route path="/forgot-password" element={<ForgotPassword />} />
        {/* <Route path="/" element={<MainLayout />}>
          <Route index element={<Navigate to={"/auditions"} />} />
          <Route path="auditions" element={<AuditionsPage />} />
        </Route> */}
        {/* <Route path="/auditions" element={<AuditionsPage></AuditionsPage>} /> */}
      </Routes>
    </Router>
  );
}

export default App;
