import React from "react";
import logo from "./logo.svg";
import "./App.css";
import { ChakraProvider } from "@chakra-ui/react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Home from "./pages/Home";
import customTheme from "./theme/index";
import NavBar from "./components/NavBar";
import SignIn from "./pages/SignIn";
import SignUp from "./pages/SignUp";
import MainLayout from './components/layouts/MainLayout';

function App() {
  return (
    <ChakraProvider theme={customTheme}>
      {/* <NavBar /> */}

      <Router basename={process.env.PUBLIC_URL}>
        <Routes>
          <Route path="/" element={
            <MainLayout />
          }>
            <Route index element={<Home />} />
            <Route path="signup" element={<SignUp />} />
          </Route>
          <Route path="/login" element={<SignIn />} />
        </Routes>
      </Router>
    </ChakraProvider>
  );
}

export default App;
