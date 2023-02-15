import { useTranslation } from "react-i18next";
import "../../common/i18n/index";
import { Audition } from "../../models";
import { FiCalendar, FiShare2 } from "react-icons/fi";
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
import { Link, useNavigate } from "react-router-dom";
import React, { useContext, useEffect, useState } from "react";
import { serviceCall } from "../../services/ServiceManager";
import { useUserService } from "../../contexts/UserService";
import AuthContext from "../../contexts/AuthContext";
import { AiOutlineInfoCircle } from "react-icons/ai";
import CopyToClipboard from 'react-copy-to-clipboard';


const PostCard: React.FC<Audition> = ({
  title,
  owner,
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
  const [profileImage, setProfileImage] = useState("")
  const [bandId, setBandId] = useState(0)
  const date = dayjs(creationDate).format('DD/MM/YYYY')
  const toast = useToast();
  const { userId } = useContext(AuthContext);

  useEffect(() => {
    serviceCall(
      userService.getUserByUrl(owner),
      navigate,
      (response) => {
        setUsername(response.name);
        setProfileImage(response.profileImage)
        setBandId(response.id);
      }
    )
  }, [])

  return (
    <Card maxW="md" margin={5} boxShadow={"2xl"} w={"2xl"}>
      <CardHeader>
        <Flex
          as="a"
          cursor="pointer"
          onClick={() => {
            navigate(userId === bandId ? "/profile" : "/user/" + bandId.toString())
          }}
          flex="1"
          gap="4"
          alignItems="center"
          justifyContent={"start"}
        >
          <Avatar
            src={profileImage}
            _dark={{
              backgroundColor: "white",
            }}
          />
          <Box noOfLines={3}>
            <Heading size="sm">{userName}</Heading>
            <Text fontSize="smaller">{location}</Text>
          </Box>
        </Flex>
      </CardHeader>
      <CardBody>
        <Stack spacing="3">
          <Heading size="md" noOfLines={2}>{title}</Heading>
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
          <Link to={"/audition/" + id.toString()}>
            <Button variant="solid" colorScheme="blue" leftIcon={<AiOutlineInfoCircle />}>
              {t("PostCard.more")}
            </Button>
          </Link>

          <CopyToClipboard text={window.location.href + "/" + id.toString()} onCopy={() => {
            toast({
              title: t("Register.success"),
              status: "success",
              description: t("Clipboard.message"),
              isClosable: true,
            })
          }}>
            <Button variant="ghost" colorScheme="blue" leftIcon={<FiShare2 />}>
              {t("PostCard.share")}
            </Button>
          </CopyToClipboard>
        </ButtonGroup>
      </CardFooter>
    </Card >
  );
};

export default PostCard;
