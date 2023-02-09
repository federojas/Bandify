import { Accordion, Avatar, Box, Button, Center, Flex, HStack, Modal, ModalBody, ModalCloseButton, ModalContent, ModalFooter, ModalHeader, ModalOverlay, Tab, TabList, TabPanel, TabPanels, Tabs, Text, useDisclosure, useToast, VStack } from "@chakra-ui/react"
import { useTranslation } from "react-i18next"
import SidenavLayout from "./SidenavLayout"
import { TiTick, TiCancel } from 'react-icons/ti'
import { useContext, useEffect, useState } from "react"
import AuthContext from "../../contexts/AuthContext"
import { useMembershipService } from "../../contexts/MembershipService"
import { serviceCall } from "../../services/ServiceManager"
import { useNavigate } from "react-router-dom"
import Membership from "../../models/Membership"
import User from "../../models/User"
import { useUserService } from "../../contexts/UserService"
import { Helmet } from "react-helmet"
import RoleTag from "../../components/Tags/RoleTag"


enum inviteStatuses {
  PENDING = "PENDING",
  ACCEPTED = "ACCEPTED",
  REJECTED = "REJECTED",
}

function InviteInfo({ membership, setRefresh, setIsLoading, refresh }: { membership: Membership, setRefresh: React.Dispatch<React.SetStateAction<boolean>>,setIsLoading: React.Dispatch<React.SetStateAction<boolean>>, refresh: boolean  }) {
  const { isOpen, onOpen, onClose } = useDisclosure()
  const { t } = useTranslation();
  const navigate = useNavigate()
  const membershipService = useMembershipService();
  const toast = useToast()

  const handleAccept = () => {
    serviceCall(membershipService.accept(membership, "ACCEPTED"), navigate)
      .then((response) => {
        if (!response.hasFailed()) {
          toast({
            title: t("Invites.Accepted"),
            description: t("Invites.AcceptedDescription"),
            status: "success",
            duration: 9000,
            isClosable: true,
          })
        } else {
          toast({
            title: t("Invites.Error"),
            description: t("Invites.ErrorDescription"),
            status: "error",
            duration: 9000,
            isClosable: true,
          })
        }
        setRefresh(!refresh);
        setIsLoading(true);
        onClose()
      })
  }

  function handleReject () {
    serviceCall(membershipService.reject(membership, "REJECTED"), navigate)
      .then((response) => {
        if (!response.hasFailed()) {
          toast({
            title: t("Invites.Rejected"),
            description: t("Invites.RejectedDescription"),
            status: "success",
            duration: 9000,
            isClosable: true,
          })
        } else {
          toast({
            title: t("Invites.Error"),
            description: t("Invites.ErrorDescription"),
            status: "error",
            duration: 9000,
            isClosable: true,
          })
        }
        setRefresh(!refresh);
        setIsLoading(true);
        onClose()
      })
  }

  return (
    <>
      <Button onClick={onOpen}>{t("Invites.MoreInfo")}</Button>

      <Modal isOpen={isOpen} onClose={onClose}>
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>{t("Invites.ModalTitle")}</ModalHeader>
          <ModalCloseButton />
          <ModalBody>
            <Text>{t("Invites.Subtitle")} {' '}{membership.band.name}{' '} {t("Invites.Subtitle2")}</Text>
            <Text>{t("Invites.Subtitle3")}</Text>
            <Text as='i'>
              {membership.description}
            </Text>
            <Text>{t("Invites.Subtitle4")}</Text>
            <HStack>
              {membership.roles.map((role) => {
                return <RoleTag key={role} size='sm' role={role} />
              }
              )}
            </HStack>
          </ModalBody>

          <ModalFooter>
            <Button leftIcon={<TiTick />} colorScheme='blue' mr={3} onClick={handleAccept}>
              {t("Invites.Accept")}
            </Button>
            <Button leftIcon={<TiCancel />} colorScheme='red' onClick={handleReject} >{t("Invites.Reject")}</Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  )
}

function InviteItem ({ membership, setRefresh , setIsLoading, refresh}: { membership: Membership, setRefresh:React.Dispatch<React.SetStateAction<boolean>>,setIsLoading: React.Dispatch<React.SetStateAction<boolean>>, refresh:boolean })  {

  return (
    <Box borderWidth='1px' borderRadius='lg' p="4" w={'full'}>
      <Flex alignItems={'center'} justify="space-between">
        <HStack>
          <Avatar src={membership.band.profileImage} //TODO: revisar ALT?
            _dark={{
              backgroundColor: "white",
            }} />
          <Box ml='3'>
            <Text fontWeight='bold'>
              {membership.band.name}
            </Text>
          </Box>
        </HStack>
        <InviteInfo membership={membership} setIsLoading={setIsLoading} setRefresh={setRefresh} refresh={refresh} />

      </Flex>
    </Box>
  )
}

function InvitesList ({ memberships, setRefresh ,setIsLoading, refresh}: { memberships: Membership[], setRefresh: React.Dispatch<React.SetStateAction<boolean>>, setIsLoading: React.Dispatch<React.SetStateAction<boolean>>, refresh:boolean }) {
  const { t } = useTranslation();
  const [membershipAux, setMembershipAux] = useState<Membership[]>(memberships);
  useEffect(() => {
    setMembershipAux(memberships)
  }, [])

  if (memberships.length === 0) return (<Text>{t("Invites.noInvites")}</Text>)
  return (<VStack width={'full'}>
    {membershipAux.map((membership) => {
      // if(membership.state === inviteStatus)

      return <InviteItem key={membership.id} setIsLoading={setIsLoading} membership={membership} setRefresh={setRefresh} refresh={refresh}  />
    })}

  </VStack>)
}

const Invites = () => {
  const { t } = useTranslation();
  const { userId } = useContext(AuthContext);
  const membershipService = useMembershipService();
  const navigate = useNavigate();
  const [memberships, setMemberships] = useState<Membership[]>([]);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [refresh, setRefresh] = useState<boolean>(false);

  useEffect(() => {
    if (!userId) return;
    serviceCall(
      membershipService.getUserMemberships({ user: userId, state: inviteStatuses.PENDING }),
      navigate,
    ).then((response) => {
      if (!response.hasFailed()) {
        setMemberships(response.getData().getContent());
      }
      setIsLoading(false);
    });
  }, [refresh]
  )
  return (
    <>
      <Helmet>
        <title>{t("Hub.Invites")}</title>
      </Helmet>
      <SidenavLayout>
        <Text fontSize='2xl' fontWeight='bold' mb='4'>{t("Invites.Title")}</Text>
        {isLoading ? <Center mt={'15%'}><span className="loader"></span></Center> :
          <>
            <InvitesList memberships={memberships} setIsLoading={setIsLoading} setRefresh={setRefresh} refresh={refresh} />
          </>}
      </SidenavLayout>
    </>
  )
}

export default Invites