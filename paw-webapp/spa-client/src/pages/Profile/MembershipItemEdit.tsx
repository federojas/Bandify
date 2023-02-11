import { Avatar, Box, Flex, HStack, Text, VStack } from "@chakra-ui/react";
import { useNavigate } from "react-router-dom";
import RoleTag from "../../components/Tags/RoleTag";
import { User } from "../../models";
import EditMembershipButton from "./EditMembershipButton";
import DeleteMembershipButton from "./DeleteMembershipButton"

const MembershipItemEdit = ({ contraUser, description, roles, membershipId, refresh }: { contraUser: User, description: string, roles: string[], membershipId: number, refresh: () => void }) => {
  const navigate = useNavigate();

  return (
    <Box borderWidth='1px' borderRadius='lg' p="4" w={'full'}>
      <Flex direction={'row'} justify="space-between">
        <Flex direction={'column'} flex={3} justify="start">
          <HStack onClick={() => {
            navigate('/users/' + contraUser.id)
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

          <Flex direction={'column'} justify={'start'} alignItems={'start'} mt={5}>
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

        <Flex direction={'column'} flex={1} justify="space-around" align={'space-between'}>
          {/*<LeaveBand membershipId={membershipId as any} refresh={refresh} /> */}
          <EditMembershipButton membershipId={membershipId} rolesParam={roles} descriptionParam={description} refresh={refresh} />
          <DeleteMembershipButton membershipId={membershipId} refresh={refresh} />
        </Flex>
      </Flex>
    </Box>
  )
}

export default MembershipItemEdit