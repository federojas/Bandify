import React, { useContext, useEffect, useState } from "react";
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
  Image,
  Box,
  Button,
  Card,
  CardBody,
  CardHeader,
  Center,
  Flex,
  Heading,
  HStack,
  Text, useToast,
  VStack,
  Modal,
  ModalCloseButton,
  ModalContent,
  ModalFooter,
  ModalHeader,
  ModalOverlay,
  useDisclosure,
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
import ApplyButton from "./ApplyAudition";
import { Helmet } from "react-helmet";
import { WarningTwoIcon } from '@chakra-ui/icons';
import { TiTick, TiCancel } from "react-icons/ti";
import { useMembershipService } from "../../contexts/MembershipService";

function ClosedAudition() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  return (
    <Box textAlign="center" py={10} px={6}>
      <WarningTwoIcon boxSize={'50px'} color={'orange.300'} />
      <Heading as="h2" size="xl" mt={6} mb={2}>
        {t("Audition.Closed")}
      </Heading>
      <Text color={'gray.500'} fontSize={'lg'}>
        {t("Audition.CheckOutOthers")}
      </Text>
      <Button colorScheme="blue" mt={6} onClick={() => {
        navigate('/auditions')
      }}>
        {t("Audition.MoreAuditions")}
      </Button>
    </Box>
  );
}

function DeleteAuditionModal(props: { auditionId: number }) {
  const { isOpen, onOpen, onClose } = useDisclosure();
  const { t } = useTranslation();
  const auditionService = useAuditionService();
  const navigate = useNavigate();
  const toast = useToast();
  const onDelete = () => {
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
          title: t("Audition.deleteSuccess"),
          status: "success",
          isClosable: true,
        })
        navigate('/auditions');
      }
    });
  }
  return (
    <>
      <Button leftIcon={<AiOutlineDelete />} w={'44'} colorScheme='red' onClick={onOpen}>{t("Audition.delete")}</Button>
      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>{t("Audition.deleteConfirm")}</ModalHeader>
          <ModalCloseButton />
          <ModalFooter>
            <Button leftIcon={<TiTick />} colorScheme='blue' mr={3} onClick={onDelete}>
              {t("Button.confirm")}
            </Button>
            <Button onClick={onClose} leftIcon={<TiCancel />} colorScheme='red' >{t("Button.cancel")}</Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  )
}

const AuditionActions = (props: { auditionId: number, isOwner: boolean, currentUser: User | undefined, bandId: number }) => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const [hasApplied, setHasApplied] = useState(true);
  const [isMember, setIsMember] = useState(true);
  const [isBand, setIsBand] = useState(true);
  const toast = useToast();
  const userService = useUserService();
  const membershipService = useMembershipService();
  const [refresh, setRefresh] = useState(true);

  const handleRefresh = () => {
    setRefresh(!refresh);
  }

  useEffect(() => {
    if (props.currentUser && !props.currentUser.band) {
      serviceCall(
        userService.getUserAuditionApplications(props.auditionId, props.currentUser.id),
        navigate,
        (response) => {
          console.log('De vuelta, refresheo ', refresh)
          console.log('Respuesta: ', response.getContent().length)
          if (response.getContent().length === 0) {
            setHasApplied(false);
          } else {
            setHasApplied(true);
          }
        },
      )
      serviceCall(
        membershipService.getUserMembershipsByBand(props.currentUser.id, props.bandId),
        navigate,
        (response) => {
          if (response.getContent().length === 0) {
            setIsMember(false);
          }
        },
      )
      setIsBand(props.currentUser.band);
    }
  }, [navigate, isBand, isMember, hasApplied, props.currentUser, refresh]);



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
          <Button onClick={() => { navigate('/auditions/' + String(props.auditionId) + '/applicants') }} leftIcon={<FiUsers />} w={'44'} colorScheme='green'>{t("Audition.applicants")}</Button>
          <Button leftIcon={<AiOutlineEdit />} w={'44'} colorScheme='teal' onClick={onEdit}>{t("Audition.edit")}</Button>
          <DeleteAuditionModal auditionId={props.auditionId} />

        </>
        :
        <>
          {(isBand || hasApplied || isMember) ? <></> : (<ApplyButton auditionId={props.auditionId} refresh={handleRefresh} />)}
        </>
      }
    </VStack>
  );
};

const AuditionCard = ({
  user,
  audition,
}: {
  user: User;
  audition: Audition;
}) => {
  const date = dayjs(audition.creationDate).format('DD/MM/YYYY');
  const { userId } = useContext(AuthContext);
  const navigate = useNavigate();
  const { t } = useTranslation();

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
            navigate(userId === user.id ? "/profile" : "/users/" + user.id)
          }}
          flex="1"
          gap="4"
          alignItems="center"
          className="ellipsis-overflow"
        >
          <Image
            src={user.profileImage}
            alt={t("Alts.profilePicture")}
            borderRadius="full"
            boxSize="70px"
            objectFit={'cover'}
            shadow="lg"
            border="5px solid"
            borderColor="gray.800"
            _dark={{
              borderColor: "gray.200",
              backgroundColor: "white"
            }}
          />
          <Heading size={"md"} noOfLines={1}>{user.name}</Heading>
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
    </Card>
  );
};

const AuditionView = () => {
  const params = useParams()
  const navigate = useNavigate();
  const [audition, setAudition] = React.useState<Audition>();
  const [isLoading, setIsLoading] = useState(true);
  const userService = useUserService();
  const auditionService = useAuditionService();
  const [ownerUser, setOwnerUser] = React.useState<User>();
  const [currentUser, setCurrentUser] = React.useState<User>();
  const [isOwner, setIsOwner] = useState(false);
  const { userId } = useContext(AuthContext);
  const { t } = useTranslation();
  const [closed, setClosed] = useState(false);

  useEffect(() => {
    serviceCall(
      auditionService.getAuditionById(parseInt(params.id as string)),
      navigate,
      (response) => {
        if (response) {
          setAudition(response)
        }
      },
    ).then(r => {
      if (r.hasFailed() && r.getError().status === 410) {
        setClosed(true);
        setIsLoading(false);
      }
    });
  }, [params.id, navigate]);


  useEffect(() => {
    if (audition) {
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
        userService.getUserByUrl(audition.owner),
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
      setIsOwner(currentUser?.id == ownerUser?.id ? true : false);
    }
  }, [audition, currentUser, ownerUser])

  useEffect(() => {
    if (ownerUser) {
      setIsLoading(false);
    }
  }, [ownerUser])

  return (
    <>
      <Helmet>
        <title>{t("Audition.title")}</title>
      </Helmet>
      <Center>
        <HStack minH={"80vh"}>
          {isLoading ? (<span className="loader"></span>) :
            (closed ? <ClosedAudition /> : (<>
              <AuditionCard user={ownerUser!} audition={audition!} />
              <AuditionActions auditionId={audition!.id} isOwner={isOwner} currentUser={currentUser} bandId={parseInt(audition!.owner.split('/')[audition!.owner.split('/').length - 1])} />
            </>))
          }
        </HStack>
      </Center>
    </>
  );
};

export default AuditionView;
