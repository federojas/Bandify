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
  Avatar,
  InputGroup,
  InputLeftAddon,
  Input
} from "@chakra-ui/react";
import { SlSocialFacebook, SlSocialInstagram, SlSocialTwitter, SlSocialYoutube, SlSocialSoundcloud, SlSocialSpotify } from "react-icons/sl";
import { serviceCall } from "../../services/ServiceManager";
import { useNavigate, useParams } from "react-router-dom";
import { User } from "../../models";
import { useMembershipService } from "../../contexts/MembershipService";
import { AiOutlineEdit, AiOutlineUserAdd } from "react-icons/ai";
import { useForm } from "react-hook-form";
import { addToBandOptions, addToBandOptionsES } from "./validations";
import {
  Select, GroupBase,
} from "chakra-react-select";
import { RoleGroup } from "../EditProfile/EntitiesGroups";
import { useRoleService } from "../../contexts/RoleService";
import { GrView } from "react-icons/gr";
import { UpdateUserSocialMediaInput } from "../../api/types/SocialMedia";
import { useUserService } from "../../contexts/UserService";
import useAuth from "../../hooks/useAuth";

interface FormData {
  twitterUrl?: string;
  spotifyUrl?: string;
  instagramUrl?: string;
  facebookUrl?: string;
  youtubeUrl?: string;
  soundcloudUrl?: string;
}

