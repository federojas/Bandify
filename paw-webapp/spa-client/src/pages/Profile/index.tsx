// import React from 'react';
export {}
// const Profile = ({ user, location }) => {
//   return (
//     <main>
//       <div className="bg-gray-100">
//         <div className="main-box">
//           <div className="user-info-div">
//             <div className="md:flex no-wrap justify-center md:-mx-2">
//               {/* Left Side */}
//               <div className="left-side">
//                 {/* ProfileCard */}
//                 <div className="profile-card">
//                   {/* Image */}
//                   <div className="image overflow-hidden">
//                     <div className="profile-image-container">
//                       <img
//                         className="profileImage"
//                         src={`/user/${user.id}/profile-image`}
//                         alt="Profile"
//                       />
//                       {user.available && (
//                         <img
//                           className="top-image-big"
//                           src="/resources/images/available.png"
//                           alt="Available"
//                         />
//                       )}
//                     </div>
//                   </div>
//                   {/* Info */}
//                   <div className="profile-left-info">
//                     <h1 className="full-name">
//                       {user.name}
//                       {user.surname && (
//                         <span className="account-type-label-artist">
//                           Artist
//                         </span>
//                       )}
//                       {!user.surname && (
//                         <span className="account-type-label-band">
//                           Band
//                         </span>
//                       )}
//                     </h1>
//                     <div className="location">
//                       {location ? (
//                         <p>{location.name}</p>
//                       ) : (
//                         <p>Location not specified</p>
//                       )}
//                     </div>
//                     <h1 className="email">{user.email}</h1>
//                   </div>
//                   {/* Edit button */}
//                   {user.surname && (
//                     <div className="edit-div">
//                       <a href="/profile/editArtist">
//                         <button className="edit-btn hover: shadow-sm">
//                           <img
//                             src="/resources/icons/edit-white-icon.svg"
//                             alt="Edit"
//                             className="icon-img"
//                           />
//                           Edit Profile
//                         </button>
//                       </a>
//                     </div>
//                   )}
//                   {!user.surname && (
//                     <div className="edit-div">
//                       <a href="/profile/editBand">
//                         <button className="edit-btn hover: shadow-sm">
//                           <img
//                             src="/resources/icons/edit-white-icon.svg"
//                             alt="Edit"
//                             className="icon-img"
//                           />
//                           Edit Profile
//                         </button>
//                       </a>
//                     </div>
//                   )}
//                 </div>
//               </div>
//             </div>
//           </div>
//         </div>
//       </div>
//     </main>
//   );
// };

// export default Profile;