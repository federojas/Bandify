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
import { Audition } from "../../models";
import { FiCalendar } from "react-icons/fi";
import dayjs from 'dayjs';
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
import { BiBullseye } from "react-icons/bi";
import { FiMusic } from "react-icons/fi";
import { Link, useLocation, useNavigate } from "react-router-dom";
import React, { useEffect, useState } from "react";
import { serviceCall } from "../../services/ServiceManager";
import { useUserService } from "../../contexts/UserService";
const PostCard: React.FC<Audition> = ({
  title,
  ownerId,
  location,
  lookingFor,
  musicGenres,
  id,
  creationDate,
}) => {
  const { t } = useTranslation();
  const userService = useUserService();
  const navigate = useNavigate();
  const [userName, setUsername] = useState("")
  const [userId, setUserId] = useState(0)
  const date = dayjs(creationDate).format('DD/MM/YYYY')
  const [userImg, setUserImg] = useState<string | undefined>(undefined);
  const urlLocation = useLocation();

  useEffect(() => {
    serviceCall(
      userService.getUserById(ownerId),
      navigate,
      (response) => {
        setUsername(response.name);
        setUserId(response.id);
      }
    )
  },[])

  useEffect(() => {
    if(ownerId) {
      serviceCall(
          userService.getProfileImageByUserId(ownerId),
          navigate,
          (response) => {
            setUserImg(
                response
            )
          },
      )
    }
  }, [ownerId, navigate])

  return (
    <Card maxW="md" margin={5} boxShadow={"2xl"} w={"2xl"}>
      <CardHeader>
        <Flex
          as="a"
          href={"/users/"+userId.toString()}
          flex="1"
          gap="4"
          alignItems="center"
          flexWrap="wrap"
        >
          <Avatar
              src={`data:image/png;base64,${userImg}`} //TODO: revisar posible mejora a link
          />
          <Box>
            <Heading size="sm">{userName}</Heading> {/*TODO: poner text overflow*/}
            <Text fontSize="smaller">{location}</Text>
          </Box>
        </Flex>
      </CardHeader>
      <CardBody>
        <Stack spacing="3">
          <Heading size="md">{title}</Heading>
          <HStack spacing={4}>
            <FiCalendar />
            <HStack wrap={'wrap'}>
              <Text>{date}</Text>
            </HStack>
          </HStack>
          <HStack spacing={4}>
            <BiBullseye />
            <HStack spacing="2" wrap={"wrap"}>
              {lookingFor.map((role) => (
                <RoleTag role={role} />
              ))}
            </HStack>
          </HStack>
          <HStack spacing={4}>
            <FiMusic />
            <HStack spacing="2" wrap={"wrap"}>
              {musicGenres.map((genre) => (
                <GenreTag genre={genre} />
              ))}
            </HStack>
          </HStack>
        </Stack>
      </CardBody>
      <Divider />
      <CardFooter>
        <ButtonGroup>
          <Link to={"/auditions/"+id.toString()}>
            <Button variant="solid" colorScheme="blue">
              {t("PostCard.more")}
            </Button> 
          </Link>
              <Button variant="ghost" colorScheme="blue">
                <button
                    onClick={() => {navigator.clipboard.writeText( window.location.href + "/" + id.toString())}}>
                {t("PostCard.share")}
                </button>
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
