import React from "react";
import { useNavigate } from "react-router-dom";
import BackIcon from "../../assets/icons/back.svg";
import "../../styles/welcome.css";
import "../../styles/postCard.css";
import "../../styles/audition.css";
import "../../styles/forms.css";
import "../../styles/modals.css";
import "../../styles/alerts.css";

type User = {
  id: number;
  name: string;
};

type Audition = {
  id: number;
  title: string;
  description: string;
  location: string;
  lookingFor: Array<{
    name: string;
  }>;
  musicGenres: Array<{
    name: string;
  }>;
  alreadyApplied: boolean;
  canBeAddedToBand: boolean;
};

const AuditionActions = (props: { auditionId: number }) => {
  const share = () => {
    // TODO: Add code to share the audition
  };

  const openConfirmation = () => {
    // TODO: Add code to open the confirmation modal
  };

  return (
    <div className="right-panel">
      <div className="buttonry">
        <a className="audition-applicants-btn hover: shadow-sm">
          <button className="audition-btn" onClick={share}>
            Share
            <img
              src="/resources/icons/copy.svg"
              className="audition-icon invert"
              alt="Share"
            />
          </button>
        </a>
        {/* TODO: add isOwner && */}
        {
          <>
            <a
              className="audition-applicants-btn hover: shadow-sm"
              href={`/auditions/${props.auditionId}/applicants`}
            >
              <button className="audition-btn" type="submit">
                Applicants
                <img
                  src="/resources/icons/user.svg"
                  className="audition-icon invert"
                  alt="Applicants"
                />
              </button>
            </a>
            <a
              className="audition-edit-btn hover: shadow-sm"
              href={`/profile/editAudition/${props.auditionId}`}
            >
              <button className="audition-btn" type="submit">
                Edit
                <img
                  src="/resources/icons/edit-white-icon.svg"
                  className="audition-icon"
                  alt="Edit"
                />
              </button>
            </a>
            <a className="audition-delete-btn">
              <button
                className="audition-btn"
                onClick={openConfirmation}
                type="submit"
              >
                Delete
                <img
                  src="/resources/icons/reject.svg"
                  className="audition-icon-remove invert"
                  alt="Delete"
                />
              </button>
            </a>
            {/* TODO: Add ConfirmationModal */}
            {/* <ConfirmationModal
              modalTitle="Delete Confirmation"
              isDelete={true}
              modalHeading="Delete Audition"
              confirmationQuestion="Are you sure you want to delete this audition?"
              action={`/profile/closeAudition/${props.auditionId}`}
            /> */}
          </>
        }
      </div>
    </div>
  );
};

function Card({ user, audition }: { user: User; audition: Audition }) {
  return (
    <div className="card-extern">
      <div className="card-content">
        <a href={`/user/${user.id}`}>
          <div className="audition-profile">
            <div className="image overflow-hidden">
              <img
                className="audition-profile-image"
                src={`/user/${user.id}/profile-image`}
                alt="Profile"
              />
            </div>
            <h1 className="audition-band-name">{user.name}</h1>
          </div>
        </a>
        <div className="card-header">
          <h3 className="title">{audition.title}</h3>
        </div>
        <div className="card-body">
          <div className="even-columns">
            <div className="card-info">
              <ul>
                <li className="info-item">
                  <b>About</b>
                  <br />
                  <p className="description">
                    &nbsp;&nbsp;{audition.description}
                  </p>
                </li>
                <li className="info-item">
                  <b>Location</b>
                  <br />
                  <div className="tag">{audition.location}</div>
                </li>
              </ul>
            </div>
          </div>
          <div className="card-info">
            <ul>
              <li className="info-item">
                <b>Desired</b>
                <br />
                <div className="tag-list">
                  {audition.lookingFor.map((item, index) => (
                    <span key={index} className="roles-span">
                      {item.name}
                    </span>
                  ))}
                </div>
              </li>
              <li className="info-item">
                <b>Genres</b>
                <br />
                <div className="tag-list">
                  {audition.musicGenres.map((item, index) => (
                    <span key={index} className="genre-span">
                      {item.name}
                    </span>
                  ))}
                </div>
              </li>
            </ul>
          </div>
        </div>
        {/* TODO: Agregar el formulario para artistas */}
      </div>
    </div>
  );
}

function LeftPanel() {
  let navigate = useNavigate();

  return (
    <div className="left-panel">
      <a
        className="back-anchor"
        onClick={() => {
          navigate(-1);
        }}
        style={{ cursor: "pointer" }}
      >
        <div className="back-div">
          <img src={BackIcon} alt="Back" className="back-icon" />
        </div>
      </a>
    </div>
  );
}

const AuditionCard = () => {
  return (
    <div className="flex flex-col">
      <div className="auditions=content">
        <LeftPanel />
        <Card
          user={{ id: 1, name: "Test" }}
          audition={{
            id: 1,
            title: "Test",
            description: "Test",
            location: "Test",
            lookingFor: [{ name: "Test" }],
            musicGenres: [{ name: "Test" }],
            alreadyApplied: false,
            canBeAddedToBand: false,
          }}
        />
        <AuditionActions auditionId={1} />
      </div>
    </div>
  );
};

export default AuditionCard;
