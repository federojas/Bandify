// import { HeroContainer, HeroTitle, Slogan1, Slogan2 } from "./styles";
import {
  Flex,
  Container,
  Heading,
  Stack,
  Text,
  Button,
  Icon,
  IconProps,
  Image,
  Center
} from "@chakra-ui/react";
import { Helmet } from "react-helmet";
import { useContext, useEffect } from "react";
import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import AuthContext from "../../contexts/AuthContext";
import WelcomeGuitar from '../../images/welcome-guitar.png';

function Home() {
  const { t } = useTranslation();
  const { isAuthenticated } = useContext(AuthContext)
  const navigate = useNavigate()

  useEffect(() => {
    if (isAuthenticated) {
      navigate('/auditions', { replace: true })
    }
  }, [isAuthenticated, navigate])

  return (
    <>
    <Helmet>
          <title>{t("Welcome.welcome")}</title>
    </Helmet>
    <Container maxW={"5xl"}>
      <Stack
        textAlign={"center"}
        align={"center"}
        spacing={{ base: 8, md: 10 }}
        py={{ base: 20, md: 28 }}
      >
        <Heading
          fontWeight={600}
          fontSize={{ base: "3xl", sm: "4xl", md: "6xl" }}
          lineHeight={"110%"}
        >
          {t("Welcome.connect")}
          <Text as={"span"} color={"blue.400"}>
            {t("Welcome.bandsArtists")}
          </Text>
        </Heading>
        <Text color={"gray.500"} maxW={"3xl"} fontSize={'2xl'}>
          {t("Welcome.join")}
        </Text>
        <Stack spacing={6} direction={"row"} >
          <Button
            rounded={"full"}
            px={6}
            colorScheme={"blue"}
            bg={"blue.400"}
            _hover={{ bg: "blue.500" }}
            as='a'
            href={'/register'}
          >
            {t("Welcome.getStarted")}
          </Button>
          
        </Stack>
      </Stack>
    </Container>
    </>
  );
}

export default Home;
