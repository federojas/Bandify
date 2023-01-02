import React from "react";
import "../../styles/profile.css";
import UserIcon from "../../assets/icons/user.svg";
import EditIcon from "../../assets/icons/edit-white-icon.svg";
import AvailableCover from "../../assets/images/available.png";
import { useTranslation } from "react-i18next";
import {
  Box,
  Button,
  Center,
  Container,
  Divider,
  Grid,
  GridItem,
  Heading,
  HStack,
  Image,
  Stack,
  Tag,
  Text,
  useColorModeValue,
  VStack,
} from "@chakra-ui/react";
import ArtistTag from "../../components/Tags/ArtistTag";
import BandTag from "../../components/Tags/BandTag";
import { ImLocation } from "react-icons/im";
import GenreTag from "../../components/Tags/GenreTag";
import RoleTag from "../../components/Tags/RoleTag";
import {
  FaFacebook,
  FaInstagram,
  FaTwitter,
  FaYoutube,
  FaSoundcloud,
  FaSpotify,
} from "react-icons/fa";

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
  const { t } = useTranslation();

  const favoriteGenres: Genre[] = [
    { name: "Rock" },
    { name: "Metal" },
    { name: "Pop" },
    { name: "Jazz" },
    { name: "Blues" },
    { name: "Folk" },
    { name: "Reggae" },
  ];
  const roles: Role[] = [
    { name: "Guitarrista" },
    { name: "Bajista" },
    { name: "Baterista" },
    { name: "Vocalista" },
    { name: "Teclista" },
    { name: "Pianista" },
  ];
  const socialMedia: SocialMedia[] = [];

  return (
    <Container maxW={"5xl"} px={"0"} py={8}>
      <Stack spacing={4}>
        <Box
          w={"full"}
          bg={useColorModeValue("gray.100", "gray.900")}
          rounded={"lg"}
          boxShadow={"lg"}
          p={6}
        >
          <VStack align={"center"} spacing={4}>
            <Box
              bg="#edf3f8"
              _dark={{
                bg: "#3e3e3e",
              }}
              style={{
                backgroundImage:
                  "url(https://9cover.com/images/ccovers/1465148347guitar-music-creative.jpg)",
                backgroundSize: "cover",
                backgroundPosition: "center",
                backgroundRepeat: "no-repeat",
              }}
              height="100%"
              width="100%"
              borderRadius="lg"
              p={8}
              display="flex"
              alignItems="center"
            >
              <Image
                src="https://temacalcos.com.ar/wp-content/uploads/2022/12/SCALONETA-VS-CROACIA-57.jpg"
                alt="Profile Picture"
                borderRadius="full"
                boxSize="150px"
                shadow="lg"
                border="5px solid"
                mb={-20}
                borderColor="gray.800"
                _dark={{
                  borderColor: "gray.200",
                }}
              />
            </Box>
            <Heading fontSize={"3xl"} fontWeight={500}>
              {user.name}
              {user.surname && <>{user.surname}</>}
            </Heading>
            {user.band ? <BandTag /> : <ArtistTag />}
            <Text color={"gray.500"} fontSize={"xl"}>
              {user.description}
            </Text>
            <HStack>
              <ImLocation />
              <Text color={"gray.500"}> {user.location}</Text>
            </HStack>
          </VStack>
        </Box>
        <Grid templateColumns={"repeat(5,1fr)"} gap={4}>
          <GridItem
            w={"full"}
            colSpan={2}
            bg={useColorModeValue("gray.100", "gray.900")}
            rounded={"lg"}
            boxShadow={"lg"}
            p={6}
          >
            <VStack spacing={4} justifyItems="start">
              <Heading fontSize={"2xl"} fontWeight={500}>
                {t("Profile.favoriteGenres")}
              </Heading>
              {favoriteGenres.length > 0 ? (
                <HStack wrap={"wrap"}>
                  {favoriteGenres.map((genre) => (
                    <GenreTag genre={genre.name} />
                  ))}
                </HStack>
              ) : (
                <>{t("Profile.noFavoriteGenres")}</>
              )}
            </VStack>
            <Divider marginY={6} />
            <VStack spacing={4} justifyItems="start">
              <Heading fontSize={"2xl"} fontWeight={500}>
                {t("Profile.roles")}
              </Heading>
              {roles.length > 0 ? (
                <HStack wrap={"wrap"}>
                  {roles.map((role) => (
                    <RoleTag role={role.name} />
                  ))}
                </HStack>
              ) : (
                <>{t("Profile.noRoles")}</>
              )}
            </VStack>
          </GridItem>
          <GridItem
            w={"full"}
            colSpan={3}
            bg={useColorModeValue("gray.100", "gray.900")}
            rounded={"lg"}
            boxShadow={"lg"}
            p={6}
          >
            <VStack spacing={4} justifyItems="start">
              <Heading fontSize={"2xl"} fontWeight={500}>
                {t("Profile.socialMedia")}
              </Heading>
              <HStack wrap={"wrap"}>
                <Button colorScheme={"facebook"} as="a" href={"#"}>
                  <FaFacebook />
                </Button>
                {/* Add Twitter, Instagram, Youtube, Soundcloud and Spotify */}
                <Button colorScheme={"twitter"} as="a" href={"#"}>
                  <FaTwitter />
                </Button>
                <Button colorScheme={'orange'} as="a" href={"#"}>
                  <FaInstagram />
                </Button>
                <Button colorScheme={"red"} as="a" href={"#"}>
                  <FaYoutube />
                </Button>
                <Button colorScheme={"orange"} as="a" href={"#"}>
                  <FaSoundcloud />
                </Button>
                <Button colorScheme={"yellow"} as="a" href={"#"}>
                  <FaSpotify />
                </Button>
              </HStack>
            </VStack>
            <Divider marginY={6} />
            <VStack spacing={4} justifyItems="start">
              <Heading fontSize={"2xl"} fontWeight={500}>
                {t("Profile.playsIn")}
              </Heading>
            </VStack>
          </GridItem>
        </Grid>
      </Stack>
    </Container>
    // <main>
    //   <div className="bg-gray-100">
    //     <div className="main-box">
    //       <div className="user-info-div">
    //         <div className="md:flex no-wrap justify-center md:-mx-2 ">
    //           {/* Left Side */}
    //           <div className="left-side">
    //             {/* ProfileCard */}
    //             <div className="profile-card">
    //               {/* Image */}
    //               <div className="image overflow-hidden">
    //                 <div className="profile-image-container">
    //                   {/* TODO path a imagen */}
    //                   <img
    //                     className="profileImage"
    //                     src={'https://i.pinimg.com/originals/d3/e2/73/d3e273980e1e3df14c4a9b26e7d98d70.jpg'}
    //                     alt="Profile"
    //                   />
    //                   {user.available && (
    //                     <>
    //                       <img
    //                         className="top-image-big"
    //                         src={AvailableCover}
    //                         alt="Available"
    //                       />
    //                     </>
    //                   )}
    //                 </div>
    //               </div>
    //               {/* Info */}
    //               <div className="profile-left-info">
    //                 <h1 className="full-name">
    //                   {user.name}
    //                   {user.surname && <>{user.surname}</>}
    //                 </h1>
    //                 {/* TODO location? */}
    //                 <div className="location">
    //                   {user.location ? (
    //                     <p>{user.location}</p>
    //                   ) : (
    //                     <p> {t("Profile.emptyLocation")} </p>
    //                   )}
    //                 </div>
    //                 {user.band ? (
    //                   <span className="account-type-label-band">{t("Profile.band")}</span>
    //                 ) : (
    //                   <span className="account-type-label-artist">{t("Profile.artist")}</span>
    //                 )
    //                 }
    //                 <h1 className="email">{user.email}</h1>
    //               </div>
    //               {/* Edit button */}
    //               <div className="edit-div">
    //                 <a href="/profile/editArtist">
    //                   <button className="edit-btn hover: shadow-sm">
    //                     <img
    //                       src={EditIcon}
    //                       className="icon-img"
    //                       alt="Profile"
    //                     />
    //                     {t("Profile.editProfile")}
    //                   </button>
    //                 </a>
    //               </div>
    //               {/* TODO: En JSP habian dos copias, una para /editArtist y otra para /editBand */}
    //               {/* TODO hasRole('BAND') */}
    //               <div className="auditions-div">
    //                 <ul>
    //                   <li className="pt-2">
    //                     <a href="/profile/auditions">
    //                       <button className="auditions-btn hover: shadow-sm">
    //                         {t("Profile.auditions")}
    //                       </button>
    //                     </a>
    //                   </li>
    //                 </ul>
    //               </div>
    //               {/* TODO hasRole('ARTIST') */}
    //               <div className="auditions-div">
    //                 <ul className="button-ul">
    //                   <li className="pt-2">
    //                     <a href="/profile/applications">
    //                       <button className="auditions-btn hover: shadow-sm">
    //                         {t("Profile.applications")}
    //                       </button>
    //                     </a>
    //                   </li>
    //                   <li className="pt-2">
    //                     <a href="/profile/invites">
    //                       <button className="auditions-btn hover: shadow-sm">
    //                         {t("Profile.invites")}
    //                       </button>
    //                     </a>
    //                   </li>
    //                 </ul>
    //               </div>
    //             </div>
    //           </div>
    //           {/* Right Side */}
    //           <div className="w-full md:w-6/12 mx-2 h-64">
    //             {/* About */}
    //             <div className="user-data">
    //               <div className="about-section-heading">
    //                 <img
    //                   src={UserIcon}
    //                   className="user-icon"
    //                   alt="User"
    //                 />
    //                 &nbsp;&nbsp;&nbsp;&nbsp;
    //                 <span>About</span>
    //               </div>
    //               <div>
    //                 {user.description == null ? (
    //                   user.band ? (
    //                     <p>This band has not provided a biography.</p>
    //                   ) : (
    //                     <p>This user has not provided a biography.</p>
    //                   )
    //                 ) : user.description.length === 0 ? (
    //                   user.band ? (
    //                     <p>This band has not provided a biography.</p>
    //                   ) : (
    //                     <p>This user has not provided a biography.</p>
    //                   )
    //                 ) : (
    //                   <p className="description">{user.description}</p>
    //                 )}
    //               </div>
    //             </div>
    //             {/* Preferred genres */}
    //             <div className="user-data">
    //               <div className="about-section-heading">
    //                 <span>
    //                   {user.band ? (
    //                     <p>Profile Band Genres</p>
    //                   ) : (
    //                     <p>Profile User Genres</p>
    //                   )}
    //                 </span>
    //               </div>
    //               <div className="genres-div">
    //                 {favoriteGenres.length === 0 ? (
    //                   user.band ? (
    //                     <p>Profile Band No Genres</p>
    //                   ) : (
    //                     <p>Profile Artist No Genres</p>
    //                   )
    //                 ) : (
    //                   <>
    //                     {favoriteGenres.map((genre) => (
    //                       <span className="genre-span">{genre.name}</span>
    //                     ))}
    //                   </>
    //                 )}
    //               </div>
    //             </div>
    //             {/* Roles */}
    //             <div className="user-data">
    //               <div className="about-section-heading">
    //                 <span>
    //                   {user.band ? (
    //                     <p>Profile Band Roles</p>
    //                   ) : (
    //                     <p>Profile User Roles</p>
    //                   )}
    //                 </span>
    //               </div>
    //               <div className="roles-div">
    //                 {roles.length === 0 ? (
    //                   user.band ? (
    //                     <p>Profile Band No Roles</p>
    //                   ) : (
    //                     <p>Profile Artist No Roles</p>
    //                   )
    //                 ) : (
    //                   <>
    //                     {roles.map((role) => (
    //                       <span className="roles-span">{role.name}</span>
    //                     ))}
    //                   </>
    //                 )}
    //               </div>
    //             </div>
    //             {/* Social networks */}
    //             <div className="user-data">
    //               <div className="about-section-heading">
    //                 <span>
    //                   <p>{t("Profile.socialMedia")}</p>
    //                 </span>
    //               </div>
    //               <div className="roles-div">
    //                 {socialMedia.length === 0 && (
    //                   <p>{/* no social media message here */}</p>
    //                 )}
    //               </div>
    //               <div className="social-media-container">
    //                 {socialMedia.map((social, index) => (
    //                   <a key={index} href={social.url}>
    //                     <img
    //                       className="social-media-icons"
    //                       alt={social.name}
    //                       src={`/resources/images/${social.name}.png`}
    //                     />
    //                   </a>
    //                 ))}
    //               </div>
    //             </div>
    //           </div>
    //         </div>
    //       </div>
    //       {/* TODO parte de abajo (plays in, members) */}
    //       <div className="md:flex no-wrap justify-center md:-mx-2 mt-24">
    //         {user.band ? (
    //           <div className="member-data"></div>
    //         ) : (
    //           <div className="play-data"></div>
    //         )}
    //       </div>
    //     </div>
    //   </div>
    // </main>
  );
};

export default Profile;
