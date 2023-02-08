import React from "react";
import { useTranslation } from "react-i18next";
import ManagerTabs from "../../components/ManagerTabs";

import "../../styles/welcome.css";
import "../../styles/auditions.css";
import "../../styles/applicants.css";
import "../../styles/invites.css";

import NextIcon from '../../assets/icons/page-next.png';

const ProfileInvites = () => {
  const { t } = useTranslation();
  const invites = {
    invites: [
        {
            bandId: 1,
            bandName: "My Band",
            inviteDescription: "My Band is looking for a drummer",
            membershipId: 1,
            memberRoles: ["Drummer"],
        },
        {
            bandId: 1,
            bandName: "My Band",
            inviteDescription: "My Band is looking for a guitarist",
            membershipId: 2,
            memberRoles: ["Guitarist"],
        },
    ],
    currentPage: 1,
    lastPage: 1,
  };

  return (
    <div className="manager-page">
      <ManagerTabs tabItem={2} />
      <div className="manager-items-container">
        <span className="manager-items-container">
          {invites.invites.length === 0 ? (
            <>{t("ProfileInvites.title1")}</>
          ) : (
            <>{t("ProfileInvites.title2")}</>
          )}
        </span>

        <hr className="rounded" />
        

        {invites.invites.length === 0 ? (
          <p className="no-invites">{t("ProfileInvites.none")}</p>
        ) : (
          <div className="manager-items-list">
            <div>
              <ul className="collapsible">
                {/* {invites.invites.map((invite, index) => {
                  <InviteItem {...invite}/>
                })} */}
              </ul>
            </div>
          </div>
        )}

        {/* TODO: PAgination */}
        <div className="pagination-invites">
          {invites.currentPage > 1 && (
            <>
              <a onClick={() => getPaginationURL(invites.currentPage - 1)}>
                <img
                  src={NextIcon}
                  alt="Previous"
                  className="pagination-next rotate"
                />
              </a>
            </>
          )}
          <b>
            {t("Pagination.page")} {invites.currentPage} {t("Pagination.of")} {invites.lastPage}
          </b>
          {invites.currentPage < invites.lastPage && (
            <>
              <a onClick={() => getPaginationURL(invites.currentPage + 1)}>
                <img
                  src={NextIcon}
                  alt="Next"
                  className="pagination-next"
                />
              </a>
            </>
          )}
        </div>
      </div>
    </div>
  );
};

export default ProfileInvites;
