import React, { useContext, useEffect, useState } from "react";
import "../../styles/profile.css";
import { useTranslation } from "react-i18next";
import {
  Box,
  Button,
  Container,
  Divider,
  Flex,
  FormControl,
  FormLabel,
  Grid,
  GridItem,
  Heading,
  HStack,
  Image,
  Modal,
  ModalBody,
  ModalCloseButton,
  ModalContent,
  ModalFooter,
  ModalHeader,
  ModalOverlay,
  Stack,
  Text,
  Textarea,
  useColorModeValue,
  useDisclosure,
  VStack,
  useToast,
  Avatar
} from "@chakra-ui/react";
import ArtistTag from "../../components/Tags/ArtistTag";
import BandTag from "../../components/Tags/BandTag";
import { ImLocation } from "react-icons/im";
import GenreTag from "../../components/Tags/GenreTag";
import RoleTag from "../../components/Tags/RoleTag";
import { HiUserGroup } from "react-icons/hi";
import AuthContext from "../../contexts/AuthContext";
import { serviceCall } from "../../services/ServiceManager";
import { useNavigate, useParams } from "react-router-dom";
import { User } from "../../models";
import { useUserService } from "../../contexts/UserService";
import { useMembershipService } from "../../contexts/MembershipService";
import { AiOutlineUserAdd } from "react-icons/ai";
import { FiMusic } from "react-icons/fi";
import { useForm } from "react-hook-form";
import { addToBandOptions, addToBandOptionsES } from "./validations";
import {
  Select, GroupBase,
} from "chakra-react-select";
import { RoleGroup } from "../EditProfile/EntitiesGroups";
import { useRoleService } from "../../contexts/RoleService";
import { GrView } from "react-icons/gr";
import Membership from "../../models/Membership";
import { Helmet } from "react-helmet";
import SocialMediaTag from "../Profile/SocialMediaTag";
import SocialMedia from "../../models/SocialMedia";
import LeaveBandButton from "./LeaveBandButton";

interface FormData {
  roles: string[];
  description: string;
}

const MembershipItem = ({ contraUser, description, roles }: { contraUser: User, description: string, roles: string[] }) => {
  const { t } = useTranslation();
  const navigate = useNavigate();

  return (
    <Box borderWidth='1px' borderRadius='lg' p="4" w={'full'}>
      <Flex direction={'column'} justify="space-between">
        <HStack onClick={() => {
          navigate('/user/' + contraUser.id)
        }}
          cursor={'pointer'}
        >
          <Avatar src={contraUser.profileImage}
            _dark={{
              backgroundColor: "white",
            }} />
          <Box ml='3'>
            <Text fontWeight='bold'>
              {contraUser.name}
              {
                contraUser.surname && ` ${contraUser.surname}`
              }
            </Text>
          </Box>
        </HStack>
        <Flex direction={'column'} justify={'space-between'} alignItems={'center'}>
          {roles.map((role) => {
            return (
              <RoleTag key={role} role={role} size="md" />
            )
          })}
          <Text as='i'>{description}</Text>
        </Flex>
      </Flex>
    </Box>
  )
}

const AddToBandButton = ({ user, refresh }: { user: User, refresh: () => void }) => {
  const { t } = useTranslation();
  const { isOpen, onOpen, onClose } = useDisclosure()
  const [roleOptions, setRoleOptions] = useState<RoleGroup[]>([]);
  const [roles, setRoles] = useState<RoleGroup[]>([]);
  const roleService = useRoleService();
  const membershipService = useMembershipService();
  const toast = useToast();
  const initialRef = React.useRef(null)
  const finalRef = React.useRef(null)
  const navigate = useNavigate();
  const options = localStorage.getItem('i18nextLng') === 'es' ? addToBandOptionsES : addToBandOptions;

  useEffect(() => {
    serviceCall(
      roleService.getRoles(),
      navigate,
      (roles) => {
        const roleAux: RoleGroup[] = roles.map((role) => {
          return { value: role.name, label: role.name };
        });
        setRoleOptions(roleAux);
      }
    )
  }, [])

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<FormData>();

  const isValidForm = (data: FormData) => {

    if (roles.length == 0) {
      toast({
        title: t("EditAudition.rolesRequired"),
        status: "error",
        duration: 9000,
        isClosable: true,
      });
      return false;
    }

    if (roles.length > 5) {
      toast({
        title: t("EditAudition.maxRoles"),
        status: "error",
        duration: 9000,
        isClosable: true,
      });
      return false;
    }

    return true;
  }


  const onSubmit = async (data: FormData) => {
    if (!isValidForm(data)) return;
    const input = {
      userId: user.id,
      roles: roles.map((role) => role.value),
      description: data.description,
    }
    serviceCall(
      membershipService.inviteToBand(input),
      navigate,
      (response) => {
      }
    ).then((r) => {
      if (r.hasFailed()) {
        toast({
          title: t("Invites.errorCreatingInvite"),
          description: t("Invites.errorCreatingInviteMessage"),
          status: "error",
          duration: 9000,
          isClosable: true,
        });
      } else {
        toast({
          title: t("Invites.successCreatingInvite"),
          status: "success",
          duration: 9000,
          isClosable: true,
        });
        refresh();
        onClose();
      }
    })
  }

  return (
    <>
      <Button leftIcon={<AiOutlineUserAdd />} w={'50'} colorScheme={'cyan'} onClick={onOpen}>
        {t("User.addToBand")}
      </Button>



      <Modal
        initialFocusRef={initialRef}
        finalFocusRef={finalRef}
        isOpen={isOpen}
        onClose={onClose}
      >
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>{t("AddToBand.Title")}</ModalHeader>
          <ModalCloseButton />
          <ModalBody pb={6}>
            <form onSubmit={handleSubmit(onSubmit)}>
              <Text fontSize={'lg'} mb={'4'}>{t("AddToBand.Subtitle")}<Text as='b'>{user.name}{' '}{user.surname}</Text> {t("AddToBand.Subtitle2")}</Text>
              <FormControl isRequired mb={'4'}>
                <FormLabel fontSize={16} fontWeight="bold">{t("AuditionSearchBar.role")}</FormLabel>
                <Select<RoleGroup, true, GroupBase<RoleGroup>>
                  isMulti

                  name="roles"
                  options={roleOptions}
                  placeholder={t("EditAudition.rolePlaceholder")}
                  closeMenuOnSelect={false}
                  variant="filled"
                  tagVariant="solid"
                  onChange={(event) => {
                    setRoles(event.flatMap((e) => e));
                  }}
                />
              </FormControl>

              <FormControl isRequired
              >
                <FormLabel fontSize={16} fontWeight="bold">{t("AddToBand.Field2")}</FormLabel>
                <Textarea
                  placeholder={t("AddToBand.Field2Placeholder")}
                  maxLength={options.message.maxLength.value}
                  {...register("description", options.message)}
                />
              </FormControl>
              <ModalFooter>
                <Button leftIcon={<AiOutlineUserAdd />} colorScheme='blue' type="submit">
                  {t("AddToBand.Add")}
                </Button>
              </ModalFooter>
            </form>
          </ModalBody>
        </ModalContent>
      </Modal>
    </>
  )
}

