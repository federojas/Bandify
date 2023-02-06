import React, { useContext, useEffect, useRef, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import dayjs from 'dayjs';
import { Audition, User } from "../../models";
import "../../styles/welcome.css";
import "../../styles/postCard.css";
import "../../styles/audition.css";
import "../../styles/forms.css";
import "../../styles/modals.css";
import "../../styles/alerts.css";
import {
  Avatar,
  Box,
  Button,
  Card,
  CardBody,
  CardHeader,
  Center,
  Flex,
  FormControl,
  FormLabel,
  Heading,
  HStack,
  Input,
  Modal,
  ModalBody,
  ModalCloseButton,
  ModalContent,
  ModalFooter,
  ModalHeader,
  ModalOverlay,
  Text, Textarea, useDisclosure, useToast,
  VStack,
} from "@chakra-ui/react";

import { BsInfoCircle } from "react-icons/bs";
import { ImLocation } from "react-icons/im";
import { BiBullseye } from "react-icons/bi";
import { FiCalendar } from "react-icons/fi";
import RoleTag from "../../components/Tags/RoleTag";
import { FiMusic, FiShare2, FiUsers } from "react-icons/fi";
import { AiOutlineEdit, AiOutlineDelete } from "react-icons/ai";
import { useTranslation } from "react-i18next";
import GenreTag from "../../components/Tags/GenreTag";
import { serviceCall } from "../../services/ServiceManager";
import { useUserService } from "../../contexts/UserService";
import { useAuditionService } from "../../contexts/AuditionService";
import AuthContext from "../../contexts/AuthContext";
import swal from 'sweetalert';
import ApplyButton from "./ApplyAudition";


const AuditionActions = (props: { auditionId: number, isOwner: boolean, currentUser: User | undefined }) => {
  const isBand = props.currentUser?.band;
  const { t } = useTranslation();
  const navigate = useNavigate();
  const toast = useToast();
  const share = () => {
    // TODO: Add code to share the audition
  };
  const auditionService = useAuditionService();

  const onDelete = () => {
    swal({
      title: t("Audition.delete"),
      text: t("Audition.deleteConfirm"),
      icon: "warning",
      buttons: {
        cancel: t("Button.cancel"),
        willDelete: t("Button.confirm")
      },
      dangerMode: true,
    })
      .then((willDelete) => {
        if (willDelete) {
          serviceCall(auditionService.deleteAuditionById(props.auditionId), navigate, () => { }).then((response) => {
            if (response.hasFailed()) {
              toast({
                title: t("Register.error"),
                status: "error",
                description: t("Audition.deleteError"),
                isClosable: true,
              })
            } else {
              toast({
                title: t("Register.success"),
                status: "success",
                description: t("Audition.deleteSuccess"),
                isClosable: true,
              })
              navigate("/auditions");
            }
          });
        }
      });

  }

  const onEdit = () => {
    navigate("/auditions/" + props.auditionId + "/edit");
  }



  return (
    <VStack>
      <Button leftIcon={<FiShare2 />} w={'44'} colorScheme='blue' onClick={() => {
        navigator.clipboard.writeText(window.location.href);
        toast({
          title: t("Register.success"),
          status: "success",
          description: t("Clipboard.message"),
          isClosable: true,
        })
      }}>

        {t("Audition.share")}
      </Button>
      {props.isOwner ?
        <>
          <Button leftIcon={<FiUsers />} w={'44'} colorScheme='green'>{t("Audition.applicants")}</Button>

          <Button leftIcon={<AiOutlineEdit />} w={'44'} colorScheme='teal' onClick={onEdit}>{t("Audition.edit")}</Button>
          <Button leftIcon={<AiOutlineDelete />} w={'44'} colorScheme='red' onClick={onDelete}>{t("Audition.delete")}</Button>

        </>
        :
        <>
          {(props.isOwner || isBand) ? <></> : (<ApplyButton auditionId={props.auditionId} />)}
        </>
      }
    </VStack>
  );
};

const AuditionCard = ({
  user,
  audition,
  userImg,
}: {
  user: User;
  audition: Audition;
  userImg: string;
}) => {
  const date = dayjs(audition.creationDate).format('DD/MM/YYYY')
  const { userId } = useContext(AuthContext);
  const navigate = useNavigate()

  return (
    <Card
      maxW={"3xl"}
      w={"2xl"}
      margin={5}
      p={5}
      boxShadow={"xl"}
      rounded={"xl"}
    >
      <CardHeader>
        <Flex
          as="a"
          cursor="pointer"
          onClick={() => {
            navigate(userId === audition.ownerId ? "/profile" : "/users/" + audition.ownerId)
          }}
          flex="1"
          gap="4"
          alignItems="center"
          className="ellipsis-overflow"
        >
          <Avatar
            src={userImg} //TODO: ALT
          />
          <Heading size={"lg"} noOfLines={1}>{user.name}</Heading>
        </Flex>
      </CardHeader>
      <CardBody>
        <Flex justifyContent="space-between">
          <VStack spacing={8} alignItems={"start"}>
            <Box maxW={'xl'}>
              <Heading size={"lg"}>{audition.title}</Heading></Box>
            <HStack spacing={4}>
              <FiCalendar />
              <HStack wrap={'wrap'}>
                <Text>{date}</Text>
              </HStack>
            </HStack>
            <HStack spacing={4}>
                <BsInfoCircle style={{ width: "100% !important" }} />
              <Box maxW={'xl'}>
                <Text fontSize={"lg"}>{audition.description}</Text>
              </Box>
            </HStack>
            <HStack spacing={4}>
              <ImLocation />
              <Text fontSize={"lg"}>{audition.location}</Text>
            </HStack>
            <HStack spacing={4}>
              <BiBullseye />
              <HStack wrap={'wrap'}>
                {audition.lookingFor.map((item, index) => (
                  <RoleTag role={item} key={index} />
                ))}
              </HStack>
            </HStack>
            <HStack spacing={4}>
              <FiMusic />
              <HStack wrap={"wrap"}>
                {audition.musicGenres.map((item, index) => (
                  <GenreTag genre={item} key={index} />
                ))}
              </HStack>
            </HStack>
          </VStack>
        </Flex>
      </CardBody>

      {/* TODO: Agregar el formulario para artistas */}
    </Card>
  );
};

const AuditionView = () => {
  const params = useParams()
  const navigate = useNavigate();
  const [audition, setAudition] = React.useState<Audition>();
  const [isLoading, setIsLoading] = useState(true);
  const [userImg, setUserImg] = useState<string | undefined>(undefined)
  const userService = useUserService();
  const auditionService = useAuditionService();
  const [ownerUser, setOwnerUser] = React.useState<User>();
  const [currentUser, setCurrentUser] = React.useState<User>();
  const [isOwner, setIsOwner] = useState(false);
  const { userId } = useContext(AuthContext);
  useEffect(() => {
    serviceCall(
      auditionService.getAuditionById(parseInt(params.id as string)),
      navigate,
      (response) => {
        if (response) {
          setAudition(response)
        }
      },
    );
  }, [params.id, navigate]);


  useEffect(() => {
    if (audition) {
      serviceCall(
        userService.getProfileImageByUserId(audition.ownerId),
        navigate,
        (response) => {
          setUserImg(URL.createObjectURL(response))
        },
      );
      if (userId) {
        serviceCall(
          userService.getUserById(userId),
          navigate,
          (response) => {
            setCurrentUser(response)
          }
        );
      }
      serviceCall(
        userService.getUserById(audition.ownerId),
        navigate,
        (response) => {
          setOwnerUser(response)
          setIsLoading(false);
        },
      );

    }
  }, [audition, navigate, userId]
  )

  useEffect(() => {
    if (currentUser && audition) {
      setIsOwner(currentUser?.id === audition.ownerId ? true : false);
    }
  }, [audition, currentUser])

  useEffect(() => {
    if (ownerUser) {
      setIsLoading(false);
    }
  }, [ownerUser])

  return (
    <Center>
      <HStack minH={"80vh"}>
        {isLoading ? <span className="loader"></span> :
          (<>
            <AuditionCard user={ownerUser!} audition={audition!} userImg={userImg!} />
            <AuditionActions auditionId={audition!.id} isOwner={isOwner} currentUser={currentUser} />
          </>)}
      </HStack>
    </Center>
  );
};

export default AuditionView;
