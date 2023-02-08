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

interface FormData {
  roles: string[];
  description: string;
}

const SocialMediaModal = () => {
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
      roles: roles.map((role) => role.value),
      description: data.description,
    }
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
                      placeholder="www.example.com"
                      focusBorderColor="brand.400"
                      rounded="md"
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
                      placeholder="www.example.com"
                      focusBorderColor="brand.400"
                      rounded="md"
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
                      placeholder="www.example.com"
                      focusBorderColor="brand.400"
                      rounded="md"
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
                      placeholder="www.example.com"
                      focusBorderColor="brand.400"
                      rounded="md"
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
                      placeholder="www.example.com"
                      focusBorderColor="brand.400"
                      rounded="md"
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
                      placeholder="www.example.com"
                      focusBorderColor="brand.400"
                      rounded="md"
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