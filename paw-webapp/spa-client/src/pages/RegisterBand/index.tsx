import RegisterBandForm from "../../components/RegisterForms/Band";
import { Flex, Heading, Stack, Text } from "@chakra-ui/react";

const RegisterBand = () => {
  return (
    <Flex minH={"100vh"} align={"center"} justify={"center"}>
      <Stack spacing={8} mx={"auto"} maxW={"lg"} py={12} px={6}>
        <Stack align={"center"}>
          <Heading fontSize={"4xl"} textAlign={"center"}>
            Sign up
          </Heading>
          <Text fontSize={"lg"} color={"gray.600"}>
            as a <Text as="u">Band</Text>
          </Text>
        </Stack>
        <RegisterBandForm />
      </Stack>
    </Flex>
  );
};

export default RegisterBand;
