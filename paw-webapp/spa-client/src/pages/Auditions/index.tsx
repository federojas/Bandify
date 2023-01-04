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
    title: "My Band is looking for a drummer ",
    roles: ["Drummer", "Guitarist", "Bassist", "Singer", "Keyboardist"],
    genres: ["Rock"],
    location: "Buenos Aires",
  };

  return (
    <>
      <Center marginY={10} flexDirection="column">
        <VStack spacing={5} >
          <Heading fontSize={40}>{t("Auditions.header")}</Heading>
          <AuditionSearchBar />
        </VStack>
      </Center>
      <Divider />
      <VStack marginY={10} alignItems={"center"} spacing={4}>
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
