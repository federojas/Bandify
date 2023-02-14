import React, { useContext, useEffect, useState } from "react";
import "../../styles/profile.css";
import "../../styles/postCard.css";
import { useTranslation } from "react-i18next";
import {
  Box,
  Button,
  Center,
  Container,
  Divider,
  Flex,
  Grid,
  GridItem,
  Heading,
  HStack,
  Image,
  Stack,
  Text,
  useColorModeValue,
  VStack,
} from "@chakra-ui/react";
import { HiUserGroup } from "react-icons/hi";
import { GrView } from "react-icons/gr";

import ArtistTag from "../../components/Tags/ArtistTag";
import BandTag from "../../components/Tags/BandTag";
import { ImLocation } from "react-icons/im";
import GenreTag from "../../components/Tags/GenreTag";
import RoleTag from "../../components/Tags/RoleTag";
import AuthContext from "../../contexts/AuthContext";
import { serviceCall } from "../../services/ServiceManager";
import { useNavigate } from "react-router-dom";
import { User } from "../../models";
import { useUserService } from "../../contexts/UserService";
import { AiOutlineEdit, AiOutlineUserAdd } from "react-icons/ai";
import { FiMusic } from "react-icons/fi";
import { useMembershipService } from "../../contexts/MembershipService";
import Membership from "../../models/Membership";
import MembershipItem from "../User/MembershipItem";
import { Helmet } from "react-helmet";
import SocialMediaModal from "../User/SocialMediaModal";
import SocialMediaTag from "./SocialMediaTag";
import SocialMedia from "../../models/SocialMedia";
import BackArrow from "../../components/BackArrow/BackArrow";

