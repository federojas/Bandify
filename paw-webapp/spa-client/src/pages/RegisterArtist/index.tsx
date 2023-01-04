import { Flex, Heading, Stack, Text } from "@chakra-ui/react";
import { useTranslation } from "react-i18next";
import RegisterArtistForm from "../../components/RegisterForms/Artist";

const RegisterArtist = () => {
  const { t } = useTranslation();
  return (
    <Flex minH={"100vh"} align={"center"} justify={"center"}>
      <Stack spacing={8} mx={'auto'} maxW={'lg'} py={12} px={6} align={'center'}>
          <Stack align={'center'}>
            <Heading fontSize={'4xl'} textAlign={'center'}>
              {t("RegisterArtist.signup")}
            </Heading>
            <Text fontSize={'lg'} color={'gray.600'}>
              {t("RegisterArtist.as")} <Text as='u'>{t("RegisterArtist.artist")}</Text>  
            </Text>
          </Stack>
      <RegisterArtistForm />
      </Stack>
    </Flex>
  );
};

export default RegisterArtist;
