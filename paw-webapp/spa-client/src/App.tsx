import React from "react";
import {
  BrowserRouter as Router,
  Route,
  Routes,
  Navigate,
} from "react-router-dom";
import Welcome from "./pages/Welcome";
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
  return (
    <Router>
      <Routes>
        <Route path="/" element={<MainLayout />}>
          <Route index element={<Navigate to={"/welcome"} />} />
          <Route path="welcome" element={<Welcome />} />
          <Route path="login" element={<AnonymousRoute component={Login} />} />
          <Route path="register" element={<AnonymousRoute component={Register} />} />
          <Route path="registerBand" element={<AnonymousRoute component={RegisterBand} />} />
          <Route path="registerArtist" element={<AnonymousRoute component={RegisterArtist} />} />
          <Route path="profile" element={<PrivateRoute component={Profile} roles={["ARTIST", "BAND"]} />} />
          <Route path="user" element={<Discover />} />
          <Route path="user/:id" element={<User />} />
          <Route path="resetPassword" element={<AnonymousRoute component={ForgotPassword} />} />
          <Route path="audition" element={<AuditionsPage />} />
          <Route path="audition/:id" element={<Audition/>} />
          <Route path="audition/:id/edit" element={<PrivateRoute component={EditAudition} roles={["BAND"]} />} />
          <Route path="audition/:id/applicants" element={<PrivateRoute component={AuditionApplicants} roles={["BAND"]} />} />
          <Route path="newAudition" element={<PrivateRoute component={NewAudition} roles={["BAND"]} />} />
          <Route path="audition/search" element={<AuditionSearch />} />
          <Route path="user/search" element={<Index />} />
          <Route path="profile/applications" element={<PrivateRoute component={Applications} roles={["ARTIST"]} />} />
          <Route path="profile/invites" element={<PrivateRoute component={Invites} roles={["ARTIST"]} />} />
          <Route path="profile/auditions" element={<PrivateRoute component={BandAuditions} roles={["BAND"]} />} />
          <Route path="user/:id/auditions" element={<PublicBandAuditions />} />
          <Route path="profile/editArtist" element={<PrivateRoute component={EditArtist} roles={["ARTIST"]} />} />
          <Route path="profile/editBand" element={<PrivateRoute component={EditBand} roles={["BAND"]} />} />
          <Route path='error' element={<Error />} />
          <Route path='emailSent' element={<AnonymousRoute component={EmailSent} />}/>
          <Route path='resetPassword/emailSent' element={<AnonymousRoute component={ResetPwdEmailSent} />}/>
          <Route path='newPassword' element={<NewPassword />} />
          <Route path='user/:id/associates' element={<Associates />} />
          <Route path='profile/editAssociates' element={<PrivateRoute component={EditAssociates} roles={["BAND"]} />} />
          <Route path='profile/associates' element={<PrivateRoute component={ProfileAssociates} roles={["BAND", "ARTIST"]} />} />
          <Route path='verify' element={<Verify />} />
          <Route path='*' element={<Error />} />
        </Route>
      </Routes>
    </Router>
  );
}

export default App;
