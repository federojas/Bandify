import React from "react";
import { useTranslation } from "react-i18next";
import UserIcon from "../../assets/icons/user.svg";
import InfoIcon from "../../assets/icons/info.png";
import {Audition} from "../../types";



const BandAuditionCard: React.FC<Audition> = ({
  title,
  location,
  creationDate,
  id,
}) => {
  const { t } = useTranslation();
//   TODO: Traer pendingApplicantsCount desde el backend
    const pendingApplicantsCount = 1;

  return (
    <div className="band-post-card-container shadow-lg">
      <div className="content-div">
        <div className="postCard-div-1">
          <h2 className="postCard-h2-0">
            <b>{title}</b>
          </h2>
        </div>

        <ul>
          <li className="date-and-location">
            <div className="location">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 24 24"
                width="14"
                height="25"
                className="mr-2"
              >
                <path d="M13.987,6.108c-.039.011-7.228,2.864-7.228,2.864a2.76,2.76,0,0,0,.2,5.212l2.346.587.773,2.524A2.739,2.739,0,0,0,12.617,19h.044a2.738,2.738,0,0,0,2.532-1.786s2.693-7.165,2.7-7.2a3.2,3.2,0,0,0-3.908-3.907ZM15.97,9.467,13.322,16.51a.738.738,0,0,1-.692.49c-.1-.012-.525-.026-.675-.378l-.908-2.976a1,1,0,0,0-.713-.679l-2.818-.7a.762.762,0,0,1-.027-1.433l7.06-2.8a1.149,1.149,0,0,1,1.094.32A1.19,1.19,0,0,1,15.97,9.467ZM12,0A12,12,0,1,0,24,12,12.013,12.013,0,0,0,12,0Zm0,22A10,10,0,1,1,22,12,10.011,10.011,0,0,1,12,22Z" />
              </svg>
              {location}
            </div>
            <p className="postCard-p-0">
              <b>
                <time className="timeago" dateTime={creationDate.toString()}></time>
              </b>
            </p>
          </li>
        </ul>

        <div className="band-post-card-btns">
          <div className="row-btns">
            <a
              className="audition-applicants-btn hover: shadow-sm"
              href={`/auditions/${id}/applicants`}
            >
              <button className="audition-btn" type="submit">
                {/* {pendingApplicantsCount > 0 && (
                  <span className="icon-button__badge">
                    {pendingApplicantsCount}
                  </span>
                )} */}
                &nbsp;
                {t("BandAuditions.applicants")}
                <img
                  src={UserIcon}
                  className="audition-icon invert"
                  alt="Applicants"
                />
              </button>
            </a>

            <a
              href={`/auditions/${id}`}
              className="auditions-more-btn hover: shadow-sm"
            >
              <button className="audition-btn" type="button">
                {t("BandAuditions.more")}
                <img src={InfoIcon} className="audition-icon" alt="More info" />
              </button>
            </a>
          </div>
        </div>
      </div>
    </div>
  );
};

export default BandAuditionCard;
