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
import { PrivateRoute } from "./PrivateRoute";
import { AnonymousRoute } from "./AnonymousRoute";
import Login from "./pages/LogIn";
import EditArtist from "./pages/EditProfile/EditArtist";
import EditBand from "./pages/EditProfile/EditBand";
import NewAudition from "./pages/Audition/NewAudition";
import EditAudition from "./pages/Audition/EditAudition";
import Index from "./pages/UsersSearch";
import Invites from "./pages/Hub/Invites"
import Applications from "./pages/Hub/Applications"
import Error from "./pages/Error"
import PublicBandAuditions from "./pages/User/publicBandAuditions";

function App() {
  // TODO: revisar las paginas anonimas, comunes a todos o privadas
  // TODO: por ejemplo, la welcome page tiene sentido que la puedan ver todos?
  return (
    <Router basename={process.env.PUBLIC_URL}>
      <Routes>
        <Route path="/" element={<MainLayout />}>
          <Route index element={<Navigate to={"/welcome"} />} />
          <Route path="welcome" element={<Welcome />} />
          <Route path="login" element={<AnonymousRoute component={Login} />} />
          <Route path="register" element={<AnonymousRoute component={Register} />} />
          <Route path="registerBand" element={<AnonymousRoute component={RegisterBand} />} />
          <Route path="registerArtist" element={<AnonymousRoute component={RegisterArtist} />} />
          <Route path="profile" element={<PrivateRoute component={Profile} roles={["ARTIST", "BAND"]} />} />
          <Route path="users" element={<Discover />} />
          <Route path="users/:id" element={<User />} />
          <Route path="forgot-password" element={<ForgotPassword />} />
          <Route path="auditions" element={<AuditionsPage />} />
          <Route path="auditions/:id" element={<PrivateRoute component={Audition} roles={["ARTIST", "BAND"]} />} />
          <Route path="auditions/:id/edit" element={<PrivateRoute component={EditAudition} roles={["BAND"]} />} />
          <Route path="newAudition" element={<PrivateRoute component={NewAudition} roles={["BAND"]} />} />
          <Route path="auditions/search" element={<AuditionSearch />} />
          <Route path="users/search" element={<Index />} />
          <Route path="applications" element={<PrivateRoute component={Applications} roles={["ARTIST"]}/>} />
          <Route path="invites" element={<PrivateRoute component={Invites} roles={["ARTIST"]}/>} />
          {/* TODO: falta hacer los components de todo esto */}
          <Route path="profile/auditions" element={<PrivateRoute component={BandAuditions} roles={["BAND"]} />} />
          {/*TODO: PORQ NO ANDA?????? TIRA 403*/}
          <Route path="users/:id/auditions" element={<PublicBandAuditions />} />
          {/* <Route path="profile/applications" element={<PrivateRoute component={ProfileApplications} roles={["ARTIST"]} />} /> */}
          <Route path="profile/editArtist" element={<PrivateRoute component={EditArtist} roles={["ARTIST"]} />} />
          <Route path="profile/editBand" element={<PrivateRoute component={EditBand} roles={["BAND"]} />} />
          <Route path="auditions/:id/applicants" element={<PrivateRoute component={AuditionsPage} roles={["BAND"]} />} />
          <Route path='error' element={<Error />} />
        </Route>
      </Routes>
    </Router>
  );
}

export default App;