const SocialMediaModal = ({refreshMedia}: {refreshMedia: Function}) => {
  const { t } = useTranslation();
  const { isOpen, onOpen, onClose } = useDisclosure()
  const userService = useUserService();
  const toast = useToast();
  const initialRef = React.useRef(null)
  const finalRef = React.useRef(null)
  const navigate = useNavigate();
  const { userId } = useAuth()
  const options = localStorage.getItem('i18nextLng') === 'es' ? addToBandOptionsES : addToBandOptions;
  const [socialMedia, setSocialMedia] = useState<FormData>({})

  useEffect(() => {
    serviceCall(
      userService.getUserSocialMedia(userId!),
      navigate,
      (response) => {
        response.forEach((social) => {
          switch (social.type) {
            case "FACEBOOK": {
              setSocialMedia((prev) => {
                if (prev.facebookUrl) return prev;
                const urlCleaned = social.url.replace('https://', '')

                return { ...prev, facebookUrl: urlCleaned }
              })
              break;
            }
            case "TWITTER": {
              setSocialMedia((prev) => {
                if (prev.twitterUrl) return prev;
                const urlCleaned = social.url.replace('https://', '')

                return { ...prev, twitterUrl: urlCleaned }
              })
              break;
            }
            case "INSTAGRAM": {
              setSocialMedia((prev) => {
                if (prev.instagramUrl) return prev;
                const urlCleaned = social.url.replace('https://', '')
                return { ...prev, instagramUrl: urlCleaned }
              })
              break;
            }
            case "YOUTUBE": {
              setSocialMedia((prev) => {
                if (prev.youtubeUrl) return prev;
                const urlCleaned = social.url.replace('https://', '')

                return { ...prev, youtubeUrl: urlCleaned }
              })
              break;
            }
            case "SOUNDCLOUD": {
              setSocialMedia((prev) => {
                if (prev.soundcloudUrl) return prev;
                const urlCleaned = social.url.replace('https://', '')
                return { ...prev, soundcloudUrl: urlCleaned }
              })
              break;
            }
            case "SPOTIFY": {
              setSocialMedia((prev) => {
                if (prev.spotifyUrl) return prev;
                const urlCleaned = social.url.replace('https://', '')

                return { ...prev, spotifyUrl: urlCleaned }
              })
              break;
            }
            default: {
              return
            }
          }
        }, {})
      }
    )
  }, [])

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<FormData>();

  const onSubmit = async (data: FormData) => {

    const input: UpdateUserSocialMediaInput = {
      twitterUrl: data.twitterUrl ? data.twitterUrl : undefined,
      spotifyUrl: data.spotifyUrl ? data.spotifyUrl : undefined,
      instagramUrl: data.instagramUrl ? data.instagramUrl : undefined,
      facebookUrl: data.facebookUrl ? data.facebookUrl : undefined,
      youtubeUrl: data.youtubeUrl ? data.youtubeUrl : undefined,
      soundcloudUrl: data.soundcloudUrl ? data.soundcloudUrl : undefined,
    }

    const inputCleaned = Object.entries(input).filter(
      ([key, value]) => value !== undefined
    ).reduce(
      (obj, [key, value]) => ({ ...obj, [key]: 'https://' + value }), {})

    serviceCall(
      userService.updateUserSocialMedia(userId!, inputCleaned),
      navigate,
      (response) => {
        toast({
          title: t("SocialMediaModal.Success"),
          description: t("SocialMediaModal.SuccessDescription"),
          status: "success",
          duration: 9000,
          isClosable: true,
        })
        refreshMedia()
        onClose();
      }
    ).then((response) => {
      if(response.hasFailed()){
        toast({
          title: t("SocialMediaModal.Error"),
          status: "error",
          description: t("SocialMediaModal.ErrorDescription"),
          isClosable: true,
        })
      }
    })
  }

  return (
    <>
      <Button leftIcon={<AiOutlineEdit />} w={'50'} colorScheme={'cyan'} onClick={onOpen}>
        {t("SocialMediaModal.title")}
      </Button>

      <Modal
        initialFocusRef={initialRef}
        finalFocusRef={finalRef}
        isOpen={isOpen}
        onClose={onClose}
      >
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>
            {t("SocialMediaModal.title")}
          </ModalHeader>
          <ModalCloseButton />
          <ModalBody pb={6}>
            <form onSubmit={handleSubmit(onSubmit)}>
              <Text fontSize={'lg'} mb={'4'}>{t("SocialMediaModal.Subtitle")}<Text as='b'></Text> {t("AddToBand.Subtitle2")}</Text>
              <VStack gap={2}>

                <FormControl>
                  <HStack>
                    <SlSocialFacebook />
                    <FormLabel
                      fontSize="sm"
                      fontWeight="md"
                      color="gray.700"
                      _dark={{
                        color: 'gray.50',
                      }}>
                      {t("SocialMediaModal.Facebook")}
                    </FormLabel>
                  </HStack>
                  <InputGroup size="sm" marginTop={2}>
                    <InputLeftAddon
                      bg="gray.50"
                      _dark={{
                        bg: 'gray.800',
                      }}
                      color="gray.500"
                      rounded="md">
                      https://
                    </InputLeftAddon>
                    <Input
                      type="tel"
                      placeholder="www.facebook.com"
                      focusBorderColor="brand.400"
                      rounded="md"
                      defaultValue={socialMedia.facebookUrl}
                      {...register("facebookUrl")}
                    />
                  </InputGroup>
                </FormControl>
                <FormControl>
                  <HStack>
                    <SlSocialInstagram />
                    <FormLabel
                      fontSize="sm"
                      fontWeight="md"
                      color="gray.700"
                      _dark={{
                        color: 'gray.50',
                      }}>
                      {t("SocialMediaModal.Instagram")}
                    </FormLabel>
                  </HStack>
                  <InputGroup size="sm" marginTop={2}>
                    <InputLeftAddon
                      bg="gray.50"
                      _dark={{
                        bg: 'gray.800',
                      }}
                      color="gray.500"
                      rounded="md">
                      https://
                    </InputLeftAddon>
                    <Input
                      type="tel"
                      placeholder="www.instagram.com"
                      focusBorderColor="brand.400"
                      rounded="md"
                      defaultValue={socialMedia.instagramUrl}
                      {...register("instagramUrl")}
                    />
                  </InputGroup>
                </FormControl>
                <FormControl>
                  <HStack>
                    <SlSocialYoutube />
                    <FormLabel
                      fontSize="sm"
                      fontWeight="md"
                      color="gray.700"
                      _dark={{
                        color: 'gray.50',
                      }}>
                      {t("SocialMediaModal.Youtube")}
                    </FormLabel>
                  </HStack>
                  <InputGroup size="sm" marginTop={2}>
                    <InputLeftAddon
                      bg="gray.50"
                      _dark={{
                        bg: 'gray.800',
                      }}
                      color="gray.500"
                      rounded="md">
                      https://
                    </InputLeftAddon>
                    <Input
                      type="tel"
                      placeholder="www.youtube.com"
                      focusBorderColor="brand.400"
                      rounded="md"
                      defaultValue={socialMedia.youtubeUrl}
                      {...register("youtubeUrl")}
                    />
                  </InputGroup>
                </FormControl>
                <FormControl>
                  <HStack>
                    <SlSocialTwitter />
                    <FormLabel
                      fontSize="sm"
                      fontWeight="md"
                      color="gray.700"
                      _dark={{
                        color: 'gray.50',
                      }}>
                      {t("SocialMediaModal.Twitter")}
                    </FormLabel>
                  </HStack>
                  <InputGroup size="sm" marginTop={2}>
                    <InputLeftAddon
                      bg="gray.50"
                      _dark={{
                        bg: 'gray.800',
                      }}
                      color="gray.500"
                      rounded="md">
                      https://
                    </InputLeftAddon>
                    <Input
                      type="tel"
                      placeholder="twitter.com"
                      focusBorderColor="brand.400"
                      rounded="md"
                      defaultValue={socialMedia.twitterUrl}
                      {...register("twitterUrl")}
                    />
                  </InputGroup>
                </FormControl>
                <FormControl>
                  <HStack>
                    <SlSocialSoundcloud />
                    <FormLabel
                      fontSize="sm"
                      fontWeight="md"
                      color="gray.700"
                      _dark={{
                        color: 'gray.50',
                      }}>
                      {t("SocialMediaModal.Soundcloud")}
                    </FormLabel>
                  </HStack>
                  <InputGroup size="sm" marginTop={2}>
                    <InputLeftAddon
                      bg="gray.50"
                      _dark={{
                        bg: 'gray.800',
                      }}
                      color="gray.500"
                      rounded="md">
                      https://
                    </InputLeftAddon>
                    <Input
                      type="tel"
                      placeholder="open.spotify.com"
                      focusBorderColor="brand.400"
                      rounded="md"
                      defaultValue={socialMedia.soundcloudUrl}
                      {...register("soundcloudUrl")}
                    />
                  </InputGroup>
                </FormControl>
                <FormControl>
                  <HStack>
                    <SlSocialSpotify />
                    <FormLabel
                      fontSize="sm"
                      fontWeight="md"
                      color="gray.700"
                      _dark={{
                        color: 'gray.50',
                      }}>
                      {t("SocialMediaModal.Spotify")}
                    </FormLabel>
                  </HStack>
                  <InputGroup size="sm" marginTop={2}>
                    <InputLeftAddon
                      bg="gray.50"
                      _dark={{
                        bg: 'gray.800',
                      }}
                      color="gray.500"
                      rounded="md">
                      https://
                    </InputLeftAddon>
                    <Input
                      type="tel"
                      placeholder="soundcloud.com"
                      focusBorderColor="brand.400"
                      rounded="md"
                      defaultValue={socialMedia.spotifyUrl}
                      {...register("spotifyUrl")}
                    />
                  </InputGroup>
                </FormControl>
              </VStack>

              <ModalFooter>
                <Button colorScheme='blue' type="submit">
                  {t("SocialMediaModal.Save")}
                </Button>
              </ModalFooter>
            </form>
          </ModalBody>
        </ModalContent>
      </Modal>
    </>
  )
}

export default SocialMediaModal