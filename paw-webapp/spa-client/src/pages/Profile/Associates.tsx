import {
  Box, Center, Heading,
  Avatar, Flex, HStack, VStack, useColorModeValue
} from "@chakra-ui/react"
import React, { useContext, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { HiUserGroup } from "react-icons/hi";
import { useLocation, useNavigate } from "react-router-dom";
import AuthContext from "../../contexts/AuthContext";
import { useMembershipService } from "../../contexts/MembershipService";
import { useUserService } from "../../contexts/UserService";
import { User } from "../../models";
import Membership from "../../models/Membership";
import { serviceCall } from "../../services/ServiceManager";
import MembershipItem from "../User/MembershipItem";
import { PaginationWrapper } from "../../components/Pagination/pagination";
import { ChevronLeftIcon, ChevronRightIcon } from "@chakra-ui/icons";
import { getQueryOrDefault, useQuery } from "../../hooks/useQuery";

const ProfileAssociates = () => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const authContext = useContext(AuthContext);
  const currentUserId = Number(authContext.userId);
  const [currentUser, setCurrentUser] = useState<User>();
  const userService = useUserService();
  const membershipService = useMembershipService();
  const [memberships, setMemberships] = useState<Membership[]>([]);
  const query = useQuery();
  const [currentPage, setCurrentPage] = useState(parseInt(getQueryOrDefault(query, "page", "1")));
  const [maxPage, setMaxPage] = useState(1);
  const [previousPage, setPreviousPage] = useState("");
  const [nextPage, setNextPage] = useState("");
  const location = useLocation();
  const [refreshMembership, setRefreshMembership] = useState(false);

  const handleRefresh = () => {
    setRefreshMembership(!refreshMembership);
  }

  useEffect(() => {
    serviceCall(
      userService.getUserById(currentUserId),
      navigate,
      (response: any) => {
        setCurrentUser(response);
      }
    )
  }, [])

  useEffect(() => {
    if (currentUserId) {
      serviceCall(
        membershipService.getUserMemberships({ user: currentUserId as number, state: "ACCEPTED" }),
        navigate,
        (response: any) => {
          setMemberships(response ? response.getContent() : []);
          setMaxPage(response ? response.getMaxPage() : 1);
          setPreviousPage(response ? response.getPreviousPage() : "");
          setNextPage(response ? response.getNextPage() : "");
        }
      )
    }
  }, [refreshMembership])


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
            src={currentUser?.profileImage}
            _dark={{
              backgroundColor: "white",
            }}
          />
          <Heading size="md" noOfLines={1}>{currentUser?.name}{' '}{currentUser?.surname}</Heading>
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
                    <MembershipItem membershipId={m.id} contraUser={currentUser?.band ? m.artist : m.band} description={m.description} roles={m.roles} refresh={handleRefresh} />
                  )
                })
                :
                <>{t("Profile.noMemberships")}</>
              }
            </VStack>
          </Flex>
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
                    setCurrentPage(currentPage - 1)
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
                    setCurrentPage(currentPage + 1)
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
        </Box>
      </VStack>
    </Center>
  )
}

export default ProfileAssociates