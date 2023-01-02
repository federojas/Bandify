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
import AuditionsPage from "./pages/Auditions";
import Profile from "./pages/Profile";
import Discover from "./pages/Discover";
import RegisterArtist from "./pages/RegisterArtist";
import RegisterBand from "./pages/RegisterBand";
import Audition from "./pages/Audition";
import AuditionSearch from "./pages/AuditionSearch";
import User from "./pages/User";
import BandAuditions from "./pages/BandAuditions";
import ProfileApplications from "./pages/ProfileApplications";
import ProfileInvites from "./pages/ProfileInvites";

const dagos = {
  id: 1,
  name: "Leonardo D'Agostino",
  email: "agustin.dagostino@hotmail.com",
  available: true,
  band: false,
  location: "CABA",
  description: "Soy un artista",
};

function App() {
  return (
    <Router basename={process.env.PUBLIC_URL}>
      <Routes>
        <Route path="/" element={<MainLayout />}>
          <Route index element={<Navigate to={"/welcome"} />} />
          <Route path="welcome" element={<Welcome />} />
          <Route path="login" element={<LogIn />} />
          <Route path="register" element={<Register />} />
          <Route path="registerBand" element={<RegisterBand />} />
          <Route path="registerArtist" element={<RegisterArtist />} />
          <Route path="profile" element={<Profile user={dagos} />} />
          <Route path="users" element={<Discover />} />
          <Route path="forgot-password" element={<ForgotPassword />} />
          <Route path="auditions" element={<AuditionsPage />} />
          <Route path="auditions/:id" element={<Audition />} />
          <Route path="auditions/search" element={<AuditionSearch />} />
          <Route path="user/:id" element={<User user={dagos} />} />
          <Route path="bandAuditions/:id" element={<BandAuditions />} />
          <Route path="profile/auditions" element={<BandAuditions />} />
          {/* TODO */}
          <Route
            path="profile/applications"
            element={<ProfileApplications />}
          />
          <Route path="profile/invites" element={<ProfileInvites />} />
          <Route path="auditions/:id/applicants" element={<AuditionsPage />} />
          <Route path="users/search" element={<Discover />} />
          <Route path="profile/editBand" element={<AuditionsPage />} />
          <Route path="profile/editArtist" element={<AuditionsPage />} />
          <Route path="newAudition" element={<AuditionsPage />} />
        </Route>
      </Routes>
    </Router>
  );
}

export default App;
