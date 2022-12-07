import { FooterCopyright, Lang, PageFooter } from "./styles";
import "../../styles/footer.css";

export default function Footer() {
  return (
    <footer className="page-footer">
      <div className="footer-copyright">
        <div className="flex flex-row justify-between px-10">
          © 2022 Copyright: Bandify
          <div>
            <b>Contact us: </b>
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
