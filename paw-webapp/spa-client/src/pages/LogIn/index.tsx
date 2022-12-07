import * as React from "react";
import Avatar from "@mui/material/Avatar";
import Button from "@mui/material/Button";
import CssBaseline from "@mui/material/CssBaseline";
import TextField from "@mui/material/TextField";
import FormControlLabel from "@mui/material/FormControlLabel";
import Checkbox from "@mui/material/Checkbox";
import Link from "@mui/material/Link";
import Grid from "@mui/material/Grid";
import Box from "@mui/material/Box";
import Typography from "@mui/material/Typography";
import Container from "@mui/material/Container";
import { createTheme, ThemeProvider } from "@mui/material/styles";
import { BandifyLogoImg } from "../../components/NavBar/styles";
import BandifyLogo from "../../images/logo.png";

import "../../styles/login.css";
import "../../styles/welcome.css";
import "../../styles/forms.css";
import "../../styles/alerts.css";
import "../../styles/forms.css";


const LoginBox = () => {
  return (
    <div className="login-box">
      <div className="general-div" id="login">
        <form action="/login" method="post">
          <div className="form-group">
            <label htmlFor="email" className="form-label">
              Welcome.email
            </label>
            <input
              type="text"
              required
              className="form-input"
              id="email"
              name="email"
              placeholder="Welcome.email"
            />
            <p id="invalidMail" className="error" style={{ display: "none" }}>
              Welcome.error.invalidMail
            </p>
          </div>
          <div className="form-group">
            <label htmlFor="password" className="form-label">
              Welcome.password
            </label>
            <input
              type="password"
              required
              className="form-input"
              id="password"
              name="password"
              placeholder="Welcome.password"
            />
          </div>
          <p id="invalidPassword" className="error" style={{ display: "none" }}>
            Welcome.error.invalidPassword
          </p>
          <div className="check-box">
            <input
              type="checkbox"
              name="rememberMe"
              id="rememberMe"
              className="remember-me"
            />
            <label htmlFor="rememberMe">Welcome.rememberme</label>
          </div>
          <a href="/resetPassword">
            <u className="login-reset-button">Welcome.resetButton</u>
          </a>
          <div className="errorDiv">
            <p className="error">
              {/* TODO param.error &&  */}
              {<>Welcome.login.auth.failed</>}
            </p>
          </div>
          <div className="loginButton">
            {/* TODO onClick={() => loginFormCheck()} */}
            <button type="submit" className="purple-hover-button">
              Welcome.loginButton
            </button>
          </div>
        </form>
        <div className="notMemberYet">
          <p>Welcome.notMemberYet</p>
          &nbsp;&nbsp;
          <b>
            <a href="/register">
              <u className="login-register-button">Welcome.registerButton</u>
            </a>
          </b>
        </div>
      </div>
    </div>
  );
};

const Login = () => {
  return (
    <main className="flex justify-center">
      <div className="login-loginform">
        <LoginBox />
      </div>
    </main>
  );
};

export default Login;
