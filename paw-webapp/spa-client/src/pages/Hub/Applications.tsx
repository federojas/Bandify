import { Badge, Box, Flex, HStack, Tab, TabList, TabPanel, TabPanels, Tabs, Text } from "@chakra-ui/react";
import { useContext, useEffect, useState } from "react";
import { Helmet } from "react-helmet";
import { useTranslation } from "react-i18next";
import {useLocation, useNavigate} from "react-router-dom";
import AuthContext from "../../contexts/AuthContext";
import { useUserService } from "../../contexts/UserService";
import {Application, Audition} from "../../models";
import { serviceCall } from "../../services/ServiceManager";
import SidenavLayout from "./SidenavLayout";
import {useAuditionService} from "../../contexts/AuditionService";
import {PaginationWrapper} from "../../components/Pagination/pagination";
import {ChevronLeftIcon, ChevronRightIcon} from "@chakra-ui/icons";
import {getQueryOrDefault, useQuery} from "../../hooks/useQuery";

const ApplicationItem = ({ type = 'PENDING', application }: { type: string, application: Application; }) => {
  const { t } = useTranslation();
  const scheme = type === "REJECTED" ? "red" : (type === "PENDING" ? undefined : "green")
  const navigate = useNavigate();

  const label = type === "REJECTED" ? t("Applications.Rejected") : (type === "PENDING" ? t("Applications.Pending") : t("Applications.Accepted"))
  return (
    <Box borderWidth='1px' borderRadius='lg' p="4" onClick={() => {
      navigate("/auditions/" + parseInt(application.audition.split('/')[application.audition.split('/').length - 1]))
    }} style={{cursor: "pointer"}}>
      <Flex alignItems={'center'} justify="space-between">
        <HStack>
          <Box ml='3'>
            <Text fontWeight='bold'>
              {application?.title}
            </Text>
          </Box>
        </HStack>
        <Badge ml='1' colorScheme={scheme}>
          {label}
        </Badge>
      </Flex>

    </Box>
  )
}

const Applications = () => {
  const { t } = useTranslation();
  const [pending, setPending] = useState<Application[]>([]);
  const [accepted, setAccepted] = useState<Application[]>([]);
  const [rejected, setRejected] = useState<Application[]>([]);
  const navigate = useNavigate();
  const { userId } = useContext(AuthContext);
  const userService = useUserService();
  const query = useQuery();
  //TODO posible mejora de codigo
  const [currentPagePending, setCurrentPagePending] = useState((getQueryOrDefault(query, "state", "") === 'PENDING' || getQueryOrDefault(query, "state", "") === '' ) ? parseInt(getQueryOrDefault(query, "page", "1")) : 1);
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
    if(state === "PENDING")
      return 0;
    else if(state === "ACCPETED")
      return 1;
    else
      return 2;
  });
  const location = useLocation();

  useEffect(() => {
    serviceCall(
      userService.getUserApplications(userId!, "PENDING", currentPagePending),
      navigate,
      (apps) => {
        setPending(apps.getContent())
        setMaxPagePending(apps ? apps.getMaxPage() : 1); //TODO revisar esto
        setPreviousPagePending(apps ? apps.getPreviousPage() : "");
        setNextPagePending(apps ? apps.getNextPage() : "");
      }
    )

    serviceCall(
      userService.getUserApplications(userId!, "REJECTED", currentPageAccepted),
      navigate,
      (apps) => {
        setRejected(apps.getContent())
        setMaxPageRejected(apps ? apps.getMaxPage() : 1); //TODO revisar esto
        setPreviousPageRejected(apps ? apps.getPreviousPage() : "");
        setNextPageRejected(apps ? apps.getNextPage() : "");
      }
    )

    serviceCall(
      userService.getUserApplications(userId!, "ACCEPTED", currentPageRejected),
      navigate,
      (apps) => {
        setAccepted(apps.getContent())
        setMaxPageAccepted(apps ? apps.getMaxPage() : 1); //TODO revisar esto
        setPreviousPageAccepted(apps ? apps.getPreviousPage() : "");
        setNextPageAccepted(apps ? apps.getNextPage() : "");
      }
    )

    const url = new URL(window.location.href);
    if(getQueryOrDefault(query, "state", "") === "")
      url.searchParams.set('state', "PENDING");
    window.history.pushState(null, '', url.toString());
  }, [userId, navigate])



  // TODO: Get the three differents arrays of applications from the Service

  return (
    <>
    <Helmet>
          <title>{t("Hub.Applications")}</title>
    </Helmet>
    <SidenavLayout>
      <Text fontSize='2xl' fontWeight='bold' mb='4'>{t("Applications.Title")}</Text>
      <Tabs variant='soft-rounded' colorScheme='blue' onChange={
        (index) => {
          setTabIndex(index)
          const url = new URL(window.location.href);
          if(index === 0) {
            url.searchParams.set('page', String(currentPagePending));
            url.searchParams.set('state', 'PENDING');
          } else if(index === 1) {
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
              pending.map((app) => {
                return <ApplicationItem key={app.id} type={'PENDING'} application={app} />
              })
              :
              <p>{t("Applications.NoApplications")}</p>
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
                              userService.getUserApplicationsByUrl(previousPagePending),
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
                      <ChevronLeftIcon mr={4}/>
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
                              userService.getUserApplicationsByUrl(nextPagePending),
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
                      <ChevronRightIcon ml={4}/>
                    </button>
                )}
              </PaginationWrapper>
            </Flex>
          </TabPanel>
          <TabPanel mt="4">

            {accepted.length > 0 ?

              accepted.map((app) => {
                return <ApplicationItem key={app.id} type={'ACCEPTED'} application={app} />
              })

              :
              <p>{t("Applications.NoApplications")}</p>
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
                              userService.getUserApplicationsByUrl(previousPageAccepted),
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
                      <ChevronLeftIcon mr={4}/>
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
                              userService.getUserApplicationsByUrl(nextPageAccepted),
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
                      <ChevronRightIcon ml={4}/>
                    </button>
                )}
              </PaginationWrapper>
            </Flex>
          </TabPanel>
          <TabPanel mt="4">
            {rejected.length > 0 ?

              rejected.map((app) => {
                return <ApplicationItem key={app.id} type={'REJECTED'} application={app} />
              })

              :
              <p>{t("Applications.NoApplications")}</p>
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
                              userService.getUserApplicationsByUrl(previousPageRejected),
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
                      <ChevronLeftIcon mr={4}/>
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
                              userService.getUserApplicationsByUrl(nextPageRejected),
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
                      <ChevronRightIcon ml={4}/>
                    </button>
                )}
              </PaginationWrapper>
            </Flex>
          </TabPanel>
        </TabPanels>
      </Tabs>
    </SidenavLayout>
    </>
  )
}

export default Applications;