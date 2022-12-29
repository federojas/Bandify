import { useTranslation } from "react-i18next";
import "../../styles/footer.css";

export default function Footer() {
  const { t } = useTranslation();
  return (
    <footer className="page-footer">
      <div className="footer-copyright">
        <div className="flex flex-row justify-between px-10">
          © 2022 Copyright: Bandify
          <div>
            <b>{t("Footer.contactUs")}: </b>
            <a href="mailto: bandifypaw@gmail.com">bandifypaw@gmail</a>
          </div>
          <div>
            <a className="languages-buttons right" href="?lang=es">
              ES
            </a>
            <a className="languages-buttons right" href="?lang=en">
              EN
            </a>
          </div>
        </div>
      </div>
    </footer>
  );
}
