import {
  Box,
  Button,
  Container,
  Stack,
  Text,
  useColorModeValue,
} from "@chakra-ui/react";
import { useTranslation } from "react-i18next";
import "../../styles/footer.css";

export default function Footer() {
  const { t, i18n } = useTranslation();

  const handleLanguageChange = (language: string) => {
    i18n.changeLanguage(language);
  };

  return (
    <Box
      bg={useColorModeValue("gray.100", "gray.900")}
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
        <Text>© 2022 Copyright: Bandify</Text>
        <Text>
          <b>{t("Footer.contactUs")}: </b>
          <a href="mailto: bandifypaw@gmail.com">bandifypaw@gmail</a>
        </Text>
        <Stack direction={"row"} spacing={6}>
          <Button onClick={() => handleLanguageChange('es')}>ES</Button>
          <Button onClick={() => handleLanguageChange('en')}>EN</Button>
        </Stack>
      </Container>
    </Box>
    // <footer className="page-footer">
    //   <div className="footer-copyright">
    //     <div className="flex flex-row justify-between px-10">
    //       © 2022 Copyright: Bandify
    //       <div>

    //       </div>
    //       <div>
    //         <a className="languages-buttons right" href="?lang=es">
    //           ES
    //         </a>
    //         <a className="languages-buttons right" href="?lang=en">
    //           EN
    //         </a>
    //       </div>
    //     </div>
    //   </div>
    // </footer>
  );
}
