import { Avatar, Box, Flex, HStack, Text, VStack } from "@chakra-ui/react";
import { useNavigate } from "react-router-dom";
import RoleTag from "../../components/Tags/RoleTag";
import { User } from "../../models";
import EditMembershipButton from "./EditMembershipButton";
import DeleteMembershipButton from"./DeleteMembershipButton"


// function LeaveBand({ membershipId, refresh }: { membershipId: number, refresh: () => void }) {
//     const { t } = useTranslation();
//     const { isOpen, onOpen, onClose } = useDisclosure();
//     const membershipService = useMembershipService();
//     const toast = useToast();
//     const navigate = useNavigate();
//
//
//     const handleAccept = (membershipId: number) => {
//
//         serviceCall(
//             membershipService.kickMember(membershipId),
//             navigate
//         ).then((response) => {
//             if (!response.hasFailed()) {
//                 toast({
//                     title: t("Profile.kickModal.success"),
//                     description: t("Profile.kickModal.successDescription"),
//                     status: "success",
//                     duration: 9000,
//                     isClosable: true,
//                 });
//
//             } else {
//                 toast({
//                     title: t("Profile.kickModal.error"),
//                     description: t("Profile.kickModal.errorDescription"),
//                     status: "error",
//                     duration: 9000,
//                     isClosable: true,
//                 });
//             }
//             refresh();
//             onClose()
//         });
//
//     }
//     return (
//         <>
//             <Button colorScheme='red' onClick={onOpen}>{t("Profile.leaveBand")}</Button>
//             <Modal isOpen={isOpen} onClose={onClose}>
//                 <ModalOverlay />
//                 <ModalContent>
//                     <ModalHeader>{t("Profile.leaveModal.title")}</ModalHeader>
//                     <ModalCloseButton />
//                     <ModalBody>
//                         <Text>{t("Profile.leaveModal.message")}</Text>
//                     </ModalBody>
//
//                     <ModalFooter>
//                         <Button leftIcon={<TiTick />} colorScheme='blue' mr={3} onClick={() => handleAccept(membershipId)}>
//                             {t("Profile.leaveModal.confirm")}
//                         </Button>
//                         <Button leftIcon={<TiCancel />} colorScheme='red' onClick={onClose} >{t("Profile.leaveModal.cancel")}</Button>
//                     </ModalFooter>
//                 </ModalContent>
//             </Modal>
//         </>
//     )
// }

const MembershipItemEdit = ({ contraUser, description, roles, membershipId, refresh }: { contraUser: User, description: string, roles: string[], membershipId: number, refresh: () => void }) => {
    const navigate = useNavigate();

    return (
        <Box borderWidth='1px' borderRadius='lg' p="4" w={'full'}>
            <Flex direction={'column'} justify="space-between">
                <VStack justify='space-between'>
                    <HStack onClick={() => {
                        navigate('/users/' + contraUser.id)
                    }}
                            cursor={'pointer'}
                    >
                        <Avatar src={contraUser.profileImage} //TODO: revisar ALT?
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
                    <Box>
                        {/*<LeaveBand membershipId={membershipId as any} refresh={refresh} /> */}
                        <EditMembershipButton membershipId={membershipId} rolesParam={roles} descriptionParam={description} refresh={refresh} />
                        <DeleteMembershipButton membershipId={membershipId} refresh={refresh} />
                    </Box>
                </VStack>
                <Flex direction={'column'} justify={'space-between'} alignItems={'center'} mt={5}>
                    <HStack>
                        {roles.map((role) => {
                            return (
                                <RoleTag key={role} role={role} size="md" />
                            )
                        })}
                    </HStack>
                    <Text as='i'>{description}</Text>
                </Flex>
            </Flex>
        </Box>
    )
}

export default MembershipItemEdit