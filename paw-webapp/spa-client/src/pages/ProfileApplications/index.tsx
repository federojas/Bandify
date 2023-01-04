import '../../styles/welcome.css';
import '../../styles/auditions.css';
import '../../styles/applicants.css';
import '../../styles/invites.css';
import { useTranslation } from "react-i18next";
import ManagerTabs from '../../components/ManagerTabs';
import { Application } from '../../types'
import ArtistApplicationItem from '../../components/ArtistApplicationItem';

const ProfileApplications = () => {
    const { t } = useTranslation();
    const pendingUrl = "/profile/applications?state=PENDING";
    const acceptedUrl = "/profile/applications?state=ACCEPTED";
    const rejectedUrl = "/profile/applications?state=REJECTED";

    const artistApplications: Application[] = [
        {
            title: "Application 1",
            state: "PENDING",
            isOpen: true,
            id: 1,
        },
        {
            title: "Application 2",
            state: "ACCEPTED",
            isOpen: true,
            id: 2,
        },
        {
            title: "Application 3",
            state: "REJECTED",
            isOpen: true,
            id: 3,
        },
    ];

    return (
    <div className="manager-page">
        <ManagerTabs tabItem={1} />
        <div className="manager-items-container">
      <span className="manager-items-title">
        {t("ManagerTabs.myApplications")}
      </span>

      <hr className="rounded" />

      <div className="auditions-content">
        <div className="user-data">
          <div className="user-data-tabs">
            <a href={pendingUrl} id="pending">{t("ProfileApplications.pending")}</a>
            <a href={acceptedUrl} id="accepted">{t("ProfileApplications.accepted")}</a>
            <a href={rejectedUrl} id="rejected">{t("ProfileApplications.rejected")}</a>
          </div>
          <hr className="rounded" />
          <div className="user-data-applicants">
            {artistApplications.length > 0 && (
              artistApplications.map((application) => (
                <ArtistApplicationItem
                  {...application}
                />
              ))
            )}
            {artistApplications.length === 0 && (
              <p className="no-applications">
                {t("ProfileApplications.noApplications")}
              </p>
            )}
          </div>
        </div>
      </div>

      {/* <div className="pagination-applications">
        {currentPage > 1 && (
          <a onClick={() => getPaginationURL(currentPage - 1)}>
            <img src="/resources/images/page-next.png" alt={previous} className="pagination-next rotate" />
          </a>
        )}
        <b>{`Page ${currentPage} of ${lastPage}`}</b>
        {currentPage < lastPage && (
          <a onClick={() => getPaginationURL(currentPage + 1)}>
            <img src="/resources/images/page-next.png" alt={next} className="pagination-next" />
          </a>
        )}
      </div> */}
    </div>
    </div>
    )
}

export default ProfileApplications;