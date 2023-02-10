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
import ForgotPassword from "./pages/LogIn/ResetPassword";
import AuditionsPage from "./pages/Auditions";
import Profile from "./pages/Profile";
import Discover from "./pages/Discover";
import RegisterArtist from "./pages/RegisterArtist";
import RegisterBand from "./pages/RegisterBand";
import Audition from "./pages/Audition";
import AuditionSearch from "./pages/AuditionSearch";
import User from "./pages/User";
import BandAuditions from "./pages/BandAuditions";
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
import PublicBandAuditions from "./pages/User/PublicBandAuditions";
import AuditionApplicants from "./pages/Audition/Applicants";
import EmailSent from "./pages/Results/EmailSent";
import ResetPwdEmailSent from "./pages/Results/ResetPwdEmailSent";
import NewPassword from "./pages/LogIn/NewPassword";
import Associates from "./pages/User/Associates";
import ProfileAssociates from "./pages/Profile/Associates";
import EditAssociates from "./pages/Profile/EditAssociates";
import Verify from "./pages/LogIn/Verify";

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
          <Route path="resetPassword" element={<ForgotPassword />} />
          <Route path="auditions" element={<AuditionsPage />} />
          <Route path="auditions/:id" element={<PrivateRoute component={Audition} roles={["ARTIST", "BAND"]} />} />
          <Route path="auditions/:id/edit" element={<PrivateRoute component={EditAudition} roles={["BAND"]} />} />
          <Route path="auditions/:id/applicants" element={<PrivateRoute component={AuditionApplicants} roles={["BAND"]} />} />
          <Route path="newAudition" element={<PrivateRoute component={NewAudition} roles={["BAND"]} />} />
          <Route path="auditions/search" element={<AuditionSearch />} />
          <Route path="users/search" element={<Index />} />
          <Route path="applications" element={<PrivateRoute component={Applications} roles={["ARTIST"]} />} />
          <Route path="invites" element={<PrivateRoute component={Invites} roles={["ARTIST"]} />} />
          <Route path="profile/auditions" element={<PrivateRoute component={BandAuditions} roles={["BAND"]} />} />
          <Route path="users/:id/auditions" element={<PublicBandAuditions />} />
          <Route path="profile/editArtist" element={<PrivateRoute component={EditArtist} roles={["ARTIST"]} />} />
          <Route path="profile/editBand" element={<PrivateRoute component={EditBand} roles={["BAND"]} />} />
          <Route path='error' element={<Error />} />
          <Route path='emailSent' element={<EmailSent />} />
          <Route path='resetPassword/emailSent' element={<ResetPwdEmailSent />} />
          <Route path='newPassword' element={<NewPassword />} />
          <Route path='users/:id/associates' element={<Associates />} />
          <Route path='profile/editAssociates' element={<EditAssociates />} />
          <Route path='profile/associates' element={<ProfileAssociates />} />
          <Route path='verify' element={<Verify />} />
          <Route path='*' element={<Error />} />
        </Route>
      </Routes>
    </Router>
  );
}

export default App;
