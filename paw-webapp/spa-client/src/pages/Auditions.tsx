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
import AuditionCard from "../components/AuditionCard";

export default function AuditionsPage() {
  return (
    <>
      <Flex align={"center"} mt={8} direction={"row"}>
        <Box p={6}>
          <AuditionCard></AuditionCard>
        </Box>
        <Box p={6}>
          <AuditionCard></AuditionCard>
        </Box>
        <Box p={6}>
          <AuditionCard></AuditionCard>
        </Box>
        <Box p={6}>
          <AuditionCard></AuditionCard>
        </Box>
      </Flex>
    </>
  );
}
