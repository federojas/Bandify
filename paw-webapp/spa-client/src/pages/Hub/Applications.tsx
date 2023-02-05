import { Avatar, Badge, Box, Flex, HStack, Tab, TabList, TabPanel, TabPanels, Tabs, Text } from "@chakra-ui/react";
import { useTranslation } from "react-i18next";
import SidenavLayout from "./SidenavLayout";

const ApplicationItem = ({ type = 'PENDING' }: { type: string }) => {
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
              Segun Adebayo

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
  const pendingApplications = [];
  const acceptedApplications = [];
  const rejectedApplications = [];

  // TODO: Get the three differents arrays of applications from the Service

  return (
    <SidenavLayout>
      <Tabs variant='soft-rounded' colorScheme='blue'>
        <TabList>
          <Tab>{t("Applications.Pending")}</Tab>
          <Tab>{t("Applications.Accepted")}</Tab>
          <Tab>{t("Applications.Rejected")}</Tab>
        </TabList>
        <TabPanels>
          <TabPanel mt="4">
            {pendingApplications.length > 0 ?
              <ApplicationItem type={'PENDING'} />
              :
              <p>{t("Applications.NoApplications")}</p>
            }
          </TabPanel>
          <TabPanel mt="4">
            {acceptedApplications.length > 0 ?
              <ApplicationItem type={'ACCEPTED'} />
              :
              <p>{t("Applications.NoApplications")}</p>
            }

          </TabPanel>
          <TabPanel mt="4">
            {rejectedApplications.length > 0 ?
              <ApplicationItem type={'REJECTED'} />
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