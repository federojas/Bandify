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

import WelcomeGuitar from '../../images/welcome-guitar.png';

function Home() {
  return (
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
          Connect with nearby{" "}
          <Text as={"span"} color={"blue.400"}>
            bands & artists
          </Text>
        </Heading>
        <Text color={"gray.500"} maxW={"3xl"} fontSize={'2xl'}>
          Join bands... or create your own!
        </Text>
        <Stack spacing={6} direction={"row"}>
          <Button
            rounded={"full"}
            px={6}
            colorScheme={"blue"}
            bg={"blue.400"}
            _hover={{ bg: "blue.500" }}
            as='a'
            href={'/register'}
          >
            Get started
          </Button>
          <Button rounded={"full"} px={6} as='a' href={'/aboutUs'}>
            Learn more
          </Button>
        </Stack>
        {/* <Center>
          <Image src={WelcomeGuitar} />
        </Center> */}
      </Stack>
    </Container>
    // <div>
    //   {/* Hero */}
    //   <HeroContainer>
    //     {/* Hero text */}
    //     <HeroTitle>
    //       <Slogan1>Connect with nearby bands and artists</Slogan1>
    //       <Slogan2>Join bands... or create your own!</Slogan2>
    //     </HeroTitle>
    //   </HeroContainer>
    // </div>
  );
}

export default Home;
