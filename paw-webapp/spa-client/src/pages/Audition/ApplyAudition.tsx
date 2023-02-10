import {
  useDisclosure,
  Button,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalCloseButton,
  ModalBody,
  FormControl,
  FormLabel,
  Textarea,
  ModalFooter,
  FormErrorMessage, useToast
} from "@chakra-ui/react";
import React from "react";
import { useForm } from "react-hook-form";
import { useTranslation } from "react-i18next";
import { FiUsers } from "react-icons/fi";
import { useNavigate } from "react-router-dom";
import { useAuditionService } from "../../contexts/AuditionService";
import { serviceCall } from "../../services/ServiceManager";
import { applyAuditionOptions, applyAuditionOptionsES } from "./validations";
import { Helmet } from "react-helmet";

interface FormData {
  message: string;
}


const ApplyButton = ({ auditionId, refresh }: { auditionId: number, refresh: () => void }) => {
  const { t } = useTranslation();
  const { isOpen, onOpen, onClose } = useDisclosure()
  const auditionService = useAuditionService();
  const initialRef = React.useRef(null)
  const finalRef = React.useRef(null)
  const navigate = useNavigate();
  const options = localStorage.getItem('i18nextLng') === 'es' ? applyAuditionOptionsES : applyAuditionOptions;
  const toast = useToast();

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<FormData>();


  const onSubmit = (data: FormData) => {
    serviceCall(auditionService.apply(auditionId, data.message),
      navigate
    ).then((response) => {
      if (response.hasFailed()) {
        toast({
          title: t("Audition.applyError"),
          status: "error",
          isClosable: true,
        });
      } else {
        toast({
          title: t("Audition.applySuccess"),
          description: t("Audition.applySuccessText"),
          status: "success",
          isClosable: true,
        });
        refresh();
        onClose();
      }
    })
  }

  return (
    <>
      <Helmet>
        <title>{t("Audition.apply")}</title>
      </Helmet>
      <Button leftIcon={<FiUsers />} w={'44'} colorScheme='green' onClick={onOpen}>{t("Audition.apply")}</Button>


      <Modal
        initialFocusRef={initialRef}
        finalFocusRef={finalRef}
        isOpen={isOpen}
        onClose={onClose}
      >
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>{t("Audition.Modal.title")}</ModalHeader>
          <ModalCloseButton />
          <ModalBody pb={6}>
            <form onSubmit={handleSubmit(onSubmit)}>
              <FormControl id="message"
                           isRequired
                           isInvalid={Boolean(errors.message)}>
                <FormLabel fontSize={16} fontWeight="bold">{t("Audition.Modal.message")}</FormLabel>
                <Textarea
                  placeholder={t("Audition.Modal.placeHolder")}
                  maxLength={options.message.maxLength.value}
                  {...register("message", options.message)}
                />
                <FormErrorMessage>{errors.message?.message}</FormErrorMessage>
              </FormControl>
              <ModalFooter>
                <Button colorScheme='blue' mr={3} type="submit">
                  {t("Audition.Modal.apply")}
                </Button>
                <Button onClick={onClose}>{t("Audition.Modal.cancel")}</Button>
              </ModalFooter>
            </form>
          </ModalBody>
        </ModalContent>
      </Modal>
    </>
  )
}

export default ApplyButton;