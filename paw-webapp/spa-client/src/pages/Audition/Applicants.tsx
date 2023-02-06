import {
  Box, Center, Heading,
  Avatar, Badge, Flex, HStack, Tab, TabList, TabPanel, TabPanels, Tabs, Text, Button,
  Modal, ModalBody, ModalCloseButton, ModalContent, ModalFooter, ModalHeader, ModalOverlay, useDisclosure, VStack, useColorModeValue
} from "@chakra-ui/react"
import { useTranslation } from "react-i18next";
import { TiCancel, TiTick } from "react-icons/ti";

function ApplicantInfo() {
  const { isOpen, onOpen, onClose } = useDisclosure()
  const { t } = useTranslation();

  return (
    <>
      <Button onClick={onOpen}>{t("AuditionApplicants.SeeApplication")}</Button>

      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>{t("AuditionApplicants.ModalTitle")}</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <Text>{t("AuditionApplicants.Subtitle")}<Text as='b'>ARTIST</Text></Text>
            <Text>{t("AuditionApplicants.Subtitle2")}<Text as='i'>DESCRIPTION</Text></Text>
            

          </ModalBody>

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

const ApplicantItem = ({ type = 'PENDING' }: { type: string }) => {
  const { t } = useTranslation();
  const scheme = type === "REJECTED" ? "red" : (type === "PENDING" ? undefined : "green")
  const label = type === "REJECTED" ? t("Applications.Rejected") : (type === "PENDING" ? t("Applications.Pending") : t("Applications.Accepted"))
  const isPending = type === "PENDING"

  return (
    <Box borderWidth='1px' borderRadius='lg' p="4">
      <Flex alignItems={'center'} justify="space-between">
        <HStack>
          <Avatar src='https://bit.ly/sage-adebayo' />
          <Box ml='3'>
            <Text fontWeight='bold'>
              Segun Adebayo
            </Text>
          </Box>
        </HStack>
        {isPending ? <ApplicantInfo /> :
          <Badge ml='1' colorScheme={scheme}>
            {label}
          </Badge>
          }
      </Flex>

    </Box>
  )
}

const AuditionApplicants = () => {
  const { t } = useTranslation();
  const pendingApplicants = [];
  const acceptedApplicants = [];
  const rejectedApplicants = [];

  return (
    <Center py={'10'}>
      <VStack spacing={'4'}>
        <Heading>{t("AuditionApplicants.Title")}</Heading>
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
                {pendingApplicants.length > 0 ?
                  <p>{t("AuditionApplicants.NoApplicants")}</p>
                  :
                  <ApplicantItem type={'PENDING'} />
                }
              </TabPanel>
              <TabPanel mt="4">
                {acceptedApplicants.length > 0 ?
                  <p>{t("AuditionApplicants.NoApplicants")}</p>
                  :
                  <ApplicantItem type={'ACCEPTED'} />
                }

              </TabPanel>
              <TabPanel mt="4">
                {rejectedApplicants.length > 0 ?
                  <ApplicantItem type={'REJECTED'} />
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