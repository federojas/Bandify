import {
  Flex,
  Box,
  FormControl,
  FormLabel,
  Input,
  InputGroup,
  HStack,
  InputRightElement,
  Stack,
  Button,
  Heading,
  Text,
  useColorModeValue,
  Link,
} from "@chakra-ui/react";
import PostCard from "../components/PostCard/PostCard";

export default function AuditionsPage() {
  return (
    <>
      <Flex align={"center"} mt={8} direction={"row"}>
        <Box p={6}>
          <PostCard></PostCard>
        </Box>
        <Box p={6}>
          <PostCard></PostCard>
        </Box>
        <Box p={6}>
          <PostCard></PostCard>
        </Box>
        <Box p={6}>
          <PostCard></PostCard>
        </Box>
      </Flex>
    </>
  );
}
