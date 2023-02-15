import {
  Box,
  Button,
  Container,
  Stack,
  Text,
  useColorModeValue,
} from "@chakra-ui/react";
import { useTranslation } from "react-i18next";

export default function Footer() {
  const { t, i18n } = useTranslation();

  const handleLanguageChange = (language: string) => {
    i18n.changeLanguage(language);
  };

  const currentYear = new Date().getFullYear();

  return (
    <Box
      bg={useColorModeValue("white", "gray.900")}
      color={useColorModeValue("gray.700", "gray.200")}
    >
      <Container
        as={Stack}
        maxW={"6xl"}
        py={4}
        direction={{ base: "column", md: "row" }}
        spacing={4}
        justify={{ base: "center", md: "space-between" }}
        align={{ base: "center", md: "center" }}
      >
        <Text>© {currentYear} Copyright: Bandify</Text>
        <Text>
          <b>{t("Footer.contactUs")}: </b>
          <a href="mailto: bandifypaw@gmail.com">bandifypaw@gmail.com</a>
        </Text>
        <Stack direction={"row"} spacing={6}>
          <Button onClick={() => handleLanguageChange('es')}>ES</Button>
          <Button onClick={() => handleLanguageChange('en')}>EN</Button>
        </Stack>
      </Container>
    </Box>
  );
}
