import PostCard from "../../components/PostCard/PostCard";
import { useTranslation } from "react-i18next";
import AuditionSearchBar from "../../components/SearchBars/AuditionSearchBar";
import { Audition } from "../../models";
import { Center, Divider, Flex, Heading, VStack } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { serviceCall } from "../../services/ServiceManager";
import { auditionService } from "../../services";
import { useNavigate } from "react-router-dom";

export default function AuditionsPage() {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const [auditions, setAuditions] = useState<Audition[]>([]);
 
  useEffect(() => {
    serviceCall(
      auditionService.getAuditions(),
      navigate,
      (response) => {
        console.log("🚀 ~ file: index.tsx:20 ~ useEffect ~ response", response)
        setAuditions(response)
      }
    )
  }, [])

  // const audition: Audition = {
  //   band: {
  //     name: "My Band",
  //     id: 1,
  //   },
  //   id: 1,
  //   creationDate: new Date(),
  //   title: "My Band is looking for a drummer ",
  //   roles: ["Drummer", "Guitarist", "Bassist", "Singer", "Keyboardist"],
  //   genres: ["Rock"],
  //   location: "Buenos Aires",
  // };

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
          {auditions.length > 0 ? auditions.map((audition) => (
            <PostCard {...audition} />
          )) : <Heading>No auditions found</Heading>  
          }
        </Flex>
      </VStack>
    </>
  );
}
