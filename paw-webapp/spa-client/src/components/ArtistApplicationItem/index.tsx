import React from "react";
import { useTranslation } from "react-i18next";
import { Application } from "../../types";
import "../../styles/artistApplication.css";

const ArtistApplicationItem: React.FC<Application> = ({
  title,
  state,
  isOpen,
  id,
}) => {
  const auditionUrl = `/auditions/${id}`;
  const { t } = useTranslation();

  if (isOpen) {
    return (
      <a href={auditionUrl} className="collection-item">
        {renderStatus(state)}
        <div className="auditionName">{title}</div>
      </a>
    );
  } else {
    return (
      <div className="closed-app">
        <div className="auditionName">{title}</div>
        <div className="states">
          {renderStatus(state)}
          <span className="badge-bigger red">applicants.tabs.closed</span>
        </div>
      </div>
    );
  }
};

const renderStatus = (state: string) => {
    switch (state) {
      case 'ACCEPTED':
        return <span className="badge green">applicants.tabs.approved</span>;
      case 'REJECTED':
        return <span className="badge red">applicants.tabs.rejected</span>;
      default:
        return <span className="badge orange">applicants.tabs.pending</span>;
    }
  };
  

export default ArtistApplicationItem;
