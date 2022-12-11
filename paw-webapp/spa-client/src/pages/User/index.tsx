import React from "react";
import "../../styles/profile.css";
import UserIcon from '../../assets/icons/user.svg'
import EditIcon from '../../assets/icons/edit-white-icon.svg'
import AvailableCover from '../../assets/images/available.png'
import { useTranslation } from "react-i18next";

type Props = {
  user: {
    id: number;
    name: string;
    surname?: string;
    email: string;
    available?: boolean;
    description?: string;
    band: boolean;
    location: string;
  };
  //   location?: {
  //     name: string;
  //   };
};

type Genre = {
  name: string;
  // other properties of a Genre object
};

type Role = {
  name: string;
  // other properties of a Role object
};

type SocialMedia = {
  name: string;
  url: string;
};

const User: React.FC<Props> = ({ user }) => {
  const { t } = useTranslation();

//   TODO: Get userId by params
  const userId = 1;

  const preferredGenres: Genre[] = [];
  const roles: Role[] = [];
  const socialMedia: SocialMedia[] = [];

  return (
    <main>
      <div className="bg-gray-100">
        <div className="main-box">
          <div className="user-info-div">
            <div className="md:flex no-wrap justify-center md:-mx-2 ">
              {/* Left Side */}
              <div className="left-side">
                {/* ProfileCard */}
                <div className="profile-card">
                  {/* Image */}
                  <div className="image overflow-hidden">
                    <div className="profile-image-container">
                      {/* TODO path a imagen */}
                      <img
                        className="profileImage"
                        src={'https://i.pinimg.com/originals/d3/e2/73/d3e273980e1e3df14c4a9b26e7d98d70.jpg'}
                        alt="Profile"
                      />
                      {user.available && (
                        <>
                          <img
                            className="top-image-big"
                            src={AvailableCover}
                            alt="Available"
                          />
                        </>
                      )}
                    </div>
                  </div>
                  {/* Info */}
                  <div className="profile-left-info">
                    <h1 className="full-name">
                      {user.name}
                      {user.surname && <>{user.surname}</>}
                    </h1>
                    {/* TODO location? */}
                    <div className="location">
                      {user.location ? (
                        <p>{user.location}</p>
                      ) : (
                        <p> {t("Profile.emptyLocation")} </p>
                      )}
                    </div>
                    {user.band ? (
                      <span className="account-type-label-band">{t("Profile.band")}</span>
                    ) : (
                      <span className="account-type-label-artist">{t("Profile.artist")}</span>
                    )
                    }
                  </div>
                  
                </div>
              </div>
              {/* Right Side */}
              <div className="w-full md:w-6/12 mx-2 h-64">
                {/* About */}
                <div className="user-data">
                  <div className="about-section-heading">
                    <img
                      src={UserIcon}
                      className="user-icon"
                      alt="User"
                    />
                    &nbsp;&nbsp;&nbsp;&nbsp;
                    <span>About</span>
                  </div>
                  <div>
                    {user.description == null ? (
                      user.band ? (
                        <p>This band has not provided a biography.</p>
                      ) : (
                        <p>This user has not provided a biography.</p>
                      )
                    ) : user.description.length === 0 ? (
                      user.band ? (
                        <p>This band has not provided a biography.</p>
                      ) : (
                        <p>This user has not provided a biography.</p>
                      )
                    ) : (
                      <p className="description">{user.description}</p>
                    )}
                  </div>
                </div>
                {/* Preferred genres */}
                <div className="user-data">
                  <div className="about-section-heading">
                    <span>
                      {user.band ? (
                        <p>Profile Band Genres</p>
                      ) : (
                        <p>Profile User Genres</p>
                      )}
                    </span>
                  </div>
                  <div className="genres-div">
                    {preferredGenres.length === 0 ? (
                      user.band ? (
                        <p>Profile Band No Genres</p>
                      ) : (
                        <p>Profile Artist No Genres</p>
                      )
                    ) : (
                      <>
                        {preferredGenres.map((genre) => (
                          <span className="genre-span">{genre.name}</span>
                        ))}
                      </>
                    )}
                  </div>
                </div>
                {/* Roles */}
                <div className="user-data">
                  <div className="about-section-heading">
                    <span>
                      {user.band ? (
                        <p>Profile Band Roles</p>
                      ) : (
                        <p>Profile User Roles</p>
                      )}
                    </span>
                  </div>
                  <div className="roles-div">
                    {roles.length === 0 ? (
                      user.band ? (
                        <p>Profile Band No Roles</p>
                      ) : (
                        <p>Profile Artist No Roles</p>
                      )
                    ) : (
                      <>
                        {roles.map((role) => (
                          <span className="roles-span">{role.name}</span>
                        ))}
                      </>
                    )}
                  </div>
                </div>
                {/* Social networks */}
                <div className="user-data">
                  <div className="about-section-heading">
                    <span>
                      <p>{t("Profile.socialMedia")}</p>
                    </span>
                  </div>
                  <div className="roles-div">
                    {socialMedia.length === 0 && (
                      <p>{/* no social media message here */}</p>
                    )}
                  </div>
                  <div className="social-media-container">
                    {socialMedia.map((social, index) => (
                      <a key={index} href={social.url}>
                        <img
                          className="social-media-icons"
                          alt={social.name}
                          src={`/resources/images/${social.name}.png`}
                        />
                      </a>
                    ))}
                  </div>
                </div>
              </div>
            </div>
          </div>
          {/* TODO parte de abajo (plays in, members) */}
          <div className="md:flex no-wrap justify-center md:-mx-2 mt-24">
            {user.band ? (
              <div className="member-data"></div>
            ) : (
              <div className="play-data"></div>
            )}
          </div>
        </div>
      </div>
    </main>
  );
};

export default User;
