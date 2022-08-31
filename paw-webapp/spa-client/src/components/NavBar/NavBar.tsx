//i18 translations
import { useState } from "react";
import { useTranslation } from "react-i18next";
import { Link, useLocation, useNavigate } from "react-router-dom";

import "../../common/i18n/index";
import {
  NavBarContainer,
  NavBarLogoSection,
  NavBarBandifyLogo,
  NavBarItemList,
  NavBarItem,
} from "./styles";

export default function NavBar() {
  const { t } = useTranslation();
  const [isLogged, setIsLogged] = useState<boolean>(false);
  const sections = [
    { path: "/auditions", name: t("NavBar.auditions") },
    // TODO: PONER BIEN LOS LINKS DESPUES
  ];

  function login() {
    setIsLogged(!isLogged);
  }

  return (
    <>
      <head>
        <title>{t("NavBar.title")}</title>
      </head>
      <body>
        <button onClick={login}>Clickeame para logear</button>
        <NavBarContainer>
          {/* TODO: NO SE QUE HACE ESTO <span id="langspan" style="display: none;"><spring:message code="app.lang"/></span> */}

          <NavBarLogoSection href="/">
            <img src="./images/logo.png" alt="{t('NavBar.logo')}" />
            <NavBarBandifyLogo>{t("NavBar.bandify")}</NavBarBandifyLogo>
          </NavBarLogoSection>
          <div style={{ width: "full" }}>
            <NavBarItemList>
              {sections.map((section) => (
                <NavBarItem key={section.path}>
                  <Link to={section.path}>{section.name}</Link>
                </NavBarItem>
              ))}
              <NavBarItem>
                {isLogged ? (
                  <Link to="/welcome">{t("NavBar.Profile")}</Link>
                ) : (
                  <button>logeate papa</button>
                )}
              </NavBarItem>
            </NavBarItemList>
          </div>
        </NavBarContainer>
      </body>
    </>
  );
}
