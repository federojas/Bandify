import {
  Button,
  Flex,
  Heading,
  Image,
  Stack,
  Text,
  useBreakpointValue,
  Link,
} from "@chakra-ui/react";

export default function Home() {
  return (
    <Stack minH={"100vh"} direction={{ base: "column", md: "row" }}>
      {/* Guitar image */}
      <Flex flex={5}>
        <Image alt={"Login Image"} objectFit={"cover"} src={"welcome-2.png"} />
      </Flex>
      {/* Welcome actions (Discover, About us, Log, Register) */}
      <Flex p={8} flex={3} align={"center"} justify={"center"}>
        <Stack spacing={6} w={"full"} maxW={"lg"}>
          <Image alt={"Bandify icon"} src={"logo.png"} w={16} />

          <Heading fontSize={{ base: "3xl", md: "4xl", lg: "5xl" }}>
            <Text
              as={"span"}
              position={"relative"}
              _after={{
                content: "''",
                width: "full",
                height: useBreakpointValue({ base: "20%", md: "30%" }),
                position: "absolute",
                bottom: 1,
                left: 0,
                bg: "blue.400",
                zIndex: -1,
              }}
            >
              Connect with
            </Text>
            <br />{" "}
            <Text color={"blue.400"} as={"span"}>
              Artists & Bands
            </Text>{" "}
          </Heading>
          <Text fontSize={{ base: "md", lg: "lg" }} color={"gray.500"}>
            Join bands... or create your own!
          </Text>
          <Stack direction={{ base: "column", md: "column" }} spacing={4}>
            <Link href={"/signup"} >
              <Button
                rounded={"full"}
                bg={"blue.400"}
                color={"white"}
                _hover={{
                  bg: "blue.500",
                }}
              >
                Sign up with your email
              </Button>
            </Link>
            <Link href={"/login"}>
              <Button rounded={"full"}>Sign in</Button>
            </Link>
          </Stack>
        </Stack>
      </Flex>
    </Stack>
  );
}
