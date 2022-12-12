import React from "react";
import "../../styles/applicationItem.css";
import { Invite } from "../../types";
import { useTranslation } from "react-i18next";
import RejectIcon from '../../assets/icons/reject.svg';
import SuccessIcon from '../../assets/icons/success.svg';
import UserIcon from '../../assets/icons/user.svg';


const InviteItem: React.FC<Invite> = ({
  bandId,
  bandName,
  inviteDescription,
  memberRoles,
  membershipId,
}) => {
  return (
    <li>
      <div className="collapsible-header applicant-header">
        <div className="image-and-name">
          <img
            className="postcard-profile-image"
            src={`/user/${bandId}/profile-image`}
            alt="Profile"
          />
          <span className="user-name">{bandName}</span>
          <div />
        </div>
      </div>
      <div className="collapsible-body applicant-body">
        <div>
          <span className="message-span">{inviteDescription}</span>
        </div>
        <div className="roles-div my-5">
          {memberRoles.map((role: any) => (
            <span className="roles-span">{role.name}</span>
          ))}
        </div>
        <div className="applicant-body-end">
          <div className="purple-button">
            <a href={`/user/${bandId}`} className="usr-url">
              <img
                src={UserIcon}
                className="audition-icon invert"
                alt="Applicants"
              />
            </a>
          </div>
          <div>
            <div className="application-icons">
              <form
                action={`/invites/${membershipId}?accept=ACCEPTED`}
                method="post"
              >
                <button type="submit">
                  <img
                    src={SuccessIcon}
                    alt="Accept"
                    className="application-icon"
                  />
                </button>
              </form>
              <form
                action={`/invites/${membershipId}?accept=REJECTED`}
                method="post"
              >
                <button onClick={openConfirmation}>
                  <img
                    src={RejectIcon}
                    alt="Reject"
                    className="application-icon"
                  />
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </li>
  );
};

export default InviteItem;
