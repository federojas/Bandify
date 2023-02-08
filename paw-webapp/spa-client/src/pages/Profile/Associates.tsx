import {
  Box, Center, Heading,
  Avatar, Badge, Flex, HStack, Tab, TabList, TabPanel, TabPanels, Tabs, Text, Button,
  Modal, ModalBody, ModalCloseButton, ModalContent, ModalFooter, ModalHeader, ModalOverlay, useDisclosure, VStack, useColorModeValue
} from "@chakra-ui/react"
import { useContext, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { HiUserGroup } from "react-icons/hi";
import { useNavigate, useParams } from "react-router-dom";
import AuthContext from "../../contexts/AuthContext";
import { useMembershipService } from "../../contexts/MembershipService";
import { useUserService } from "../../contexts/UserService";
import { User } from "../../models";
import Membership from "../../models/Membership";
import { serviceCall } from "../../services/ServiceManager";
import MembershipItem from "../User/MembershipItem";

const ProfileAssociates = () => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const authContext = useContext(AuthContext);
  const currentUserId = Number(authContext.userId);
  const [currentUser, setCurrentUser] = useState<User>();
  const userService = useUserService();
  const membershipService = useMembershipService();
  const [memberships, setMemberships] = useState<Membership[]>([]);

  useEffect(() => {

    serviceCall(
      userService.getUserById(currentUserId),
      navigate,
      (response: any) => {
        setCurrentUser(response);
      }
    )


  }, [currentUserId])

  useEffect(() => {
    if (currentUser) {
      serviceCall(
        membershipService.getUserMemberships({ user: currentUserId as number, state: "ACCEPTED", preview: true }),
        navigate,
        (response: any) => {
          console.log(response.getContent())
          setMemberships(response.getContent());
        }
      )
    }
  }, [navigate, currentUser])


  return (
    <Center py={'10'}>
      <VStack spacing={'4'}>
        <Flex
          as="a"
          onClick={() => {
            navigate("/profile");
          }}
          flex="1"
          gap="4"
          alignItems="center"
          justifyContent={"start"}
          className="ellipsis-overflow"
          cursor={'pointer'}
        >
          <Avatar
            src={currentUser?.profileImage} //TODO: revisar ALT
          />
          <Heading size="md">{currentUser?.name}{' '}{currentUser?.surname}</Heading> {/*TODO: poner text overflow*/}
        </Flex>


        <Box bg={useColorModeValue("white", "gray.900")} px={4}
          py={6} shadow={'md'} rounded={'xl'} w={'xl'}
        >
          <Flex direction={'column'} alignItems='center' gap={'4'}>
            <HStack>
              <HiUserGroup size={'40'} />
              <Heading>{currentUser?.band ? t("Profile.BandMembers") : t("Profile.playsIn")}</Heading>
            </HStack>
            <VStack mt={4} w={'80%'}>
              {memberships.length > 0 ?
                memberships.map((m) => {
                  return (
                    <MembershipItem contraUser={currentUser?.band ? m.artist : m.band} description={m.description} roles={m.roles} />
                  )
                })
                :
                <>{t("Profile.noMemberships")}</>
              }
            </VStack>
          </Flex>
        </Box>
      </VStack>
    </Center>
  )
}

export default ProfileAssociates