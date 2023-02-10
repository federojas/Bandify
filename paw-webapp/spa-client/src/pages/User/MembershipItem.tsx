import { Avatar, Box, Flex, HStack, Text, VStack } from "@chakra-ui/react";
import { useNavigate } from "react-router-dom";
import RoleTag from "../../components/Tags/RoleTag";
import { User } from "../../models";

const MembershipItem = ({ contraUser, description, roles}: { contraUser: User, description: string, roles: string[], membershipId: number, refresh: () => void }) => {
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
        </VStack>
        <Flex direction={'column'} justify={'space-between'} alignItems={'center'}>
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

export default MembershipItem