import { Avatar, Box, Button, Flex, HStack, IconButton, Modal, ModalBody, ModalCloseButton, ModalContent, ModalFooter, ModalHeader, ModalOverlay, Text, useDisclosure, useToast, VStack } from "@chakra-ui/react";
import { useTranslation } from "react-i18next";
import { TiTick, TiCancel } from "react-icons/ti";
import { useNavigate } from "react-router-dom";
import Membership from "../../api/types/Membership";
import RoleTag from "../../components/Tags/RoleTag";
import { useMembershipService } from "../../contexts/MembershipService";
import { User } from "../../models";
import { serviceCall } from "../../services/ServiceManager";


function LeaveBand({ membershipId }: { membershipId: number }) {
  const { t } = useTranslation();
  const { isOpen, onOpen, onClose } = useDisclosure();
  const membershipService = useMembershipService();
  const toast = useToast();
  const navigate = useNavigate();


  const handleAccept = (membershipId: number) => {

    serviceCall(
      membershipService.kickMember(membershipId),
      navigate
    ).then((response) => {
      if (!response.hasFailed()) {
        toast({
          title: t("Profile.kickModal.success"),
          description: t("Profile.kickModal.successDescription"),
          status: "success",
          duration: 9000,
          isClosable: true,
        });
      } else {
        toast({
          title: t("Profile.kickModal.error"),
          description: t("Profile.kickModal.errorDescription"),
          status: "error",
          duration: 9000,
          isClosable: true,
        });
      }
      onClose()
    });

  }

  return (
    <>
      <Button colorScheme='red' onClick={onOpen}>{t("Profile.leaveBand")}</Button>
      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>{t("Profile.leaveModal.title")}</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <Text>{t("Profile.leaveModal.message")}</Text>
          </ModalBody>

          <ModalFooter>
            <Button leftIcon={<TiTick />} colorScheme='blue' mr={3} onClick={() => handleAccept(membershipId)}>
              {t("Profile.leaveModal.confirm")}
            </Button>
            <Button leftIcon={<TiCancel />} colorScheme='red' onClick={onClose} >{t("Profile.leaveModal.cancel")}</Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  )
}

function KickFromBand({ membershipId }: { membershipId: number }) {
  const { t } = useTranslation();
  const { isOpen, onOpen, onClose } = useDisclosure();
  const membershipService = useMembershipService();
  const toast = useToast();
  const navigate = useNavigate();

  const handleAccept = (membershipId: number) => {

    serviceCall(
      membershipService.kickMember(membershipId),
      navigate
    ).then((response) => {
      if (!response.hasFailed()) {
        toast({
          title: t("Profile.kickModal.success"),
          description: t("Profile.kickModal.successDescription"),
          status: "success",
          duration: 9000,
          isClosable: true,
        });
      } else {
        toast({
          title: t("Profile.kickModal.error"),
          description: t("Profile.kickModal.errorDescription"),
          status: "error",
          duration: 9000,
          isClosable: true,
        });
      }
      onClose()

    });

  }


  return (
    <>
      <Button onClick={onOpen} colorScheme='red'>{t("Profile.kickFromBand")}</Button>
      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>{t("Profile.kickModal.title")}</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <Text>{t("Profile.kickModal.message")}</Text>
          </ModalBody>

          <ModalFooter>
            <Button leftIcon={<TiTick />} colorScheme='blue' mr={3} onClick={() => handleAccept(membershipId)}>
              {t("Profile.kickModal.confirm")}
            </Button>
            <Button leftIcon={<TiCancel />} colorScheme='red' onClick={onClose} >{t("Profile.kickModal.cancel")}</Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  )
}

const MembershipItem = ({ contraUser, description, roles, isOwner, membershipId }: { contraUser: User, description: string, roles: string[], isOwner: boolean, membershipId: number }) => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  return (
    <Box borderWidth='1px' borderRadius='lg' p="4" w={'full'}>
      <Flex direction={'column'} justify="space-between">
        <VStack justify='space-between'>
          <HStack onClick={() => {
            navigate('/users/' + contraUser.id)
          }}
            cursor={'pointer'}
          >
            <Avatar src={contraUser.profileImage} //TODO: revisar ALT?
              _dark={{
                backgroundColor: "white",
              }} />
            <Box ml='3'>
              <Text fontWeight='bold'>
                {contraUser.name}
                {
                  contraUser.surname && ` ${contraUser.surname}`
                }
              </Text>
            </Box>
          </HStack>
          <Box>
            {isOwner ?
              (contraUser.band ?
                <LeaveBand membershipId={membershipId as any} /> :
                <KickFromBand membershipId={membershipId} />)
              : <></>}
          </Box>
        </VStack>
        <Flex direction={'column'} justify={'space-between'} alignItems={'center'}>
          <HStack>
            {roles.map((role) => {
              return (
                <RoleTag key={role} role={role} size="md" />
              )
            })}
          </HStack>
          <Text as='i'>{description}</Text>
        </Flex>
      </Flex>
    </Box>
  )
}

export default MembershipItem