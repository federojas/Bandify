import {
  Box,
  Center,
  Heading,
  Link,
  Avatar,
  Badge,
  Flex,
  HStack,
  Tab,
  TabList,
  TabPanel,
  TabPanels,
  Tabs,
  Text,
  Button,
  Modal,
  ModalBody,
  ModalCloseButton,
  ModalContent,
  ModalFooter,
  ModalHeader,
  ModalOverlay,
  useDisclosure,
  VStack,
  useColorModeValue,
  useToast
} from "@chakra-ui/react"
import { useEffect, useState } from "react";
import { Helmet } from "react-helmet";
import { useTranslation } from "react-i18next";
import { TiCancel, TiTick } from "react-icons/ti";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import { useAuditionService } from "../../contexts/AuditionService";
import { Application, Audition, User } from "../../models";
import { serviceCall } from "../../services/ServiceManager";
import AddToBandButton from "../User/AddToBandButton";
import { getQueryOrDefault, useQuery } from "../../hooks/useQuery";
import { PaginationWrapper } from "../../components/Pagination/pagination";
import { ChevronLeftIcon, ChevronRightIcon } from "@chakra-ui/icons";
import { useUserService } from "../../contexts/UserService";

function ApplicantInfo({ application, handleRefresh }: { application: Application, handleRefresh: () => void }) {
  const { isOpen, onOpen, onClose } = useDisclosure()
  const { t } = useTranslation();
  const toast = useToast();
  const navigate = useNavigate();
  const auditionService = useAuditionService();

  const onAccept = () => {
    serviceCall(auditionService.changeApplicationStatus(parseInt(application.audition.split('/')[application.audition.split('/').length - 1]), application.id, "ACCEPTED"),
      navigate
    ).then((response) => {
      if (response.hasFailed()) {
        toast({
          title: t("Invites.acceptError"),
          status: "error",
          isClosable: true,
        });
      } else {
        toast({
          title: t("Invites.acceptSuccess"),
          status: "success",
          isClosable: true,
        });
        handleRefresh();
        onClose();
      }
    })
  }

  const onReject = () => {
    serviceCall(auditionService.changeApplicationStatus(parseInt(application.audition.split('/')[application.audition.split('/').length - 1]), application.id, "REJECTED"),
      navigate
    ).then((response) => {
      if (response.hasFailed()) {
        toast({
          title: t("Invites.rejectError"),
          status: "error",
          isClosable: true,
        });
      } else {
        toast({
          title: t("Invites.rejectSuccess"),
          status: "success",
          isClosable: true,
        });
        handleRefresh();
        onClose();
      }
    })
  }

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
            <Text mt="2" mb="1">{t("AuditionApplicants.Subtitle2")}</Text>
            <Text as='i'>{application.message}</Text>
          </ModalBody>

          <ModalFooter>
            <Button leftIcon={<TiTick />} colorScheme='blue' mr={3} onClick={onAccept}>
              {t("Invites.Accept")}
            </Button>
            <Button leftIcon={<TiCancel />} colorScheme='red' onClick={onReject}>
              {t("Invites.Reject")}
            </Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  )
}

const ApplicantItem = ({ type = 'PENDING', application, handleRefresh, auditionId }: { type: string, application: Application, handleRefresh: () => void, auditionId: number }) => {
  const { t } = useTranslation();
  const scheme = type === "REJECTED" ? "red" : (type === "PENDING" ? undefined : "green")
  const label = type === "REJECTED" ? t("Applications.Rejected") : (type === "PENDING" ? t("Applications.Pending") : t("Applications.Accepted"))
  const isPending = type === "PENDING"
  const isAccepted = type === "ACCEPTED"
  const userService = useUserService();
  const [user, setUser] = useState<User>();
  const navigate = useNavigate();

  useEffect(() => {
    serviceCall(
      userService.getUserByUrl(application.applicant),
      navigate,
      (user) => {
        setUser(user);
      }
    )
  }, [navigate, userService])

  return (
    <Box borderWidth='1px' borderRadius='lg' p="4">
      <Flex alignItems={'center'} justify="space-between">
        <Link onClick={() => { navigate("/users/" + user?.id) }}>
          <HStack>
            <Avatar src={user?.profileImage} _dark={{
              backgroundColor: "white",
            }} />
            <Box ml='3'>
              <Text fontWeight='bold'>
                {user?.name + " " + user?.surname}
              </Text>
            </Box>
          </HStack>
        </Link>
        {isPending ? <ApplicantInfo application={application} handleRefresh={handleRefresh} /> :
          <Badge mx={2} colorScheme={scheme}>
            {label}
          </Badge>
        }
        {isAccepted && user &&
          <AddToBandButton user={user!} auditionId={auditionId} refresh={handleRefresh}/>
        }
      </Flex>

    </Box>
  )
}

