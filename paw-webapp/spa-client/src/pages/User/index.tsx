import React, { useContext, useEffect, useState } from "react";
import "../../styles/profile.css";
import UserIcon from "../../assets/icons/user.svg";
import EditIcon from "../../assets/icons/edit-white-icon.svg";
import AvailableCover from "../../assets/images/available.png";
import { useTranslation } from "react-i18next";
import {
  Box,
  Button,
  Center,
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
  Tag,
  Text,
  Textarea,
  useColorModeValue,
  useDisclosure,
  VStack,
} from "@chakra-ui/react";
import ArtistTag from "../../components/Tags/ArtistTag";
import BandTag from "../../components/Tags/BandTag";
import { ImLocation } from "react-icons/im";
import GenreTag from "../../components/Tags/GenreTag";
import RoleTag from "../../components/Tags/RoleTag";
import {
  FaFacebook,
  FaInstagram,
  FaTwitter,
  FaYoutube,
  FaSoundcloud,
  FaSpotify,
} from "react-icons/fa";
import AuthContext from "../../contexts/AuthContext";
import { serviceCall } from "../../services/ServiceManager";
import { useNavigate, useParams } from "react-router-dom";
import { User } from "../../models";
import { useUserService } from "../../contexts/UserService";
import { AiOutlineUserAdd } from "react-icons/ai";
import { FiMusic } from "react-icons/fi";
import { useForm } from "react-hook-form";
import { addToBandOptions, addToBandOptionsES } from "./validations";
import {
  Select, GroupBase,
} from "chakra-react-select";
import { RoleGroup } from "../EditProfile/EntitiesGroups";
import { useRoleService } from "../../contexts/RoleService";


interface FormData {
  roles: string[];
  description: string;
}


const AddToBandButton = ({ user }: { user: User }) => {
  const { t } = useTranslation();
  const { isOpen, onOpen, onClose } = useDisclosure()
  const [roleOptions, setRoleOptions] = useState<RoleGroup[]>([]);
  const [roles, setRoles] = useState<RoleGroup[]>([]);
  const roleService = useRoleService();

  // const auditionService = useAuditionService();
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


  const onSubmit = (data: FormData) => {
    console.log('hola')
    const input = {
      roles: roles.map((role) => role.value),
      description: data.description,
    }
    console.log(input)
    // serviceCall(auditionService.apply(auditionId, data.description),
    //   navigate
    // ).then((response) => {//todo: distintos response messages segun el error
    //   if (response.hasFailed()) {
    //     swal({
    //       title: t("Audition.applyError"),
    //       icon: "error",
    //     })
    //   } else {
    //     swal({
    //       title: t("Audition.applySuccess"),
    //       text: t("Audition.applySuccessText"),
    //       icon: "success",
    //     })
    //   }
    // })
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
                <FormLabel fontSize={16}  fontWeight="bold">{t("AuditionSearchBar.role")}</FormLabel>
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
                <FormLabel>{t("AddToBand.Field2")}</FormLabel>
                <Textarea
                  // ref={initialRef}
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
  const [userImg, setUserImg] = useState<string | undefined>(undefined)
  const userService = useUserService();
  const filterAvailable = require(`../../images/available.png`);
  const bg = useColorModeValue('white', 'gray.900')

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
  }, [])

  useEffect(() => {
    if (user) {
      serviceCall(
        userService.getProfileImageByUserId(user.id),
        navigate,
        (response) => {
          setUserImg(
            response
          )
        },
      )
    }
  }, [user, navigate])


  return (
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
            { }
            <HStack gap={'8'}>
              <Flex>
              <Image
                src={`data:image/png;base64,${userImg}`} //TODO: revisar posible mejora a link
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
              <VStack align={"left"} spacing={4}>
                <Box maxW={'lg'}>
                  <Heading fontSize={"3xl"} fontWeight={700}>
                    {user?.name}{" "}
                    {user?.surname && <>{user?.surname}</>}
                  </Heading>
                </Box>
                {user?.band ? <BandTag /> : <ArtistTag />}
                <Text color={"gray.500"} fontSize={"xl"}>
                  {user?.description}
                </Text>
                {
                  user?.location &&
                  <HStack>
                    <ImLocation />
                    <Text color={"gray.500"}> {user?.location}</Text>
                  </HStack>
                }
              </VStack>
            </HStack>
            <VStack justify={'center'}>

              {!user?.band && user?.available && currentUser?.band &&
                <AddToBandButton user={user} />
              }
              {user?.band &&

                <>
                  <Button leftIcon={<FiMusic />} w={'50'} colorScheme={'cyan'} onClick={() => {
                    // TODO: Logica de ver audiciones de X banda + esa vista
                    navigate('/profile/auditions')
                  }}>
                    {t("User.auditions")}
                  </Button>
                </>
              }
            </VStack>
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
              {/* TODO: socialMedia from user.socialMedia */}
              <HStack wrap={"wrap"}>
                <Button colorScheme={"facebook"} as="a" href={"#"}>
                  <FaFacebook />
                </Button>
                {/* Add Twitter, Instagram, Youtube, Soundcloud and Spotify */}
                <Button colorScheme={"twitter"} as="a" href={"#"}>
                  <FaTwitter />
                </Button>
                <Button colorScheme={"orange"} as="a" href={"#"}>
                  <FaInstagram />
                </Button>
                <Button colorScheme={"red"} as="a" href={"#"}>
                  <FaYoutube />
                </Button>
                <Button colorScheme={"orange"} as="a" href={"#"}>
                  <FaSoundcloud />
                </Button>
                <Button colorScheme={"yellow"} as="a" href={"#"}>
                  <FaSpotify />
                </Button>
              </HStack>
            </VStack>
            <Divider marginY={6} />
            <VStack spacing={4} justifyItems="start">
              <Heading fontSize={"2xl"} fontWeight={500}>
                {t("Profile.playsIn")}
              </Heading>
            </VStack>
          </GridItem>
        </Grid>
      </Stack>
    </Container>

  );
};

export default UserProfile;
