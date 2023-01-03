import React from "react";
import { useNavigate } from "react-router-dom";
import BackIcon from "../../assets/icons/back.svg";
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
import RoleTag from "../../components/Tags/RoleTag";
import { FiMusic, FiShare2, FiUsers } from "react-icons/fi";
import { AiOutlineEdit, AiOutlineDelete } from "react-icons/ai";

import GenreTag from "../../components/Tags/GenreTag";

type User = {
  id: number;
  name: string;
};

type Audition = {
  id: number;
  title: string;
  description: string;
  location: string;
  lookingFor: Array<{
    name: string;
  }>;
  musicGenres: Array<{
    name: string;
  }>;
  alreadyApplied: boolean;
  canBeAddedToBand: boolean;
  band: {
    id: number;
    name: string;
  };
};

const AuditionTest = {
  band: {
    name: "My Band",
    id: 1,
  },
  id: 1,
  creationDate: new Date(),
  title: "My Band is looking for a drummer and a singer for a rock band",
  description: "We are looking for a drummer and a singer for a rock band in Buenos Aires city.",
  lookingFor: [
    { name: "Drummer" },
    { name: "Guitarist" },
    { name: "Bassist" },
    { name: "Singer" },
  ],
  musicGenres: [{ name: "Rock" }],
  location: "Buenos Aires",
  alreadyApplied: false,
  canBeAddedToBand: false,
};

const AuditionActions = (props: { auditionId: number }) => {
  const share = () => {
    // TODO: Add code to share the audition
  };

  const openConfirmation = () => {
    // TODO: Add code to open the confirmation modal
  };

  return (
    <VStack>
      <Button leftIcon={<FiShare2/>} w={'44'} colorScheme='blue'>Share</Button>
      <Button leftIcon={<FiUsers/>} w={'44'} colorScheme='green'>Applicants</Button>
      <Button leftIcon={<AiOutlineEdit/>} w={'44'} colorScheme='teal'>Edit</Button>
      <Button leftIcon={<AiOutlineDelete/>} w={'44'} colorScheme='red'>Delete</Button>
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
          href={`/user/${audition.band.id}`}
          flex="1"
          gap="4"
          alignItems="center"
          flexWrap="wrap"
        >
          <Avatar
            name={audition.band.name}
            src={`/user/${user.id}/profile-image`}
          />
          <Heading size={"lg"}>{audition.band.name}</Heading>
        </Flex>
      </CardHeader>
      <CardBody>
        <VStack spacing={8} alignItems={"start"}>
          <Heading size={"lg"}>{audition.title}</Heading>
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
                <RoleTag role={item.name} key={index} />
              ))}
            </HStack>
          </HStack>
          <HStack spacing={4}>
            <FiMusic />
            <HStack wrap={"wrap"}>
              {audition.musicGenres.map((item, index) => (
                <GenreTag genre={item.name} key={index}/>
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
  return (
    <Center>
      <HStack minH={"80vh"}>
        <AuditionCard user={{ id: 1, name: "Test" }} audition={AuditionTest} />
        <AuditionActions auditionId={1} />
      </HStack>
    </Center>
  );
};

export default AuditionView;
