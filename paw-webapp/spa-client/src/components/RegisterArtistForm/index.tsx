import "../../styles/forms.css";
import "../../styles/alerts.css";
import "../../js/register.js";
import React from "react";
import { useTranslation } from "react-i18next";

const RegisterArtistForm = () => {
    const { t } = useTranslation();


    return (
        <div className="register-content">
      <form
        action="/registerBand"
        method="post"
        acceptCharset="utf-8"
        className="box"
      >
        <div>
          <label className="form-label" htmlFor="email">
            {t("Register.email")}
          </label>
          {/* Message for register.form.emailplaceholder */}
          {/* TODO placeholder={/* emailplaceholder } */}
          <input
            type="text"
            maxLength={254}
            id="bandEmaill"
            className="form-input"
            name="email"
            placeholder={t("Register.email_placeholder")}
          />
          {/* form errors for email */}
          <p className="error" id="wrongMail" style={{ display: "none" }}>
            {/* Message for Email.userBandForm.email */}
          </p>
        </div>

        <div>
          <label className="form-label" htmlFor="password">
            {t("Register.password")}
          </label>
          {/* Message for register.form.passwordplaceholder */}
          {/* TODO placeholder={/* passwordplaceholder } */}
          <input
            id="password_band"
            type="password"
            maxLength={25}
            placeholder={t("Register.password_placeholder")}
            className="form-input"
            name="password"
            onKeyUp={() => checkPasswordBand()}
          />
          {/* form errors for password */}
          <p className="error" id="emptyPass" style={{ display: "none" }}>
            {/* Message for register.form.emptyPassword with arguments 8, 25 */}
          </p>
        </div>

        <div>
          <label className="form-label" htmlFor="passwordConfirmation">
            {t("Register.confirmPassword")}
          </label>
          {/* Message for register.form.passwordplaceholder */}
          {/* TODO placeholder={/* passwordplaceholder } */}
          <input
            id="confirm_password_band"
            type="password"
            maxLength={25}
            placeholder={t("Register.password_placeholder")}
            className="form-input"
            name="passwordConfirmation"
            onKeyUp={() => checkPasswordArtist()}
          />
          {/* form errors for passwordConfirmation */}
          <span
            id="match_message"
            className="success"
            style={{ display: "none" }}
          >
            {/* Message for register.form.passwordmatch */}
          </span>
          <span
            id="nonmatch_message"
            className="error"
            style={{ display: "none" }}
          >
            {/* Message for register.form.passwordnomatch */}
          </span>
        </div>

        <div>
          <label className="form-label" htmlFor="name">
            {t("Register.artist_name")}
          </label>
          {/* Message for register.form.nameplaceholder */}
          {/* TODO placeholder={/* nameplaceholder } */}
          <input
            type="text"
            maxLength={50}
            placeholder={t("Register.artist_name")}
            className="form-input"
            id="artistName"
            name="name"
          />
          {/* form errors for name */}
          <p className="error" id="wrongName" style={{ display: "none" }}>
            {/* Message for register.form.invalidName */}
          </p>
        </div>

        <div>
          <label className="form-label" htmlFor="name">
            {t("Register.artist_surname")}
          </label>
          {/* Message for register.form.nameplaceholder */}
          {/* TODO placeholder={/* nameplaceholder } */}
          <input
            type="text"
            maxLength={50}
            placeholder={t("Register.artist_surname")}
            className="form-input"
            id="artistSurname"
            name="name"
          />
          {/* form errors for name */}
          <p className="error" id="wrongName" style={{ display: "none" }}>
            {/* Message for register.form.invalidName */}
          </p>
        </div>

        {/* TODO onClick={() => registerbandCheck()} */}
        <div className="end-button-div">
          <button type="submit" value="submit" className="purple-button">
            {t("Register.button")}
          </button>
        </div>
      </form>
    </div>
    )
}

export default RegisterArtistForm;