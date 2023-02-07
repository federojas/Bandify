import { Avatar, Badge, Box, Flex, HStack, Tab, TabList, TabPanel, TabPanels, Tabs, Text, VStack } from "@chakra-ui/react";
import { useContext, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import AuthContext from "../../contexts/AuthContext";
import { useUserService } from "../../contexts/UserService";
import { Application } from "../../models";
import { serviceCall } from "../../services/ServiceManager";
import SidenavLayout from "./SidenavLayout";

const ApplicationItem = ({ type = 'PENDING', application }: { type: string, application: Application }) => {
  const { t } = useTranslation();
  const scheme = type === "REJECTED" ? "red" : (type === "PENDING" ? undefined : "green")

  const label = type === "REJECTED" ? t("Applications.Rejected") : (type === "PENDING" ? t("Applications.Pending") : t("Applications.Accepted"))
  return (
    <Box borderWidth='1px' borderRadius='lg' p="4">
      <Flex alignItems={'center'} justify="space-between">
        <HStack>
          <Avatar src='https://bit.ly/sage-adebayo' />
          <Box ml='3'>
            <Text fontWeight='bold'>
              {application.audition}
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

  useEffect(() => {
    serviceCall(
      userService.getUserApplications(userId!, "PENDING"),
      navigate,
      (apps) => {
        setPending(apps)
        console.log(apps)
      }
    )

    serviceCall(
      userService.getUserApplications(userId!, "REJECTED"),
      navigate,
      (apps) => {
        setRejected(apps)
        console.log(apps)
      }
    )

    serviceCall(
      userService.getUserApplications(userId!, "SELECTED"),
      navigate,
      (apps) => {
        setAccepted(apps)
        console.log(apps)
      }
    )
  }, [userService, userId, navigate])



  // TODO: Get the three differents arrays of applications from the Service

  return (
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
          </TabPanel>
          <TabPanel mt="4">

            {accepted.length > 0 ?

              accepted.map((app) => {
                return <ApplicationItem key={app.id} type={'ACCEPTED'} application={app} />
              })

              :
              <p>{t("Applications.NoApplications")}</p>
            }

          </TabPanel>
          <TabPanel mt="4">
            {rejected.length > 0 ?

              rejected.map((app) => {
                return <ApplicationItem key={app.id} type={'REJECTED'} application={app} />
              })

              :
              <p>{t("Applications.NoApplications")}</p>
            }

          </TabPanel>
        </TabPanels>
      </Tabs>
    </SidenavLayout>
  )
}

export default Applications;