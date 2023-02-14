import { Avatar, Box, Button, Center, Flex, HStack, Modal, ModalBody, ModalCloseButton, ModalContent, ModalFooter, ModalHeader, ModalOverlay, Text, useDisclosure, useToast, VStack } from "@chakra-ui/react"
import { useTranslation } from "react-i18next"
import SidenavLayout from "./SidenavLayout"
import { TiTick, TiCancel } from 'react-icons/ti'
import React, { useContext, useEffect, useState } from "react"
import AuthContext from "../../contexts/AuthContext"
import { useMembershipService } from "../../contexts/MembershipService"
import { serviceCall } from "../../services/ServiceManager"
import {useNavigate, Link, useLocation} from "react-router-dom"
import Membership from "../../models/Membership"
import { Helmet } from "react-helmet"
import RoleTag from "../../components/Tags/RoleTag"
import {getQueryOrDefault, useQuery} from "../../hooks/useQuery";
import {PaginationWrapper} from "../../components/Pagination/pagination";
import {ChevronLeftIcon, ChevronRightIcon} from "@chakra-ui/icons";


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
  const toast = useToast();

  const handleAccept = () => {
    serviceCall(membershipService.accept(membership), navigate)
      .then((response) => {
        if (!response.hasFailed()) {
          toast({
            title: t("Invites.Accepted"),
            description: t("Invites.AcceptedDescription"),
            status: "success",
            duration: 9000,
            isClosable: true,
          })
          setRefresh(!refresh);
          setIsLoading(true);
          onClose();
        } else {
          toast({
            title: t("Invites.Error"),
            description: t("Invites.ErrorDescription"),
            status: "error",
            duration: 9000,
            isClosable: true,
          })
        }
      })
  }

  function handleReject () {
    serviceCall(membershipService.reject(membership), navigate)
      .then((response) => {
        if (!response.hasFailed()) {
          toast({
            title: t("Invites.Rejected"),
            description: t("Invites.RejectedDescription"),
            status: "success",
            duration: 9000,
            isClosable: true,
          })
          setRefresh(!refresh);
          setIsLoading(true);
          onClose();
        } else {
          toast({
            title: t("Invites.Error"),
            description: t("Invites.ErrorDescription"),
            status: "error",
            duration: 9000,
            isClosable: true,
          })
        }
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
        <Link to={"/user/" + membership.band.id.toString()}>
          <HStack>
            <Avatar src={membership.band.profileImage}
              _dark={{
                backgroundColor: "white",
              }} />
            <Box ml='3'>
              <Text fontWeight='bold'>
                {membership.band.name}
              </Text>
            </Box>
          </HStack>
        </Link>
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
  }, [memberships])

  if (memberships.length === 0) return (<Text>{t("Invites.noInvites")}</Text>)
  return (<VStack width={'full'}>
    {membershipAux.map((membership) => {
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
  const query = useQuery();
  const [currentPage, setCurrentPage] = useState(parseInt(getQueryOrDefault(query, "page", "1")));
  const [maxPage, setMaxPage] = useState(1);
  const [previousPage, setPreviousPage] = useState("");
  const [nextPage, setNextPage] = useState("");
  const location = useLocation();

  useEffect(() => {
    if (!userId) return;
    serviceCall(
      membershipService.getUserMemberships({ user: userId, state: inviteStatuses.PENDING }, currentPage),
      navigate,
        (response: any) => {
        setMemberships(response ? response.getContent() : []);
        setMaxPage(response ? response.getMaxPage() : 1);
        setPreviousPage(response ? response.getPreviousPage() : "");
        setNextPage(response ? response.getNextPage() : "");
        setIsLoading(false);
        }
      )
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
        <Flex
            w="full"
            p={50}
            alignItems="center"
            justifyContent="center"
        >
          <PaginationWrapper>
            {currentPage > 1 && (
                <button
                    onClick={() => {
                      serviceCall(
                          membershipService.getUserMembershipsUrl(previousPage),
                          navigate,
                          (response) => {
                            setMemberships(response ? response.getContent() : []);
                            setPreviousPage(response ? response.getPreviousPage() : "");
                            setNextPage(response ? response.getNextPage() : "");
                          },
                          location
                      )
                      setCurrentPage(currentPage - 1);
                      const url = new URL(window.location.href);
                      url.searchParams.set('page', String(currentPage - 1));
                      window.history.pushState(null, '', url.toString());
                    }}
                    style={{ background: "none", border: "none" }}
                >
                  <ChevronLeftIcon mr={4} />

                </button>
            )}
            {t("Pagination.message", {
              currentPage: currentPage,
              maxPage: maxPage,
            })}
            {currentPage < maxPage && (
                <button
                    onClick={() => {
                      serviceCall(
                          membershipService.getUserMembershipsUrl(nextPage),
                          navigate,
                          (response) => {
                            setMemberships(response ? response.getContent() : []);
                            setPreviousPage(response ? response.getPreviousPage() : "");
                            setNextPage(response ? response.getNextPage() : "");
                          },
                          location
                      )
                      setCurrentPage(currentPage + 1);
                      const url = new URL(window.location.href);
                      url.searchParams.set('page', String(currentPage + 1));
                      window.history.pushState(null, '', url.toString());
                    }}
                    style={{ background: "none", border: "none" }}
                >
                  <ChevronRightIcon ml={4} />
                </button>
            )}
          </PaginationWrapper>
        </Flex>
      </SidenavLayout>
    </>
  )
}

export default Invites