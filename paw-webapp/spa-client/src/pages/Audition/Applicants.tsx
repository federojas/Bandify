import {
  Box, Center, Heading,
  Avatar, Badge, Flex, HStack, Tab, TabList, TabPanel, TabPanels, Tabs, Text, Button,
  Modal, ModalBody, ModalCloseButton, ModalContent, ModalFooter, ModalHeader, ModalOverlay, useDisclosure, VStack, useColorModeValue
} from "@chakra-ui/react"
import { useEffect, useState } from "react";
import { Helmet } from "react-helmet";
import { useTranslation } from "react-i18next";
import { TiCancel, TiTick } from "react-icons/ti";
import { useNavigate, useParams } from "react-router-dom";
import { useAuditionService } from "../../contexts/AuditionService";
import { Application, Audition } from "../../models";
import { serviceCall } from "../../services/ServiceManager";
import AddToBandButton from "../User/AddToBandButton";

function ApplicantInfo({application} : {application: Application}) {
  const { isOpen, onOpen, onClose } = useDisclosure()
  const { t } = useTranslation();

  return (
    <>
      <Helmet>
        <title>{t("InviteItem.applicantsAlt")}</title>
      </Helmet>
      <Button onClick={onOpen}>{t("AuditionApplicants.SeeApplication")}</Button>

      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>{t("AuditionApplicants.ModalTitle")}</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <Text>{t("AuditionApplicants.Subtitle")}<Text as='b'>{application.applicant}</Text></Text>
            <Text mt="4">{t("AuditionApplicants.Subtitle2")}</Text>
            <Text as='i'>{application.message}</Text>

          </ModalBody>

{/* TODO: Agregar funcionalidad de aceptar y rechazar aplicacion a audition */}
          <ModalFooter>
            <Button leftIcon={<TiTick />} colorScheme='blue' mr={3} onClick={onClose}>
              {t("Invites.Accept")}
            </Button>
            <Button leftIcon={<TiCancel />} colorScheme='red'>{t("Invites.Reject")}</Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  )
}

const ApplicantItem = ({ type = 'PENDING', application }: { type: string, application: Application }) => {
  const { t } = useTranslation();
  const scheme = type === "REJECTED" ? "red" : (type === "PENDING" ? undefined : "green")
  const label = type === "REJECTED" ? t("Applications.Rejected") : (type === "PENDING" ? t("Applications.Pending") : t("Applications.Accepted"))
  const isPending = type === "PENDING"
  const isAccepted = type === "ACCEPTED"

  return (
    <Box borderWidth='1px' borderRadius='lg' p="4">
      <Flex alignItems={'center'} justify="space-between">
        <HStack>
          <Avatar src='https://bit.ly/sage-adebayo' />
          <Box ml='3'>
            <Text fontWeight='bold'>
              {application.applicant}
            </Text>
          </Box>
        </HStack>
        {isPending ? <ApplicantInfo application={application} /> :
          <Badge ml='1' colorScheme={scheme}>
            {label}
          </Badge>
        }
        {/* { isAccepted && 
          <AddToBandButton user={{}}/>
        } */}
      </Flex>

    </Box>
  )
}

const AuditionApplicants = () => {
  const { t } = useTranslation();
  const { id } = useParams();

  const [audition, setAudition] = useState<Audition>();
  const [pending, setPending] = useState<Application[]>([]);
  const [accepted, setAccepted] = useState<Application[]>([]);
  const [rejected, setRejected] = useState<Application[]>([]);

  const navigate = useNavigate();
  const auditionService = useAuditionService();

  useEffect(() => {

    serviceCall(
      auditionService.getAuditionById(Number(id)),
      navigate,
      (audition) => {
        console.log(audition)
        setAudition(audition)
      }
    )

    serviceCall(
      auditionService.getAuditionApplications(Number(id), 1, 'PENDING'),
      navigate,
      (applications) => {
        console.log(applications)
        setPending(applications)
      }
    )

    serviceCall(
      auditionService.getAuditionApplications(Number(id), 1, 'SELECTED'),
      navigate,
      (applications) => {
        console.log(applications)
        setAccepted(applications)
      }
    )

    serviceCall(
      auditionService.getAuditionApplications(Number(id), 1, 'REJECTED'),
      navigate,
      (applications) => {
        console.log(applications)
        setRejected(applications)
      }
    )

  }, [navigate, auditionService, id])

  return (
    <Center py={'10'}>
      <VStack spacing={'4'}>
        <Heading>{t("AuditionApplicants.Title")}{audition?.title}</Heading>
        <Box bg={useColorModeValue("white", "gray.900")} px={4}
          py={4} shadow={'md'} rounded={'xl'} w={'xl'}
        >
          <Tabs variant='soft-rounded' colorScheme='blue'>
            <TabList>
              <Tab>{t("Applications.Pending")}</Tab>
              <Tab>{t("Applications.Accepted")}</Tab>
              <Tab>{t("Applications.Rejected")}</Tab>
            </TabList>
            <TabPanels>
              <TabPanel mt="4">
                {pending.length > 0 ?
                  pending.map((application) => <ApplicantItem type={'PENDING'} application={application} />)
                  :
                  <p>{t("AuditionApplicants.NoApplicants")}</p>
                }
              </TabPanel>
              <TabPanel mt="4">
                {accepted.length > 0 ?
                  accepted.map((application) => <ApplicantItem type={'ACCEPTED'} application={application} />)
                  :
                  <p>{t("AuditionApplicants.NoApplicants")}</p>
                }

              </TabPanel>
              <TabPanel mt="4">
                {rejected.length > 0 ?
                  rejected.map((application) => <ApplicantItem type={'REJECTED'} application={application} />)
                  :
                  <p>{t("AuditionApplicants.NoApplicants")}</p>
                }

              </TabPanel>
            </TabPanels>
          </Tabs>
        </Box>
      </VStack>
    </Center>
  )
}

export default AuditionApplicants