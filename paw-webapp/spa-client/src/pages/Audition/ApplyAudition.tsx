import { useDisclosure, Button, Modal, ModalOverlay, ModalContent, ModalHeader, ModalCloseButton, ModalBody, FormControl, FormLabel, Textarea, ModalFooter } from "@chakra-ui/react";
import React from "react";
import { useForm } from "react-hook-form";
import { useTranslation } from "react-i18next";
import { FiUsers } from "react-icons/fi";
import { useNavigate } from "react-router-dom";
import { useAuditionService } from "../../contexts/AuditionService";
import { serviceCall } from "../../services/ServiceManager";
import { applyAuditionOptions, applyAuditionOptionsES } from "./validations";
import swal from 'sweetalert';

interface FormData {
  description: string;
}


const ApplyButton = ({ auditionId }: { auditionId: number }) => {
  const { t } = useTranslation();
  const { isOpen, onOpen, onClose } = useDisclosure()
  const auditionService = useAuditionService();
  const initialRef = React.useRef(null)
  const finalRef = React.useRef(null)
  const navigate = useNavigate();
  const options = localStorage.getItem('i18nextLng') === 'es' ? applyAuditionOptionsES : applyAuditionOptions;


  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<FormData>();


  const onSubmit = (data: FormData) => {
    serviceCall(auditionService.apply(auditionId, data.description),
      navigate
    ).then((response) => {//todo: distintos response messages segun el error
      if (response.hasFailed()) {
        swal({
          title: t("Audition.applyError"),
          icon: "error",
        })
      } else {
        swal({
          title: t("Audition.applySuccess"),
          text: t("Audition.applySuccessText"),
          icon: "success",
        })
      }
    })
  }

  return (
    <>
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
              <FormControl>
                <FormLabel>{t("Audition.Modal.message")}</FormLabel>
                <Textarea
                  // ref={initialRef}
                  placeholder={t("Audition.Modal.placeHolder")}
                  maxLength={options.message.maxLength.value}
                  {...register("description", options.message)}
                />
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