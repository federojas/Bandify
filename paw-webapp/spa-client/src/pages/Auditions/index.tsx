import PostCard from "../../components/PostCard/PostCard";
import { useTranslation } from "react-i18next";
import AuditionSearchBar from "../../components/AuditionSearchBar";
import { Audition } from "../../types";
import { Center, Divider, Flex, Heading, VStack } from "@chakra-ui/react";

export default function AuditionsPage() {
  const { t } = useTranslation();
  const audition: Audition = {
    band: {
      name: "My Band",
      id: 1,
    },
    id: 1,
    creationDate: new Date(),
    title: "My Band is looking for a drummer",
    roles: ["Drummer"],
    genres: ["Rock"],
    location: "Buenos Aires",
  };

  return (
    <>
      <Center flexDirection="column">
        <VStack spacing={5} marginTop={10}>
          <Heading fontSize={40}>Search for Auditions 🎸</Heading>
          <AuditionSearchBar />
        </VStack>
      </Center>
      <Divider marginY={10} />
      <VStack alignItems={"center"} spacing={10}>
        <Heading>{t("Auditions.latest")}</Heading>

        <Flex
          direction={"row"}
          wrap={"wrap"}
          margin={2}
          justifyContent={"space-around"}
        >
          <PostCard {...audition} />
          <PostCard {...audition} />
          <PostCard {...audition} />
          <PostCard {...audition} />
        </Flex>
      </VStack>
    </>
  );
}
