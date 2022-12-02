//i18 translations
import { useState } from "react";
import { useTranslation } from "react-i18next";
import { Link, useLocation, useNavigate } from "react-router-dom";
import BandifyLogo from "../../images/logo.png";

import "../../common/i18n/index";
import {
  NavBarContainer,
  NavBarLogoSection,
  NavBarBandifyLogo,
  NavBarItemList,
  NavBarItem,
  Nav,
  BandifyLogoImg,
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
    <Nav>
      {/* <button onClick={login}>Clickeame para logear</button> */}
      <NavBarContainer>
        {/* TODO: NO SE QUE HACE ESTO <span id="langspan" style="display: none;"><spring:message code="app.lang"/></span> */}

        <NavBarLogoSection href="/">
          <BandifyLogoImg src={BandifyLogo}/>
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
    </Nav>
  );
}
