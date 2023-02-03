import RegisterBandForm from "../../components/RegisterForms/Band";
import { Flex, Heading, Stack, Text } from "@chakra-ui/react";
import { useTranslation } from "react-i18next";

const RegisterBand = () => {
  const { t } = useTranslation();
  return (
    <Flex minH={"100vh"} align={"center"} justify={"center"}>
      <Stack spacing={8} mx={"auto"} maxW={"lg"} py={12} px={6} align={'center'}>
        <Stack align={"center"}>
          <Heading fontSize={"4xl"} textAlign={"center"}>
            {t("RegisterBand.signup")}
          </Heading>
        </Stack>
        <RegisterBandForm />
      </Stack>
    </Flex>
  );
};

export default RegisterBand;
