import {useTranslation} from "react-i18next";
import {
    Button,
    Modal, ModalBody,
    ModalCloseButton,
    ModalContent, ModalFooter,
    ModalHeader,
    ModalOverlay, Text,
    useDisclosure,
    useToast
} from "@chakra-ui/react";
import {useMembershipService} from "../../contexts/MembershipService";
import {useNavigate} from "react-router-dom";
import {serviceCall} from "../../services/ServiceManager";
import {TiCancel, TiTick} from "react-icons/ti";
import React from "react";
import {AiOutlineCloseCircle} from "react-icons/ai";

const LeaveBandButton = ({ membershipId, refresh }: { membershipId: number, refresh: () => void }) => {
    const { t } = useTranslation();
    const { isOpen, onOpen, onClose } = useDisclosure();
    const membershipService = useMembershipService();
    const toast = useToast();
    const navigate = useNavigate();
    const initialRef = React.useRef(null)
    const finalRef = React.useRef(null)


    const handleAccept = (membershipId: number) => {

        serviceCall(
            membershipService.kickMember(membershipId),
            navigate
        ).then((response) => {
            if (response.hasFailed()) {

                toast({
                    title: t("Profile.kickModal.error"),
                    description: t("Profile.leaveModal.errorDescription"),
                    status: "error",
                    duration: 9000,
                    isClosable: true,
                });

            } else {
                toast({
                    title: t("Profile.leaveModal.success"),
                    description: t("Profile.leaveModal.successDescription"),
                    status: "success",
                    duration: 9000,
                    isClosable: true,
                });
                refresh();
                onClose()
            }
        });

    }
    return (
        <>
            <Button leftIcon={<AiOutlineCloseCircle />} w={'50'} colorScheme='red' onClick={onOpen}>{t("Profile.leaveBand")}</Button>
            <Modal initialFocusRef={initialRef} finalFocusRef={finalRef} isOpen={isOpen} onClose={onClose}>
                <ModalOverlay />
                <ModalContent>
                    <ModalHeader>{t("Profile.leaveModal.title")}</ModalHeader>
                    <ModalCloseButton />
                    <ModalBody>
                        <Text>{t("Profile.leaveModal.message")}</Text>
                    </ModalBody>

                    <ModalFooter>
                        <Button leftIcon={<TiTick />} colorScheme='blue' mr={3} onClick={() => handleAccept(membershipId)}>
                            {t("Profile.leaveModal.confirm")}
                        </Button>
                        <Button leftIcon={<TiCancel />} colorScheme='red' onClick={onClose} >{t("Profile.leaveModal.cancel")}</Button>
                    </ModalFooter>
                </ModalContent>
            </Modal>
        </>
    )
}

export default LeaveBandButton