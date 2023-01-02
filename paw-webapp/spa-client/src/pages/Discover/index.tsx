import { Center, Flex, Heading, VStack } from "@chakra-ui/react";
import React from "react";
import SearchForm from "../../components/DiscoverSearchBar";
import "../../styles/usersDiscover.css";
import { DiscoverBg } from "./styles";

const Discover = () => {
  return (
    <Center paddingTop={12}>
        <VStack spacing={4}>
        <Heading fontSize={"4xl"} fontWeight={"bold"}>
          Find artists and bands!
        </Heading>
        <SearchForm />
        </VStack>
    </Center>
  );
};

export default Discover;
