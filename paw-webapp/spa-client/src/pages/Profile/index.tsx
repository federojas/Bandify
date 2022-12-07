import React from "react";

type Props = {
  user: {
    id: number;
    name: string;
    surname?: string;
    email: string;
    available?: boolean;
  };
//   location?: {
//     name: string;
//   };
};

const Profile: React.FC<Props> = ({ user }) => {
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
                  {/* edit-div */}
                </div>
              </div>
              {/* Right Side */}
              <div className="right-side">
                {/* Profile-right-info */}
                <div className="profile-right-info">
                  {/* Profile-right-info-top */}
                  <div className="profile-right-info-top">
                    {/* Genres */}
                    <div className="genres">
                      {/* Genres Title */}
                      <div className="title">
                        <h1>{/* profile.genres */}</h1>
                      </div>
                      {/* Genres List */}
                      <div className="genres-list">
                        {/* Genres List Item */}

                        <div className="genre-list-item">
                          {/* Genre Item */}
                          <div className="genre-item">
                            {/* Genre Name */}
                            <h1>{/* genre.name */}</h1>
                          </div>
                        </div>
                      </div>
                    </div>
                    {/* Social Links */}
                    <div className="social-links">
                      {/* Social Links Title */}
                      <div className="title">
                        <h1>{/* profile.social */}</h1>
                      </div>
                      {/* Social Links List */}
                      <div className="social-links-list">
                        {/* Social Link */}
                        <div className="social-link">
                          {/* Social Link Icon */}
                          <div className="social-link-icon">
                            <img src="" alt="" />
                          </div>
                          {/* Social Link Link */}
                          <div className="social-link-link">
                            <a href="">{/* profile.link */}</a>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  {/* Profile-right-info-bottom */}
                  <div className="profile-right-info-bottom">
                    {/* Profile-right-info-bottom Title */}
                    <div className="title">
                      <h1>{/* profile.bio */}</h1>
                    </div>
                    {/* Profile-right-info-bottom Description */}
                    <div className="description">{/* user.bio */}</div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </main>
  );
};

export default Profile;
