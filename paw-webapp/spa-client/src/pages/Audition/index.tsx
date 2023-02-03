import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import BackIcon from "../../assets/icons/back.svg";
import dayjs from 'dayjs';
import { Audition } from "../../models";
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
  Text,
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
import {useAuditionService} from "../../contexts/AuditionService";


//TODO: QUE HACER CON EL USER???
type User = {
  id: number;
  name: string;
};

// type Audition = {
//   id: number;
//   title: string;
//   description: string;
//   location: string;
//   lookingFor: Array<{
//     name: string;
//   }>;
//   musicGenres: Array<{
//     name: string;
//   }>;
//   alreadyApplied: boolean;
//   canBeAddedToBand: boolean;
//   band: {
//     id: number;
//     name: string;
//   };
// };



const AuditionActions = (props: { auditionId: number }) => {
  const share = () => {
    // TODO: Add code to share the audition
  };

  const openConfirmation = () => {
    // TODO: Add code to open the confirmation modal
  };
  const { t } = useTranslation();

  return (
    <VStack>
      {/* TODO: LA FOOT NAV NO CAMBIA EL IDIOMA DE ESTAS COSAS */}
      <Button leftIcon={<FiShare2/>} w={'44'} colorScheme='blue'>{t("Audition.share")} </Button>
      <Button leftIcon={<FiUsers/>} w={'44'} colorScheme='green'>{t("Audition.applicants")}</Button>
      <Button leftIcon={<AiOutlineEdit/>} w={'44'} colorScheme='teal'>{t("Audition.edit")}</Button>
      <Button leftIcon={<AiOutlineDelete/>} w={'44'} colorScheme='red'>{t("Audition.delete")}</Button>
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
            name='pep' //todo: name
            src={`/user/[e[/profile-image`} //todo: user
          />
          {/* todo: EL OWNER ES LA URL */}
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
  const [audition, setAudition] = useState<Audition>();
  const [isLoading, setIsLoading] = useState(true);
  const [userName, setUsername] = useState("");
  const userService = useUserService();
  const auditionService = useAuditionService();
  useEffect(() => {
    serviceCall(
      auditionService.getAuditionById(parseInt(params.id as string)),
      navigate,
      (response) => {  
        if(response) setAudition(response);
        setIsLoading(false);
      },
    )
  }, []
  )

  useEffect(()=>{
    serviceCall(
      userService.getUserById(parseInt(params!.id!)),
      navigate,
      (response) => {
        setUsername(response.name);
      }
    )
  },[]);


  return (
    <Center>
      <HStack minH={"80vh"}>  
        {isLoading ? <span className="loader"></span> : <AuditionCard user={{ id: 1, name: userName }} audition={audition!} />}        
        <AuditionActions auditionId={1} />
      </HStack>
    </Center>
  );
};

export default AuditionView;
