import React, { useContext, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import dayjs from 'dayjs';
import {Audition, User} from "../../models";
import "../../styles/welcome.css";
import "../../styles/postCard.css";
import "../../styles/audition.css";
import "../../styles/forms.css";
import "../../styles/modals.css";
import "../../styles/alerts.css";
import {
    Avatar,
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
    ModalOverlay,
    ModalContent,
    ModalHeader,
    ModalFooter,
    ModalBody,
    ModalCloseButton,
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
import {useAuditionService} from "../../contexts/AuditionService";
import AuthContext from "../../contexts/AuthContext";

const AuditionActions = (props: { auditionId: number, isOwner:boolean, currentUser:User|undefined}) => {
  const isBand = props.currentUser?.band;
  const { t } = useTranslation();
  const auditionService = useAuditionService();
  const toast = useToast();
  const navigate = useNavigate();

function DeleteModal() {
    const { isOpen, onClose, onOpen} = useDisclosure();

    return (
        <>
            <Button leftIcon={<AiOutlineDelete/>} w={'44'} colorScheme='red' onClick={onOpen}>
                {t("Audition.delete")}
            </Button>
            <Modal isOpen={isOpen} onClose={onClose}>
                <ModalOverlay />
                <ModalContent>
                    <ModalHeader>{t("Audition.deleteConfirm")}</ModalHeader>
                    <ModalCloseButton />
                    <ModalFooter>
                        <Button colorScheme='blue' mr={3} onClick={onClose}>
                            {t("Modal.close")}
                        </Button>
                        <Button variant='ghost' onClick={handleDelete}>{t("Modal.confirm")}</Button>
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </>
    )
}

  function handleDelete() {
    //TODO analizar uso de deleteResponse
    let deleteRespone = auditionService
        .deleteAuditionById(props.auditionId)
        .then(() => {
            toast({
                title: t("Register.success"),
                status: "success",
                description: t("Audition.deleteSuccess"),
                isClosable: true,
            });
        })
        .catch(() =>
            toast({
                title: t("Register.error"),
                status: "error",
                description: t("Audition.deleteError"),
                isClosable: true,
            })
        )
      navigate(-1);
  }


  return (
    <VStack>
      <Button leftIcon={<FiShare2/>} w={'44'} colorScheme='blue'>
        <button
            onClick={() =>
            {
                navigator.clipboard.writeText( window.location.href );
                toast({
                    title: t("Register.success"),
                    status: "success",
                    description: t("Clipboard.message"),
                    isClosable: true,
                });
            }

        }>
          {t("Audition.share")}
        </button>
      </Button>
      {props.isOwner ?
       <>
        <Button leftIcon={<FiUsers/>} w={'44'} colorScheme='green'>{t("Audition.applicants")}</Button>
        <Button leftIcon={<AiOutlineEdit/>} w={'44'} colorScheme='teal'>{t("Audition.edit")}</Button>
        <DeleteModal/>
        </>
        :
        <>
        {isBand ?  <></> : <Button leftIcon={<FiUsers/>} w={'44'} colorScheme='green'>{t("Audition.apply")}</Button>}
        {/*AGREGAR !hasApplied */}
        </>
      }
    </VStack>
  );
};



const AuditionCard = ({
  user,
  audition,
  userImg
}: {
  user: User;
  audition: Audition;
  userImg: string;
}) => {
  const date =dayjs(audition.creationDate).format('DD/MM/YYYY')
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
          href={`/user/`}//todo: cambiar por el id de la banda
          flex="1"
          gap="4"
          alignItems="center"
          flexWrap="wrap"
        >
          <Avatar
            src={`data:image/png;base64,${userImg}`}
          />
          <Heading size={"lg"}>{user.name}</Heading> 
        </Flex>
      </CardHeader>
      <CardBody>
        <VStack spacing={8} alignItems={"start"}>
          <Heading size={"lg"}>{audition.title}</Heading>
          <HStack spacing={4}>
            <FiCalendar />
            <HStack wrap={'wrap'}>
              <Text>{date}</Text>
            </HStack>
          </HStack>
          <HStack spacing={4}>
            <BsInfoCircle />
            <Text fontSize={"lg"}>{audition.description}</Text>
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
                <GenreTag genre={item} key={index}/>
              ))}
            </HStack>
          </HStack>
        </VStack>
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
  const {userId} = useContext(AuthContext);
  useEffect(() => {
    serviceCall(
      auditionService.getAuditionById(parseInt(params.id as string)),
      navigate,
      (response) => {  
        if(response) {
          setAudition(response)
        }
      },
    );
  }, [params.id, navigate]);


  useEffect(() => {
    if(audition) {
          serviceCall(
              userService.getProfileImageByUserId(audition.ownerId),
              navigate,
              (response) => {
                setUserImg(response)
              },
          );
          if(userId){
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
                setIsOwner(currentUser?.id === audition.ownerId ? true : false);
                setIsLoading(false);
              },
          );
  
        }
      }, [audition, isOwner, navigate, userId, currentUser]
  )

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
