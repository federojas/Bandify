import {
  PostCardMainContainer,
  PostCardProfile,
  PostCardBandName,
  PostCardAuditionTitle,
  PostCardRolesSpan,
  PostCardGenresSpan,
  PostCardButtonContainer,
  PostCardButton,
  PostCardLocation,
  Location,
  RolesContainer,
  LoopDiv,
  GenresContainer,
} from "./styles";

//i18 translations
import { useTranslation } from "react-i18next";
import "../../common/i18n/index";
import { Audition } from "../../types";
import {
  Avatar,
  Box,
  Button,
  ButtonGroup,
  Card,
  CardBody,
  CardFooter,
  CardHeader,
  Divider,
  Flex,
  Heading,
  HStack,
  IconButton,
  Image,
  Stack,
  Tag,
  Text,
} from "@chakra-ui/react";
import GenreTag from "../Tags/GenreTag";
import RoleTag from "../Tags/RoleTag";
const PostCard: React.FC<Audition> = ({
  title,
  band,
  location,
  roles,
  genres,
}) => {
  const { t } = useTranslation();

  return (
    <Card maxW="md" margin={5} boxShadow={'2xl'}>
      <CardHeader>
        <Flex as='a' href={'#'} flex="1" gap="4" alignItems="center" flexWrap="wrap">
          <Avatar
            name={band.name}
            src="https://i.pinimg.com/originals/d3/e2/73/d3e273980e1e3df14c4a9b26e7d98d70.jpg"
          />

          <Box>
            <Heading size="sm">{band.name}</Heading>
            <Text fontSize="smaller">{location}</Text>
          </Box>
        </Flex>
      </CardHeader>
      <CardBody>
        <Stack spacing="3">
          <Heading size="md">{title}</Heading>
          <HStack spacing="2" wrap={'wrap'}>
            {roles.map((role) => (
              <RoleTag role={role} />
            ))}
          </HStack>
          <HStack spacing="2" wrap={'wrap'}>
            {genres.map((genre) => (
              <GenreTag genre={genre} />
            ))}
          </HStack>
        </Stack>
      </CardBody>
      <Divider />
      <CardFooter>
        <ButtonGroup>
          <Button variant="solid" colorScheme="blue">
            More
          </Button>
          <Button variant="ghost" colorScheme="blue">
            Share
          </Button>
        </ButtonGroup>
      </CardFooter>
    </Card>
    // <PostCardMainContainer>
    //   {/* TODO: a href */}
    //   <a style={{ textDecoration: "none" }} href="#">
    //     <PostCardProfile>
    //       <img
    //         style={{
    //           width: "50px",
    //           height: "50px",
    //           objectFit: "cover",
    //           borderRadius: "1000px",
    //           border: "2px solid black",
    //         }}
    //         alt={t("Profile.alt.img")}
    //         src="https://cdn-1.motorsport.com/images/amp/6O1P1km2/s1000/jos-verstappen-1.jpg"
    //       ></img>
    //       <PostCardBandName> {band.name} </PostCardBandName>
    //     </PostCardProfile>
    //   </a>

    //   {/* --------------- postcard title -------------  */}
    //   <div style={{ marginLeft: "20px" }}>
    //     <div style={{ marginBottom: "1rem", overflow: "hidden !important" }}>
    //       <PostCardAuditionTitle>
    //         <b>{title}</b>
    //       </PostCardAuditionTitle>
    //     </div>
    //     {/* --------------------------------------------  */}

    //     {/* ---------- Location roles and genres ------- */}
    //     <ul>
    //       {/* LOCATION */}
    //       <PostCardLocation>
    //         <Location>
    //           <svg
    //             xmlns="http://www.w3.org/2000/svg"
    //             viewBox="0 0 24 24"
    //             width="14"
    //             height="25"
    //           >
    //             <path d="M13.987,6.108c-.039.011-7.228,2.864-7.228,2.864a2.76,2.76,0,0,0,.2,5.212l2.346.587.773,2.524A2.739,2.739,0,0,0,12.617,19h.044a2.738,2.738,0,0,0,2.532-1.786s2.693-7.165,2.7-7.2a3.2,3.2,0,0,0-3.908-3.907ZM15.97,9.467,13.322,16.51a.738.738,0,0,1-.692.49c-.1-.012-.525-.026-.675-.378l-.908-2.976a1,1,0,0,0-.713-.679l-2.818-.7a.762.762,0,0,1-.027-1.433l7.06-2.8a1.149,1.149,0,0,1,1.094.32A1.19,1.19,0,0,1,15.97,9.467ZM12,0A12,12,0,1,0,24,12,12.013,12.013,0,0,0,12,0Zm0,22A10,10,0,1,1,22,12,10.011,10.011,0,0,1,12,22Z" />
    //           </svg>
    //           <div style={{ marginLeft: "0.5rem" }}>{location}</div>
    //         </Location>
    //       </PostCardLocation>
    //       {/* TODO: TIMEAGO FUNCTION */}
    //       {/* ROLES */}
    //       <RolesContainer>
    //         <LoopDiv>
    //           {/* TODO: curl con los searchlinks */}

    //           <a style={{ textDecoration: "none" }} href="#">
    //             {roles.map((role) => (
    //               <PostCardRolesSpan>{role}</PostCardRolesSpan>
    //             ))}
    //           </a>
    //         </LoopDiv>
    //       </RolesContainer>
    //       {/* GENRES */}
    //       <GenresContainer>
    //         {/* TODO: curl con los searchlinks */}
    //         <a style={{ textDecoration: "none" }} href="#">
    //           {genres.map((genre) => (
    //             <PostCardGenresSpan>{genre}</PostCardGenresSpan>
    //           ))}
    //         </a>
    //       </GenresContainer>
    //     </ul>
    //     {/* --------------------------------------------  */}
    //     <PostCardButtonContainer>
    //       <a style={{ textDecoration: "none" }} href="#">
    //         <PostCardButton type="button">
    //           {t("PostCard.postCardButton")}
    //         </PostCardButton>
    //       </a>
    //     </PostCardButtonContainer>
    //   </div>
    // </PostCardMainContainer>
  );
};

export default PostCard;
