//i18 translations
import { useState } from "react";
import { useTranslation } from "react-i18next";
import { Link, useLocation, useNavigate } from "react-router-dom";
import BandifyLogo from "../../images/logo.png";
import './navbar.css';

function Nav() {
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
    <nav>
      <div className="nav-container nav-div">
        <a href="/" className="logo-section">
          <img
            src="/resources/images/logo.png"
            className="bandify-logo"
            alt="Bandify"
          />
          <span className="bandify-title">Bandify</span>
        </a>

        <div className="w-full md:block md:w-auto" id="mobile-menu">
          <ul>
            <li>
              <a href="/auditions" className="block py-2 pr-4 pl-3 text-white rounded text-2xl">Auditions</a>
            </li>
            {/* isLogged && */}
            {(
              <li>
                <a href="/users" className="block py-2 pr-4 pl-3 text-white rounded text-2xl">Users</a>
              </li>
            )}
            {!isLogged && (
              <>
                <li>
                  <a href="/aboutUs" className="block py-2 pr-4 pl-3 text-white rounded text-2xl">About Us</a>
                </li>
                {/* param.navItem !== 0 && param.navItem !== 5 && */}
                { (
                  <li>
                    <div className="flex">
                      <a href="/login" className="purple-login-button">Login</a>
                    </div>
                  </li>
                )}
              </>
            )}
            {isLogged && (
              <>
              {/* hasRole('BAND') && */}
                { (
                  <li>
                    <a href="/newAudition" className="block py-2 pr-4 pl-3 text-white rounded text-2xl">Post</a>
                  </li>
                )}
                {/* hasRole('ARTIST') && */}
                { (
                  <li>
                    <a href="/profile/applications" className="block py-2 pr-4 pl-3 text-white rounded text-2xl">
                      <img src="/resources/icons/manager.svg" className="profile-icon-img" alt="Manager" />
                      Manager
                    </a>
                  </li>
                )}
                <li>
                  <a href="/profile" className="block py-2 pr-4 pl-3 text-white rounded text-2xl">Profile</a>
                </li>
                <li>
                  <a href="/logout" className="block py-2 pr-4 pl-3 text-white rounded text-2xl">Logout</a>
                </li>
              </>
            )}
          </ul>
        </div>
      </div>
    </nav>
  );
}

export default Nav;
