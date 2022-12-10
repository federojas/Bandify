import * as React from "react";

import { BandifyLogoImg } from "../../components/NavBar/styles";
import BandifyLogo from "../../images/logo.png";

import { useTranslation } from "react-i18next";

import "../../styles/forms.css";
import "../../styles/register.css";
import "../../js/alerts.js";

import RegisterBandForm from "../../components/RegisterBandForm";

const RegisterContent = () => {
  const { t } = useTranslation();

  return (
    // <div>
    //   {t("Register.header")}
    //   <a href="/registerBand">{t("Register.band")}</a>
    //   <a href="/registerArtist">{t("Register.artist")}</a>
    // </div>

    <main>
      <div className="register-content flex flex-col">
        <div className="card-content">
          <div className="header">
            {t("Register.header")}
            <div className="forms-buttons">
              <a href="/registerArtist">

                  {t("Register.artist")}
              </a>
              <a href="/registerBand">
                {t("Register.band")}
              </a>
            </div>
          </div>
        </div>
      </div>
    </main>
    //       {(
    //         <>
    //           <div id="artist-form" style={{ display: 'block' }}>
    //             {/* TODO Include the registerArtistForm component here */}
    //             {/* <RegisterArtistForm artist={1} /> */}
    //           </div>
    //           <div id="band-form" style={{ display: 'none' }}>
    //             {/* TODO Include the registerBandForm component here */}
    //             <RegisterBandForm />
    //           </div>
    //         </>
    //       ) : (
    //         <>
    //           <div id="artist-form" style={{ display: 'none' }}>
    //             {/* TODO Include the registerArtistForm component here */}
    //             {/* <RegisterArtistForm artist={1} /> */}
    //           </div>
    //           <div id="band-form" style={{ display: 'block' }}>
    //             {/* TODO  Include the registerBandForm component here */}
    //             <RegisterBandForm/>
    //           </div>
    //         </>
    //       )}
    //     </div>
    //   </div>
    //   <div id="snackbar">Snackbar.message</div>
    // </main>
  );
};

export default RegisterContent;