const AuditionApplicants = () => {
  const { t } = useTranslation();
  const { id } = useParams();
  const query = useQuery();
  //TODO posible mejora de codigo
  const [currentPagePending, setCurrentPagePending] = useState((getQueryOrDefault(query, "state", "") === 'PENDING' || getQueryOrDefault(query, "state", "") === '') ? parseInt(getQueryOrDefault(query, "page", "1")) : 1);
  const [currentPageRejected, setCurrentPageRejected] = useState(getQueryOrDefault(query, "state", "") === 'REJECTED' ? parseInt(getQueryOrDefault(query, "page", "1")) : 1);
  const [currentPageAccepted, setCurrentPageAccepted] = useState(getQueryOrDefault(query, "state", "") === 'ACCEPTED' ? parseInt(getQueryOrDefault(query, "page", "1")) : 1);
  const [maxPageAccepted, setMaxPageAccepted] = useState(1);
  const [previousPageAccepted, setPreviousPageAccepted] = useState("");
  const [nextPageAccepted, setNextPageAccepted] = useState("");
  const [maxPageRejected, setMaxPageRejected] = useState(1);
  const [previousPageRejected, setPreviousPageRejected] = useState("");
  const [nextPageRejected, setNextPageRejected] = useState("");
  const [nextPagePending, setNextPagePending] = useState("");
  const [maxPagePending, setMaxPagePending] = useState(1);
  const [previousPagePending, setPreviousPagePending] = useState("");
  const [tabIndex, setTabIndex] = useState(() => {
    let state = getQueryOrDefault(query, "state", "PENDING");
    if (state === "PENDING")
      return 0;
    else if (state === "ACCEPTED")
      return 1;
    else
      return 2;
  });
  const location = useLocation();
  const [audition, setAudition] = useState<Audition>();
  const [pending, setPending] = useState<Application[]>([]);
  const [accepted, setAccepted] = useState<Application[]>([]);
  const [rejected, setRejected] = useState<Application[]>([]);
  const navigate = useNavigate();
  const auditionService = useAuditionService();
  const [refresh, setRefresh] = useState<boolean>(false);

  const handleRefresh = () => {
    setRefresh(!refresh)
  }

  useEffect(() => {

    serviceCall(
      auditionService.getAuditionById(Number(id)),
      navigate,
      (audition) => {
        setAudition(audition)
      }
    )

    serviceCall(
      auditionService.getAuditionApplications(Number(id), currentPagePending, 'PENDING'),
      navigate,
      (applications) => {
        setPending(applications.getContent())
        setMaxPagePending(applications ? applications.getMaxPage() : 1); //TODO revisar esto
        setPreviousPagePending(applications ? applications.getPreviousPage() : "");
        setNextPagePending(applications ? applications.getNextPage() : "");
      }
    )

    serviceCall(
      auditionService.getAuditionApplications(Number(id), currentPageAccepted, 'ACCEPTED'),
      navigate,
      (applications) => {
        setAccepted(applications.getContent())
        setMaxPageAccepted(applications ? applications.getMaxPage() : 1); //TODO revisar esto
        setPreviousPageAccepted(applications ? applications.getPreviousPage() : "");
        setNextPageAccepted(applications ? applications.getNextPage() : "");
      }
    )

    serviceCall(
      auditionService.getAuditionApplications(Number(id), currentPageRejected, 'REJECTED'),
      navigate,
      (applications) => {
        setRejected(applications.getContent())
        setMaxPageRejected(applications ? applications.getMaxPage() : 1); //TODO revisar esto
        setPreviousPageRejected(applications ? applications.getPreviousPage() : "");
        setNextPageRejected(applications ? applications.getNextPage() : "");
      }
    )

    const url = new URL(window.location.href);
    if (getQueryOrDefault(query, "state", "") === "")
      url.searchParams.set('state', "PENDING");
    window.history.pushState(null, '', url.toString());
  }, [navigate, auditionService, id, refresh])

  return (
    <Center py={'10'}>
      <VStack spacing={'4'}>
        <Heading>{t("AuditionApplicants.Title")}{audition?.title}</Heading>
        <Box bg={useColorModeValue("white", "gray.900")} px={4}
          py={4} shadow={'md'} rounded={'xl'} w={'3xl'}
        >
          <Tabs variant='soft-rounded' colorScheme='blue' onChange={
            (index) => {
              setTabIndex(index)
              const url = new URL(window.location.href);
              if (index === 0) {
                url.searchParams.set('page', String(currentPagePending));
                url.searchParams.set('state', 'PENDING');
              } else if (index === 1) {
                url.searchParams.set('page', String(currentPageAccepted));
                url.searchParams.set('state', 'ACCEPTED');
              } else {
                url.searchParams.set('page', String(currentPageRejected));
                url.searchParams.set('state', 'REJECTED');
              }
              window.history.pushState(null, '', url.toString());
            }
          } defaultIndex={tabIndex}>
            <TabList>
              <Tab>{t("Applications.Pending")}</Tab>
              <Tab>{t("Applications.Accepted")}</Tab>
              <Tab>{t("Applications.Rejected")}</Tab>
            </TabList>
            <TabPanels>
              <TabPanel mt="4">
                {pending.length > 0 ?
                  pending.map((application) => <ApplicantItem type={'PENDING'} application={application} handleRefresh={handleRefresh} auditionId={parseInt(id!)}/>)
                  :
                  <p>{t("AuditionApplicants.NoApplicants")}</p>
                }
                {/*TODO: ver si se puede hacer componente*/}
                <Flex
                  w="full"
                  p={50}
                  alignItems="center"
                  justifyContent="center"
                >

                  <PaginationWrapper>
                    {currentPagePending > 1 && (
                      <button
                        onClick={() => {
                          serviceCall(
                            auditionService.getAuditionApplicationsByUrl(previousPagePending),
                            navigate,
                            (response) => {
                              setPending(response ? response.getContent() : []);
                              setPreviousPagePending(response ? response.getPreviousPage() : "");
                              setNextPagePending(response ? response.getNextPage() : "");
                            },
                            location
                          )
                          setCurrentPagePending(currentPagePending - 1)
                          const url = new URL(window.location.href);
                          url.searchParams.set('page', String(currentPagePending - 1));
                          window.history.pushState(null, '', url.toString());
                        }}
                        style={{ background: "none", border: "none" }}
                      >
                        <ChevronLeftIcon mr={4} />
                      </button>
                    )}
                    {t("Pagination.message", {
                      currentPage: currentPagePending,
                      maxPage: maxPagePending,
                    })}
                    {currentPagePending < maxPagePending && (
                      <button
                        onClick={() => {
                          serviceCall(
                            auditionService.getAuditionApplicationsByUrl(nextPagePending),
                            navigate,
                            (response) => {
                              setPending(response ? response.getContent() : []);
                              setPreviousPagePending(response ? response.getPreviousPage() : "");
                              setNextPagePending(response ? response.getNextPage() : "");
                            },
                            location
                          )
                          setCurrentPagePending(currentPagePending + 1)
                          const url = new URL(window.location.href);
                          url.searchParams.set('page', String(currentPagePending + 1));
                          window.history.pushState(null, '', url.toString());
                        }}
                        style={{ background: "none", border: "none" }}
                      >
                        <ChevronRightIcon ml={4} />
                      </button>
                    )}
                  </PaginationWrapper>
                </Flex>
              </TabPanel>
              <TabPanel mt="4">
                {accepted.length > 0 ?
                  accepted.map((application) => <ApplicantItem type={'ACCEPTED'} application={application} handleRefresh={handleRefresh} auditionId={parseInt(id!)}/>)
                  :
                  <p>{t("AuditionApplicants.NoApplicants")}</p>
                }
                {/*TODO: ver si se puede hacer componente*/}
                <Flex
                  w="full"
                  p={50}
                  alignItems="center"
                  justifyContent="center"
                >

                  <PaginationWrapper>
                    {currentPageAccepted > 1 && (
                      <button
                        onClick={() => {
                          serviceCall(
                            auditionService.getAuditionApplicationsByUrl(previousPageAccepted),
                            navigate,
                            (response) => {
                              setAccepted(response ? response.getContent() : []);
                              setPreviousPageAccepted(response ? response.getPreviousPage() : "");
                              setNextPageAccepted(response ? response.getNextPage() : "");
                            },
                            location
                          )
                          setCurrentPageAccepted(currentPageAccepted - 1)
                          const url = new URL(window.location.href);
                          url.searchParams.set('page', String(currentPageAccepted - 1));
                          window.history.pushState(null, '', url.toString());
                        }}
                        style={{ background: "none", border: "none" }}
                      >
                        <ChevronLeftIcon mr={4} />
                      </button>
                    )}
                    {t("Pagination.message", {
                      currentPage: currentPageAccepted,
                      maxPage: maxPageAccepted,
                    })}
                    {currentPageAccepted < maxPageAccepted && (
                      <button
                        onClick={() => {
                          serviceCall(
                            auditionService.getAuditionApplicationsByUrl(nextPageAccepted),
                            navigate,
                            (response) => {
                              setAccepted(response ? response.getContent() : []);
                              setPreviousPageAccepted(response ? response.getPreviousPage() : "");
                              setNextPageAccepted(response ? response.getNextPage() : "");
                            },
                            location
                          )
                          setCurrentPageAccepted(currentPageAccepted + 1)
                          const url = new URL(window.location.href);
                          url.searchParams.set('page', String(currentPageAccepted + 1));
                          window.history.pushState(null, '', url.toString());
                        }}
                        style={{ background: "none", border: "none" }}
                      >
                        <ChevronRightIcon ml={4} />
                      </button>
                    )}
                  </PaginationWrapper>
                </Flex>
              </TabPanel>
              <TabPanel mt="4">
                {rejected.length > 0 ?
                  rejected.map((application) => <ApplicantItem type={'REJECTED'} application={application} handleRefresh={handleRefresh} auditionId={parseInt(id!)} />)
                  :
                  <p>{t("AuditionApplicants.NoApplicants")}</p>
                }
                {/*TODO: ver si se puede hacer componente*/}
                <Flex
                  w="full"
                  p={50}
                  alignItems="center"
                  justifyContent="center"
                >

                  <PaginationWrapper>
                    {currentPageRejected > 1 && (
                      <button
                        onClick={() => {
                          serviceCall(
                            auditionService.getAuditionApplicationsByUrl(previousPageRejected),
                            navigate,
                            (response) => {
                              setRejected(response ? response.getContent() : []);
                              setPreviousPageRejected(response ? response.getPreviousPage() : "");
                              setNextPageRejected(response ? response.getNextPage() : "");
                            },
                            location
                          )
                          setCurrentPageRejected(currentPageRejected - 1)
                          const url = new URL(window.location.href);
                          url.searchParams.set('page', String(currentPageRejected - 1));
                          window.history.pushState(null, '', url.toString());
                        }}
                        style={{ background: "none", border: "none" }}
                      >
                        <ChevronLeftIcon mr={4} />
                      </button>
                    )}
                    {t("Pagination.message", {
                      currentPage: currentPageRejected,
                      maxPage: maxPageRejected,
                    })}
                    {currentPageRejected < maxPageRejected && (
                      <button
                        onClick={() => {
                          serviceCall(
                            auditionService.getAuditionApplicationsByUrl(nextPageRejected),
                            navigate,
                            (response) => {
                              setRejected(response ? response.getContent() : []);
                              setPreviousPageRejected(response ? response.getPreviousPage() : "");
                              setNextPageRejected(response ? response.getNextPage() : "");
                            },
                            location
                          )
                          setCurrentPageRejected(currentPageRejected + 1)
                          const url = new URL(window.location.href);
                          url.searchParams.set('page', String(currentPageRejected + 1));
                          window.history.pushState(null, '', url.toString());
                        }}
                        style={{ background: "none", border: "none" }}
                      >
                        <ChevronRightIcon ml={4} />
                      </button>
                    )}
                  </PaginationWrapper>
                </Flex>
              </TabPanel>
            </TabPanels>
          </Tabs>
        </Box>
      </VStack>
    </Center>
  )
}

export default AuditionApplicants