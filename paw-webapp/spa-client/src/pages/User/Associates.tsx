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
import MembershipItem from "./MembershipItem";

const Associates = () => {
  const { t } = useTranslation();
  const { id } = useParams();
  const userId = Number(id);
  const navigate = useNavigate();
  const [user, setUser] = useState<User>();
  const authContext = useContext(AuthContext);
  const currentUserId = Number(authContext.userId);
  const [currentUser, setCurrentUser] = useState<User>();
  const userService = useUserService();
  const membershipService = useMembershipService();
  const [memberships, setMemberships] = useState<Membership[]>([]);

  useEffect(() => {
    serviceCall(
      userService.getUserById(userId!),
      navigate,
      (response: any) => {
        setUser(response);
      }
    )

    serviceCall(
      userService.getUserById(currentUserId),
      navigate,
      (response: any) => {
        setCurrentUser(response);
      }
    )


  }, [userId])

  useEffect(() => {
    if (user && currentUser) {
      serviceCall(
        membershipService.getUserMemberships({ user: user?.id as number, state: "ACCEPTED", preview: true }),
        navigate,
        (response: any) => {
          console.log(response.getContent())
          setMemberships(response.getContent());
        }
      )
    }
  }, [user, navigate, currentUser])


  return (
    <Center py={'10'}>
      <VStack spacing={'4'}>
        <Flex
          as="a"
          onClick={() => {
            navigate("/users/" + id);
          }}
          flex="1"
          gap="4"
          alignItems="center"
          justifyContent={"start"}
          className="ellipsis-overflow"
          cursor={'pointer'}
        >
          <Avatar
            src={user?.profileImage} //TODO: revisar ALT
          />
          <Heading size="md">{user?.name}{' '}{user?.surname}</Heading> {/*TODO: poner text overflow*/}
        </Flex>


        <Box bg={useColorModeValue("white", "gray.900")} px={4}
          py={6} shadow={'md'} rounded={'xl'} w={'xl'}
        >
          <Flex direction={'column'} alignItems='center' gap={'4'}>
            <HStack>
              <HiUserGroup size={'40'} />
              <Heading>{user?.band ? t("Profile.BandMembers") : t("Profile.playsIn")}</Heading>
            </HStack>
            <VStack mt={4} w={'80%'}>
              {memberships.length > 0 ?
                memberships.map((m) => {
                  return (
                    <MembershipItem contraUser={user?.band ? m.artist : m.band} description={m.description} roles={m.roles} />
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

export default Associates