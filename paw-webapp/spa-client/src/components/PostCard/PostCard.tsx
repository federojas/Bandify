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
  Stack,
  Text, useToast,
} from "@chakra-ui/react";
import GenreTag from "../Tags/GenreTag";
import RoleTag from "../Tags/RoleTag";
import { BiBullseye } from "react-icons/bi";
import { FiMusic } from "react-icons/fi";
import { Link, useLocation, useNavigate } from "react-router-dom";
import React, {useContext, useEffect, useState} from "react";
import { serviceCall } from "../../services/ServiceManager";
import { useUserService } from "../../contexts/UserService";
import AuthContext from "../../contexts/AuthContext";
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
  const [bandId, setBandId] = useState(0)
  const date = dayjs(creationDate).format('DD/MM/YYYY')
  const [userImg, setUserImg] = useState<string | undefined>(undefined);
  const toast = useToast();
  const { userId } = useContext(AuthContext);

  useEffect(() => {
    serviceCall(
      userService.getUserById(ownerId),
      navigate,
      (response) => {
        setUsername(response.name);
        setBandId(response.id);
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
          href={userId  === ownerId ? "/profile" : "/users/"+bandId.toString()}
          flex="1"
          gap="4"
          alignItems="center"
          justifyContent={"start"}
          className="ellipsis-overflow"
        >
          <Avatar
              src={`data:image/png;base64,${userImg}`} //TODO: revisar posible mejora a link y ALT
          />
          <Box >
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
                    onClick={() => {
                      navigator.clipboard.writeText( window.location.href + "/" + id.toString())
                      toast({
                        title: t("Register.success"),
                        status: "success",
                        description: t("Clipboard.message"),
                        isClosable: true,
                      });
                    }

                }>
                {t("PostCard.share")}
                </button>
              </Button>
        </ButtonGroup>
      </CardFooter>
    </Card>
  );
};

export default PostCard;
