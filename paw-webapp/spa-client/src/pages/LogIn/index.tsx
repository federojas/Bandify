import * as React from "react";
import "../../styles/login.css";
import "../../styles/welcome.css";
import "../../styles/forms.css";
import "../../styles/alerts.css";
import "../../styles/forms.css";
import { loginService } from "../../services";
import { useForm } from 'react-hook-form';

import { useTranslation } from "react-i18next";
import "../../common/i18n/index";

interface FormData {
  email: string;
  password: string;
  rememberMe: boolean;
}



const LoginBox = () => {
  const { t } = useTranslation();

  const { register, handleSubmit, getValues } = useForm<FormData>();
  let emailError = "";
  let passwordError = "";

  const onSubmit = (data: FormData) => {
    const values = getValues();
    // check for validation errors on the "email" and "password" fields
    emailError = values.email ? "" : 'This field is required';
    passwordError = values.password ? "" : 'This field is required';
  
    console.log(values);
    // submit the form data, for example to an API
    loginService.login(values.email, values.password).then((response) => {
      console.log(response);
    });
  };

  return (
    <div className="login-box">
      <div className="general-div" id="login">
        <form onSubmit={handleSubmit(onSubmit)}>
          <div className="form-group">
            <label htmlFor="email" className="form-label">
              {t("Login.email")}
            </label>
            <input
              type="text"
              required
              className="form-input"
              id="email"
              placeholder={t("Login.email")}
              {...register("email", {})}
            />
            {emailError && <span>This field is required</span>}
          </div>
          <div className="form-group">
            <label htmlFor="password" className="form-label">
            {t("Login.password")}
            </label>
            <input
              type="password"
              required
              className="form-input"
              id="password"
              placeholder={t("Login.password")}
              {...register("password", {})}            
            />
            {passwordError && <span>This field is required</span>}
          </div>
          <div className="check-box">
            <input
              type="checkbox"
              name="rememberMe"
              id="rememberMe"
              className="remember-me"
            />
            <label htmlFor="rememberMe">{t("Login.rememberMe")}</label>
          </div>
          <a href="/resetPassword">
            <u className="login-reset-button">{t("Login.forgotPassword")}</u>
          </a>
          {/* <div className="errorDiv">
            <p className="error">
               TODO param.error &&  *
              {<>Welcome.login.auth.failed</>}
            </p>
          </div> */}
          <div className="loginButton">
            <button type="submit" className="purple-hover-button">
            {t("Login.login")}
            </button>
          </div>
        </form>
        <div className="notMemberYet">
          <p>{t("Login.notAMemberyet")}</p>
          &nbsp;&nbsp;
          <b>
            <a href="/register">
              <u className="login-register-button">{t("Login.register")}</u>
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
