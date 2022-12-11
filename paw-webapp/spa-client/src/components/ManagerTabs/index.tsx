import React from "react";
import { useTranslation } from "react-i18next";
import MusicIcon1 from "../../assets/icons/music-icon-1.svg";
import UsersNetwork from "../../assets/icons/users-network.svg";


const ManagerTabs = (props: { tabItem: number}) => {
  const { t } = useTranslation();
//   TODO : traer el pendingMembershipsCount de la api
  const pendingMembershipsCount = 0;


  return (
    <div className="manager-tabs">
      <a href="/profile/applications">
        <div
          className={
            props.tabItem === 1 ? "manager-tab-selected" : "manager-tab"
          }
        >
          <div className="manager-tab-title-icon">
            <img
              src={MusicIcon1}
              className="audition-icon"
              alt="My applications"
            />
            <span className="manager-items-title">{t("ManagerTabs.myApplications")}</span>
          </div>
        </div>
      </a>
      <a href="/profile/invites">
        <div
          className={
            props.tabItem === 2 ? "manager-tab-selected" : "manager-tab"
          }
        >
          <div className="manager-tab-title-icon">
            <img
              src={UsersNetwork}
              className="audition-icon"
              alt="My invites"
            />
            <span className="manager-items-title">{t("ManagerTabs.invites")}</span>
          </div>
          {pendingMembershipsCount > 0 && (
            <span className="icon-button__badge">
              {pendingMembershipsCount}
            </span>
          )}
        </div>
      </a>
    </div>
  );
};

export default ManagerTabs;
