import "../../styles/forms.css";
import "../../styles/alerts.css";
import "../../js/register.js";
import React from "react";
import { useTranslation } from "react-i18next";
import { useForm } from "react-hook-form";

interface FormData {
  email: string;
  password: string;
  passwordConfirmation: string;
  name: string;
  surname: string;
}

const RegisterArtistForm = () => {
  const { t } = useTranslation();
  const {
    register,
    handleSubmit,
    setError,
    formState: { errors },
  } = useForm<FormData>();

  const onSubmit = (data: FormData) => {
    console.log(data);
  };

  const registerArtistOptions = {
    email: {
      required: "Email is required",
      maxLength: {
        value: 254,
        message: "Email cannot be larger than 254 characters",
      },
      pattern: /^\S+@\S+$/i,
    },
    password: {
      required: "Password is required",
      minLength: {
        value: 8,
        message: "Password must be at least 8 characters",
      },
      maxLength: {
        value: 25,
        message: "Password cannot be larger than 25 characters",
      },
    },
    passwordConfirmation: {
      required: "Password confirmation is required",
    },
    name: {
      required: "Name is required",
      maxLength: {
        value: 50,
        message: "Name cannot be larger than 50 characters",
      },
    },
    surname: {
      required: "Surname is required",
      maxLength: {
        value: 50,
        message: "Surname cannot be larger than 50 characters",
      },
    },
  };

  return (
    <div className="register-content">
      <form onSubmit={handleSubmit(onSubmit)} className="box">
        <div>
          <label className="form-label" htmlFor="email">
            {t("Register.email")}
          </label>
          {/* Message for register.form.emailplaceholder */}
          {/* TODO placeholder={/* emailplaceholder } */}
          <input
            type="email"
            maxLength={255}
            // id="bandEmaill"
            className="form-input"
            // name="email"
            {...register("email", registerArtistOptions.email)}
            placeholder={t("Register.email_placeholder")}
          />
          {/* form errors for email */}
          <p className="error" id="wrongMail">
            {errors.email && <span>{errors.email.message}</span>}
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
            // name="password"
            {...register("password", registerArtistOptions.password)}
            // onKeyUp={() => checkPasswordBand()}
            // TODO: Check both passwords match
          />
          {/* form errors for password */}
          <p className="error" id="emptyPass">
            {errors.password && <span>{errors.password.message}</span>}
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
            // name="passwordConfirmation"
            {...register("passwordConfirmation", registerArtistOptions.passwordConfirmation)}
            // onKeyUp={() => checkPasswordArtist()}
            // TODO: Check both passwords match
          />
          <p className="error" id="emptyPass">
            {errors.passwordConfirmation && <span>{errors.passwordConfirmation.message}</span>}
            {/* Message for register.form.emptyPassword with arguments 8, 25 */}
          </p>
          {/* form errors for passwordConfirmation */}
          <span
            id="match_message"
            className="success"
          >
              {/* TODO: Message for matching passwords  */}
            {/* Message for register.form.passwordmatch */}
          </span>
          <span
            id="nonmatch_message"
            className="error"
          >
            {/* {errors.pass} */}
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
            // name="name"
            {...register("name", registerArtistOptions.name)}
          />
          {/* form errors for name */}
          <p className="error" id="wrongName" style={{ display: "none" }}>
            {/* Message for register.form.invalidName */}
            {errors.name && <span>{errors.name.message}</span>}
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
            // name="name"
            {...register("surname", registerArtistOptions.surname)}
          />
          {/* form errors for name */}
          <p className="error" id="wrongName" style={{ display: "none" }}>
            {/* Message for register.form.invalidName */}
            {errors.surname && <span>{errors.surname.message}</span>}
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
  );
};

export default RegisterArtistForm;
