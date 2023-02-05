import { Accordion, Avatar, Box, Button, Flex, HStack, Modal, ModalBody, ModalCloseButton, ModalContent, ModalFooter, ModalHeader, ModalOverlay, Text, useDisclosure, VStack } from "@chakra-ui/react"
import { useTranslation } from "react-i18next"
import SidenavLayout from "./SidenavLayout"
import {TiTick, TiCancel} from 'react-icons/ti'

function InviteInfo() {
  const { isOpen, onOpen, onClose } = useDisclosure()
  const {t} = useTranslation();

  return (
    <>
      <Button onClick={onOpen}>{t("Invites.MoreInfo")}</Button>

      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>{t("Invites.ModalTitle")}</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <Text>{t("Invites.Subtitle")} {' '}BAND{' '} {t("Invites.Subtitle2")}</Text>
            <Text>{t("Invites.Subtitle3")}</Text>
            <Text as='i'>DESCRIPTION</Text>
            <Text>{t("Invites.Subtitle4")}</Text>
            
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

const InviteItem = () => {
  return (
    <Box borderWidth='1px' borderRadius='lg' p="4" w={'full'}>
      <Flex alignItems={'center'} justify="space-between">
        <HStack>
          <Avatar src='https://bit.ly/sage-adebayo' />
          <Box ml='3'>
            <Text fontWeight='bold'>
              Segun Adebayo

            </Text>
          </Box>
        </HStack>
        <InviteInfo />

      </Flex>
    </Box>
  )
}

const InvitesList = () => {
  return (<VStack width={'full'}>
    <InviteItem />
    <InviteItem />
    <InviteItem />


  </VStack>)
}

const Invites = () => {
  const { t } = useTranslation();

  return (
    <SidenavLayout>
      <Text fontSize='2xl' fontWeight='bold' mb='4'>{t("Invites.Title")}</Text>
      <InvitesList />
    </SidenavLayout>
  )
}

export default Invites