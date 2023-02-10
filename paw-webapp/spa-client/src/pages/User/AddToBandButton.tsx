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

import { serviceCall } from "../../services/ServiceManager";
import { useNavigate, useParams } from "react-router-dom";
import { User } from "../../models";
import { useMembershipService } from "../../contexts/MembershipService";
import { AiOutlineUserAdd } from "react-icons/ai";
import { useForm } from "react-hook-form";
import { addToBandOptions, addToBandOptionsES } from "./validations";
import {
  Select, GroupBase,
} from "chakra-react-select";
import { RoleGroup } from "../EditProfile/EntitiesGroups";
import { useRoleService } from "../../contexts/RoleService";
import {TiCancel} from "react-icons/ti";

interface FormData {
  roles: string[];
  description: string;
}

const AddToBandButton = ({ user, auditionId, refresh }: { user: User, auditionId: number; refresh: () => void }) => {
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
      membershipService.createMembershipByApplication(input, auditionId),
      navigate,
      (response) => {
        console.log(response)
      }
    ).then((r) => {
      if (r.hasFailed()) {
        toast({
          title: t("Invites.errorCreatingMembership"),
          description: t("Invites.errorCreatingInviteMessage"),
          status: "error",
          duration: 9000,
          isClosable: true,
        });
      } else {
        toast({
          title: t("Invites.successCreatingMembership"),
          status: "success",
          duration: 9000,
          isClosable: true,
        });
        onClose();
        refresh();
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
                  // ref={initialRef}
                  placeholder={t("AddToBand.Field2Placeholder")}
                  maxLength={options.message.maxLength.value}
                  {...register("description", options.message)}
                />
              </FormControl>
              <ModalFooter>
                <Button leftIcon={<AiOutlineUserAdd />} colorScheme='blue' type="submit" mr={5}>
                  {t("AddToBand.Add")}
                </Button>
                <Button leftIcon={<TiCancel />} colorScheme='red' onClick={onClose} >{t("Profile.leaveModal.cancel")}</Button>
              </ModalFooter>
            </form>
          </ModalBody>
        </ModalContent>
      </Modal>
    </>
  )
}

export default AddToBandButton