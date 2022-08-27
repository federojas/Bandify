//i18 translations
import { useTranslation } from "react-i18next";
import "../../common/i18n/index";
import { NavBarContainer } from "./styles";
export default function NavBar() {
  const { t } = useTranslation();
  return (
    <>
      <head>
        <title>{t("NavBar.title")}</title>
      </head>
      <body>
        <NavBarContainer></NavBarContainer>
      </body>
    </>
  );
}
