import React, { useContext, useEffect, useState } from "react";
import "../../styles/profile.css";
import "../../styles/postCard.css";
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
  Grid,
  GridItem,
  Heading,
  HStack,
  Image,
  Stack,
  Tag,
  Text,
  useColorModeValue,
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
import { useNavigate } from "react-router-dom";
import { User } from "../../models";
import { useUserService } from "../../contexts/UserService";

const Profile = () => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const [user, setUser] = React.useState<User>();
  const [userImg, setUserImg] = useState<string | undefined>(undefined)
  const userService = useUserService();
  const [isLoading, setIsLoading] = useState(true);
  const { userId } = useContext(AuthContext);


  useEffect(() => {
    serviceCall(
      userService.getUserById(userId!),
      navigate,
      (response: any) => {
        setUser(response);
      }
    )
    serviceCall(
      userService.getProfileImageByUserId(Number(userId)),
      navigate,
      (response) => {
        setUserImg(
          response
        )
        setIsLoading(false)
      })
  }, [userService]);


  return (
    <Container maxW={"5xl"} px={"0"} py={8}>
      {isLoading ? <Center mt={'25%'}><span className="loader"></span></Center> : (
        <Stack spacing={4}>
          <Box
            w={"full"}
            // bg={useColorModeValue("gray.100", "gray.900")}
            bg="gray.100"
            _dark={{ bg: "gray.900" }}
            rounded={"lg"}
            boxShadow={"lg"}
            p={6}
          >
            <HStack gap={'8'}>
              { }
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
                }}
              />
              <VStack align={"left"} spacing={4}>
                <Box maxW={'xl'}>
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
          </Box>

          <Grid templateColumns={"repeat(5,1fr)"} gap={4}>
            <GridItem
              w={"full"}
              colSpan={2}
              // bg={useColorModeValue("gray.100", "gray.900")}
              bg="gray.100"
              _dark={{ bg: "gray.900" }}
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
              bg="gray.100"
              _dark={{ bg: "gray.900" }}
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
      )}
    </Container>
  );
};

export default Profile;
