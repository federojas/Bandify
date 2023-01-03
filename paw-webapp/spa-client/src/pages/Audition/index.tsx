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
import { FiMusic } from "react-icons/fi";
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
  title: "My Band is looking for a drummer",
  description: "We are looking for a drummer",
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
    <div className="right-panel">
      <div className="buttonry">
        <a className="audition-applicants-btn hover: shadow-sm">
          <button className="audition-btn" onClick={share}>
            Share
            <img
              src="/resources/icons/copy.svg"
              className="audition-icon invert"
              alt="Share"
            />
          </button>
        </a>
        {/* TODO: add isOwner && */}
        {
          <>
            <a
              className="audition-applicants-btn hover: shadow-sm"
              href={`/auditions/${props.auditionId}/applicants`}
            >
              <button className="audition-btn" type="submit">
                Applicants
                <img
                  src="/resources/icons/user.svg"
                  className="audition-icon invert"
                  alt="Applicants"
                />
              </button>
            </a>
            <a
              className="audition-edit-btn hover: shadow-sm"
              href={`/profile/editAudition/${props.auditionId}`}
            >
              <button className="audition-btn" type="submit">
                Edit
                <img
                  src="/resources/icons/edit-white-icon.svg"
                  className="audition-icon"
                  alt="Edit"
                />
              </button>
            </a>
            <a className="audition-delete-btn">
              <button
                className="audition-btn"
                onClick={openConfirmation}
                type="submit"
              >
                Delete
                <img
                  src="/resources/icons/reject.svg"
                  className="audition-icon-remove invert"
                  alt="Delete"
                />
              </button>
            </a>
            {/* TODO: Add ConfirmationModal */}
            {/* <ConfirmationModal
              modalTitle="Delete Confirmation"
              isDelete={true}
              modalHeading="Delete Audition"
              confirmationQuestion="Are you sure you want to delete this audition?"
              action={`/profile/closeAudition/${props.auditionId}`}
            /> */}
          </>
        }
      </div>
    </div>
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
            <HStack wrap={"wrap"}>
              {audition.lookingFor.map((item, index) => (
                <RoleTag role={item.name} key={index} marginY={0} />
              ))}
            </HStack>
          </HStack>
          <HStack spacing={4}>
            <FiMusic />
            <HStack wrap={"wrap"}>
              {audition.musicGenres.map((item, index) => (
                <GenreTag genre={item.name} key={index} marginY={0} />
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
