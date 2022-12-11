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
import Register from "./pages/Register";
import MainLayout from "./components/layouts";
import ForgotPassword from "./pages/ForgotPassword";
import AuditionsPage from "./pages/Auditions/Auditions";
import Profile from "./pages/Profile";
import AboutUs from "./pages/AboutUs";
import Discover from "./pages/Discover";
import RegisterArtist from "./pages/RegisterArtist";
import RegisterBand from "./pages/RegisterBand";
import Audition from "./pages/Audition";
import AuditionSearch from "./pages/AuditionSearch";

const theme = {
  colors: {
    white: "#ffffff",
  },
};

const dagos = {
  id: 1,
  name: "Dagos",
  email: "agustin.dagostino@hotmail.com",
  available: true,
  band: false,
};

function App() {
  return (
    <ThemeProvider theme={theme}>
      <Router basename={process.env.PUBLIC_URL}>
        <Routes>
          <Route path="/" element={<MainLayout />}>
            <Route index element={<Navigate to={"/welcome"} />} />
            <Route path="welcome" element={<Welcome />} />
            <Route path="login" element={<LogIn />} />
            <Route path="register" element={<Register />} />
            <Route path="profile" element={<Profile user={dagos} />} />
            <Route path="aboutUs" element={<AboutUs />} />
            <Route path="users" element={<Discover />} />
            <Route path="registerBand" element={<RegisterBand />} />
            <Route path="registerArtist" element={<RegisterArtist />} />
            <Route path="forgot-password" element={<ForgotPassword />} />
            <Route path="auditions" element={<AuditionsPage />} />
            <Route path="auditions/:id" element={<Audition />} />
            {/* TODO */}
            <Route path="auditions/search" element={<AuditionSearch />} />
            <Route path="user/:id" element={<Profile user={dagos}/>} />
            <Route path="bandAuditions/:id" element={<AuditionsPage />} />
            <Route path="profile/applications" element={<AuditionsPage />} />
            <Route path="profile/invites" element={<AuditionsPage />} />
            <Route path="profile/auditions" element={<AuditionsPage />} />
            <Route path="profile/editBand" element={<AuditionsPage />} />
            <Route path="profile/editArtist" element={<AuditionsPage />} />
            <Route path="newAudition" element={<AuditionsPage />} />
            <Route path="users/search" element={<Discover />} />
          </Route>          
        </Routes>
      </Router>
    </ThemeProvider>
  );
}

export default App;