const UserProfile = () => {
  const { t } = useTranslation();
  const { id } = useParams();
  const userId = Number(id);
  const navigate = useNavigate();
  const [user, setUser] = React.useState<User>();
  const authContext = useContext(AuthContext);
  const currentUserId = Number(authContext.userId);
  const [currentUser, setCurrentUser] = React.useState<User>();
  const [canInvite, setCanInvite] = React.useState<Boolean>(false);
  const [canLeave, setCanLeave] = React.useState<Boolean>(false);
  const [membershipId, setMembershipId] = React.useState(0);
  const userService = useUserService();
  const membershipService = useMembershipService();
  const filterAvailable = require(`../../images/available.png`);
  const bg = useColorModeValue('white', 'gray.900')
  const [memberships, setMemberships] = React.useState<Membership[]>([]);
  const [socialMedia, setSocialMedia] = useState<SocialMedia[]>([]);
  const [refreshMemberships, setRefreshMemberships] = useState(false);

  const handleRefresh = () => {
    setRefreshMemberships(!refreshMemberships);
  }


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
    if (userId === currentUserId)
      navigate('/profile')
  }, [userId, currentUserId, navigate])

  useEffect(() => {
    if (user) {
      serviceCall(
        userService.getUserSocialMedia(user.id),
        navigate,
        (response: any) => {
          setSocialMedia(response);
        }
      )
    }
  }, [user, navigate])


  useEffect(() => {
    if (user && currentUser) {
      serviceCall(
        membershipService.getUserMemberships({ user: user.id as number, state: "ACCEPTED", preview: true }, 1),
        navigate,
        (response: any) => {
          setMemberships(response.getContent());
        }
      )
      if (!user.band && currentUser.band) {
        serviceCall(
          membershipService.getUserMembershipsByBand(user?.id, currentUser?.id),
          navigate,
          (response) => {
            if (response.getContent().length === 0) {
              setCanInvite(true);
            } else {
              setCanInvite(false);
            }
          }
        )
      } else if(user.band && !currentUser.band) {
        serviceCall(
            membershipService.getUserMembershipsByBand(currentUser?.id, user?.id),
            navigate,
            (response) => {
              if (response.getContent().length === 0) {
                setCanLeave(false);
              } else {
                if (response.getContent().at(0) && response.getContent().at(0)!.state === 'ACCEPTED') {
                  setMembershipId(response.getContent().at(0)!.id)
                  setCanLeave(true);
                }
              }
            }
        )
      }
    }
  }, [refreshMemberships, user, navigate, currentUser, canInvite, canLeave])

  return (
    <>
      <Helmet>
        <title>{t("NavBar.Profile")}</title>
      </Helmet>
      <Container maxW={"5xl"} px={"0"} py={8}>
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
                {!user?.band && canInvite && user?.available && currentUser?.band &&
                  <AddToBandButton user={user} refresh={handleRefresh} />
                }
                {user?.band && canLeave && !currentUser?.band &&
                  <LeaveBandButton membershipId={membershipId} refresh={handleRefresh} />
                }
                {user?.band &&
                  <>
                    <Button leftIcon={<FiMusic />} w={'50'} colorScheme={'cyan'} onClick={() => {
                      navigate("/user/" + userId + "/auditions")
                    }}>
                      {t("User.auditions")}
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
              // bg={useColorModeValue("gray.100", "gray.900")}
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
              // bg={useColorModeValue("gray.100", "gray.900")}
              bg={bg}

              rounded={"lg"}
              boxShadow={"lg"}
              p={6}
            >
              <VStack spacing={4} justifyItems="start">
                <Heading fontSize={"2xl"} fontWeight={500}>
                  {t("Profile.socialMedia")}
                </Heading>
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
                  <Button leftIcon={<GrView />} w={'50'} colorScheme={'cyan'} onClick={() => {
                    navigate("/user/" + userId + "/associates")
                  }}>
                    {t("Profile.ViewAll")}
                  </Button>
                </HStack>
                <VStack w={'80%'}>
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
              </VStack>
            </GridItem>
          </Grid>
        </Stack>
      </Container>
    </>
  );
};

export default UserProfile;
