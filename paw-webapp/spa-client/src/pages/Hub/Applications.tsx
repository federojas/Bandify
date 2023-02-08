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
  const [currentPage, setCurrentPage] = useState(parseInt(getQueryOrDefault(query, "page", "1")));
  const [maxPage, setMaxPage] = useState(1);
  const [previousPage, setPreviousPage] = useState("");
  const [nextPage, setNextPage] = useState("");
  const location = useLocation();

  useEffect(() => {
    serviceCall(
      userService.getUserApplications(userId!, "PENDING"),
      navigate,
      (apps) => {
        setPending(apps.getContent())
        setMaxPage(apps ? apps.getMaxPage() : 1); //TODO revisar esto
        setPreviousPage(apps ? apps.getPreviousPage() : "");
        setNextPage(apps ? apps.getNextPage() : "");
      }
    )

    serviceCall(
      userService.getUserApplications(userId!, "REJECTED"),
      navigate,
      (apps) => {
        setRejected(apps.getContent())
        setMaxPage(apps ? apps.getMaxPage() : 1); //TODO revisar esto
        setPreviousPage(apps ? apps.getPreviousPage() : "");
        setNextPage(apps ? apps.getNextPage() : "");
      }
    )

    serviceCall(
      userService.getUserApplications(userId!, "ACCEPTED"),
      navigate,
      (apps) => {
        setAccepted(apps.getContent())
        setMaxPage(apps ? apps.getMaxPage() : 1); //TODO revisar esto
        setPreviousPage(apps ? apps.getPreviousPage() : "");
        setNextPage(apps ? apps.getNextPage() : "");
      }
    )
  }, [userService, userId, navigate])



  // TODO: Get the three differents arrays of applications from the Service

  return (
    <>
    <Helmet>
          <title>{t("Hub.Applications")}</title>
    </Helmet>
    <SidenavLayout>
      <Text fontSize='2xl' fontWeight='bold' mb='4'>{t("Applications.Title")}</Text>
      <Tabs variant='soft-rounded' colorScheme='blue'>
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
                {currentPage > 1 && (
                    <button
                        onClick={() => {
                          serviceCall(
                              userService.getUserApplicationsByUrl(previousPage),
                              navigate,
                              (response) => {
                                setPending(response ? response.getContent() : []);
                                setPreviousPage(response ? response.getPreviousPage() : "");
                                setNextPage(response ? response.getNextPage() : "");
                              },
                              location
                          )
                          setCurrentPage(currentPage - 1)
                          const url = new URL(window.location.href);
                          url.searchParams.set('page', String(currentPage - 1));
                          window.history.pushState(null, '', url.toString());
                        }}
                        style={{ background: "none", border: "none" }}
                    >
                      <ChevronLeftIcon mr={4}/>
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
                              userService.getUserApplicationsByUrl(nextPage),
                              navigate,
                              (response) => {
                                setPending(response ? response.getContent() : []);
                                setPreviousPage(response ? response.getPreviousPage() : "");
                                setNextPage(response ? response.getNextPage() : "");
                              },
                              location
                          )
                          setCurrentPage(currentPage + 1)
                          const url = new URL(window.location.href);
                          url.searchParams.set('page', String(currentPage + 1));
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
                {currentPage > 1 && (
                    <button
                        onClick={() => {
                          serviceCall(
                              userService.getUserApplicationsByUrl(previousPage),
                              navigate,
                              (response) => {
                                setAccepted(response ? response.getContent() : []);
                                setPreviousPage(response ? response.getPreviousPage() : "");
                                setNextPage(response ? response.getNextPage() : "");
                              },
                              location
                          )
                          setCurrentPage(currentPage - 1)
                          const url = new URL(window.location.href);
                          url.searchParams.set('page', String(currentPage - 1));
                          window.history.pushState(null, '', url.toString());
                        }}
                        style={{ background: "none", border: "none" }}
                    >
                      <ChevronLeftIcon mr={4}/>
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
                              userService.getUserApplicationsByUrl(nextPage),
                              navigate,
                              (response) => {
                                setAccepted(response ? response.getContent() : []);
                                setPreviousPage(response ? response.getPreviousPage() : "");
                                setNextPage(response ? response.getNextPage() : "");
                              },
                              location
                          )
                          setCurrentPage(currentPage + 1)
                          const url = new URL(window.location.href);
                          url.searchParams.set('page', String(currentPage + 1));
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
                {currentPage > 1 && (
                    <button
                        onClick={() => {
                          serviceCall(
                              userService.getUserApplicationsByUrl(previousPage),
                              navigate,
                              (response) => {
                                setRejected(response ? response.getContent() : []);
                                setPreviousPage(response ? response.getPreviousPage() : "");
                                setNextPage(response ? response.getNextPage() : "");
                              },
                              location
                          )
                          setCurrentPage(currentPage - 1)
                          const url = new URL(window.location.href);
                          url.searchParams.set('page', String(currentPage - 1));
                          window.history.pushState(null, '', url.toString());
                        }}
                        style={{ background: "none", border: "none" }}
                    >
                      <ChevronLeftIcon mr={4}/>
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
                              userService.getUserApplicationsByUrl(nextPage),
                              navigate,
                              (response) => {
                                setRejected(response ? response.getContent() : []);
                                setPreviousPage(response ? response.getPreviousPage() : "");
                                setNextPage(response ? response.getNextPage() : "");
                              },
                              location
                          )
                          setCurrentPage(currentPage + 1)
                          const url = new URL(window.location.href);
                          url.searchParams.set('page', String(currentPage + 1));
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