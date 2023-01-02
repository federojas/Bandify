import { Flex, Heading, Stack, Text } from "@chakra-ui/react";
import RegisterArtistForm from "../../components/RegisterForms/Artist";

const RegisterArtist = () => {
  return (
    <Flex minH={"100vh"} align={"center"} justify={"center"}>
      <Stack spacing={8} mx={'auto'} maxW={'lg'} py={12} px={6} align={'center'}>
          <Stack align={'center'}>
            <Heading fontSize={'4xl'} textAlign={'center'}>
              Sign up
            </Heading>
            <Text fontSize={'lg'} color={'gray.600'}>
              as an <Text as='u'>Artist</Text>  
            </Text>
          </Stack>
      <RegisterArtistForm />
      </Stack>
    </Flex>
  );
};

export default RegisterArtist;
