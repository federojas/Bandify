import React from "react";
import "../../styles/profile.css";

type Props = {
  user: {
    id: number;
    name: string;
    surname?: string;
    email: string;
    available?: boolean;
    description?: string;
    band: boolean;
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

const Profile: React.FC<Props> = ({ user }) => {
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
                      {/* TODO  alt={img} */}
                      <img
                        className="profileImage"
                        src={`/user/${user.id}/profile-image`}
                      />
                      {user.available && (
                        <>
                          {/* TODO alt={available} */}
                          <img
                            className="top-image-big"
                            src="/resources/images/available.png"
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
                    {/* <div className="location">
                      {location ? (
                        <p>{location.name}</p>
                      ) : (
                        <p> profile.emptyLocation </p>
                      )}
                    </div> */}
                    {/* account-type-label-artist and account-type-label-band */}
                    <h1 className="email">{user.email}</h1>
                  </div>
                  {/* Edit button */}
                  <div className="edit-div">
                    <a href="/profile/editArtist">
                      <button className="edit-btn hover: shadow-sm">
                        {/* profile.edit.alt */}
                        <img
                          src="/resources/icons/edit-white-icon.svg"
                          alt={"TODO alt"}
                          className="icon-img"
                        />
                        Edit profile TR
                        {/* profile.editProfile */}
                      </button>
                    </a>
                  </div>
                  {/* TODO: En JSP habian dos copias, una para /editArtist y otra para /editBand */}
                  {/* TODO hasRole('BAND') */}
                  <div className="auditions-div">
                    <ul>
                      <li className="pt-2">
                        <a href="/profile/auditions">
                          <button className="auditions-btn hover: shadow-sm">
                            Auditions TR
                          </button>
                        </a>
                      </li>
                    </ul>
                  </div>
                  {/* TODO hasRole('ARTIST') */}
                  <div className="auditions-div">
                    <ul className="button-ul">
                      <li className="pt-2">
                        <a href="/profile/applications">
                          <button className="auditions-btn hover: shadow-sm">
                            My Applications
                          </button>
                        </a>
                      </li>
                      <li className="pt-2">
                        <a href="/invites">
                          <button className="auditions-btn hover: shadow-sm">
                            Invites
                          </button>
                        </a>
                      </li>
                    </ul>
                  </div>
                </div>
              </div>
              {/* Right Side */}
              <div className="w-full md:w-6/12 mx-2 h-64">
                {/* About */}
                <div className="user-data">
                  <div className="about-section-heading">
                    <img
                      src="/resources/icons/user.svg"
                      className="user-icon"
                      alt={"TODO ALT"}
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
                      <p>TR social media message here </p>
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

export default Profile;