const Profile = () => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const [user, setUser] = React.useState<User>();
  const userService = useUserService();
  const [isLoading, setIsLoading] = useState(true);
  const { userId, updateProfileImg } = useContext(AuthContext);
  const filterAvailable = require(`../../images/available.png`);
  const bg = useColorModeValue("white", "gray.900")
  const membershipService = useMembershipService();
  const [memberships, setMemberships] = React.useState<Membership[]>([]);
  const [socialMedia, setSocialMedia] = useState<SocialMedia[]>([]);
  const [refreshMedia, setRefreshMedia] = useState(false);
  const [refreshMembership, setRefreshMembership] = useState(false);
  const authContext = useContext(AuthContext);
  const currentUserId = Number(authContext.userId);

  useEffect(() => {
    if (user) {
      serviceCall(
        membershipService.getUserMemberships({ user: user?.id as number, state: "ACCEPTED", preview: true }, 1),
        navigate,
        (response: any) => {
          setMemberships(response.getContent());
        }
      )

      serviceCall(
        userService.getUserSocialMedia(user.id),
        navigate,
        (response: any) => {
          setSocialMedia(response);
        }
      )
    }
  }, [user, navigate, user, refreshMedia, refreshMembership])

  useEffect(() => {
    serviceCall(
      userService.getUserById(userId!),
      navigate,
      (response: any) => {
        setUser(response);
        updateProfileImg(response.profileImage);
        setIsLoading(false)
      }
    )
  }, [userService]);



  return (
    <>
      <Flex ml={100} mt={8} mb={-20}>
        <BackArrow/>
      </Flex>
      <Helmet>
        <title>{t("NavBar.Profile")}</title>
      </Helmet>
      <Flex>
      <Container maxW={"5xl"} px={"0"} py={8}>
      <Flex justify={'start'}>
        </Flex>
        {isLoading ? <Center mt={'25%'}><span className="loader" /></Center> : (
          <Stack spacing={4}>
            <Box
              w={"full"}
              bg={bg}
              rounded={"lg"}
              boxShadow={"lg"}
              p={6}
            >
              <Flex justify={'space-between'}>
                <Flex flex={2}>
                  <Image
                    src={user?.profileImage}
                    alt={t("Alts.profilePicture")}
                    borderRadius="full"
                    boxSize="150px"
                    objectFit={'cover'}
                    shadow="lg"
                    border="5px solid"
                    borderColor="gray.800"
                    _dark={{
                      borderColor: "gray.200",
                      backgroundColor: "white"
                    }}
                  />
                  {user?.available ? <Image
                    src={filterAvailable}
                    alt={t("Alts.available")}
                    boxSize="141px"
                    ml={1}
                    mt={1.5}
                    borderRadius="full"
                    position={"absolute"}
                  /> : <></>
                  }
                </Flex>
                <Flex direction={'column'} flex={6} align={"left"} gap={2}>
                  <Box maxW={'lg'}>
                    <Heading fontSize={"3xl"} fontWeight={700}>
                      {user?.name}{" "}
                      {user?.surname && <>{user?.surname}</>}
                    </Heading>
                  </Box>
                  {user?.band ? <BandTag /> : <ArtistTag />}
                  <Text color={"gray.500"} fontSize={"xl"} maxW={'lg'}>
                    {user?.description}
                  </Text>
                  {
                    user?.location &&
                    <HStack>
                      <ImLocation />
                      <Text color={"gray.500"}> {user?.location}</Text>
                    </HStack>
                  }
                </Flex>
                <Flex direction={'column'} flex={2} gap={2} justify={'center'}>
                  <Button leftIcon={<AiOutlineEdit />} w={'44'} colorScheme='teal' onClick={() => {
                    let postfix = user?.band ? 'editBand' : 'editArtist';
                    let url = "/profile/" + postfix
                    navigate(url);
                  }
                  }>{t("Profile.edit")}</Button>
                  {!user?.band ? <>
                    <Button leftIcon={<FiMusic />} w={'44'} colorScheme={'cyan'} onClick={() => { navigate('/profile/applications') }}>
                      {t("Hub.Applications")}
                    </Button>
                    <Button leftIcon={<AiOutlineUserAdd />} colorScheme={'linkedin'} w={'44'} onClick={() => { navigate('/profile/invites') }}>
                      {t("Hub.Invites")}
                    </Button>
                  </>
                    :
                    <>
                      <Button leftIcon={<FiMusic />} w={'44'} colorScheme={'cyan'} onClick={() => { navigate('/profile/auditions') }}>
                        {t("MyAuditions.title")}
                      </Button>
                    </>
                  }
                </Flex>
              </Flex>
            </Box>

            <Grid templateColumns={"repeat(5,1fr)"} gap={4}>
              <GridItem
                w={"full"}
                colSpan={2}
                bg={bg}
                rounded={"lg"}
                boxShadow={"lg"}
                p={6}
              >
                <VStack spacing={4} justifyItems="start">
                  <Heading fontSize={"2xl"} fontWeight={500}>
                    {t("Profile.favoriteGenres")}
                  </Heading>
                  {user?.genres && user?.genres.length > 0 ? (
                    <HStack wrap={"wrap"}>
                      {user?.genres.map((genre) => (
                        <GenreTag genre={genre} />
                      ))}
                    </HStack>
                  ) : (
                    <>{t("Profile.noFavoriteGenres")}</>
                  )}
                </VStack>
                <Divider marginY={6} />
                <VStack spacing={4} justifyItems="start">
                  <Heading fontSize={"2xl"} fontWeight={500}>
                    {user?.band ? t("Profile.rolesBand") : t("Profile.rolesArtist")}
                  </Heading>
                  {user?.roles && user?.roles.length > 0 ? (
                    <HStack wrap={"wrap"}>
                      {user?.roles.map((role) => (
                        <RoleTag role={role} />
                      ))}
                    </HStack>
                  ) : (
                    <>{t("Profile.noRoles")}</>
                  )}
                </VStack>
              </GridItem>
              <GridItem
                w={"full"}
                colSpan={3}
                bg={bg}
                rounded={"lg"}
                boxShadow={"lg"}
                p={6}
              >
                <VStack spacing={4} justifyItems="start">
                  <HStack w={'full'} justify={'space-around'}>
                    <HStack>
                      <HiUserGroup />
                      <Heading fontSize={"2xl"} fontWeight={500}>
                        {t("Profile.socialMedia")}
                      </Heading>
                    </HStack>
                    <SocialMediaModal refreshMedia={() => {
                      setRefreshMedia(!refreshMedia);
                    }} />
                  </HStack>
                  <HStack wrap={"wrap"}>
                    {socialMedia.length > 0 ? socialMedia.map((social) => (
                      <SocialMediaTag social={social} />
                    )) :
                      <Text>{t("Profile.noSocialMedia")}</Text>
                    }
                  </HStack>
                </VStack>
                <Divider marginY={6} />
                <VStack spacing={4} justifyItems="start">
                  <HStack w={'full'} justify={'space-around'}>
                    <HStack>
                      <HiUserGroup />
                      <Heading fontSize={"2xl"} fontWeight={500}>
                        {user?.band ? t("Profile.BandMembers") : t("Profile.playsIn")}
                      </Heading>
                    </HStack>
                    {user?.band && user?.id === currentUserId ?
                      <Button leftIcon={<AiOutlineEdit />} w={'50'} colorScheme={'cyan'} onClick={() => {
                        navigate("/profile/editAssociates")
                      }}>
                        {t("Profile.ViewEditAll")}
                      </Button>
                      :
                      <Button leftIcon={<GrView />} w={'50'} colorScheme={'cyan'} onClick={() => {
                        navigate("/profile/associates")
                      }}>
                        {t("Profile.ViewAll")}
                      </Button>}
                  </HStack>
                  <VStack w={'80%'}>
                    {memberships.length > 0 ?
                      memberships.map((m) => {
                        return (
                          <MembershipItem key={m.id} membershipId={m.id} contraUser={user?.band ? m.artist : m.band} description={m.description} roles={m.roles} refresh={() => {
                            setRefreshMembership(!refreshMembership);
                          }} />
                        )
                      })
                      :
                      <>{t("Profile.noMemberships")}</>
                    }
                  </VStack>
                </VStack>
              </GridItem>
            </Grid>
          </Stack>
        )}
      </Container>
      </Flex>
    </>);
};

export default Profile;
