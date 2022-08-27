//i18 translations
import { useTranslation } from "react-i18next";
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
  return (
    <>
      <head>
        <title>{t("NavBar.title")}</title>
      </head>
      <body>
        <NavBarContainer>
          {/* TODO: NO SE QUE HACE ESTO <span id="langspan" style="display: none;"><spring:message code="app.lang"/></span> */}

          <NavBarLogoSection href="/">
            <img src="./images/logo.png" alt="{t('NavBar.logo')}" />
            <NavBarBandifyLogo>{t("NavBar.bandify")}</NavBarBandifyLogo>
          </NavBarLogoSection>
          <div style={{ width: "full" }}>
            <NavBarItemList>
              {/* TODO: conditional rendering o states */}
              <NavBarItem href="/auditions">Audiciones</NavBarItem>
              <NavBarItem href="/auditions">About us</NavBarItem>
              <NavBarItem href="/auditions">Audiciones</NavBarItem>
            </NavBarItemList>
          </div>
        </NavBarContainer>
      </body>
    </>
  );
}
