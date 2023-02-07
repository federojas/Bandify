import {
  Button,
  FormControl,
  Flex,
  Heading,
  Input,
  Stack,
  Text,
  useColorModeValue,
} from '@chakra-ui/react';
import { useTranslation } from 'react-i18next';

type ForgotPasswordFormInputs = {
  email: string;
};

export default function ForgotPasswordForm(): JSX.Element {
  const {t} = useTranslation();

  return (
    <Flex
      minH={'80vh'}
      align={'center'}
      justify={'center'}
    >
      <Stack
        spacing={4}
        w={'full'}
        maxW={'md'}
        bg={useColorModeValue('white', 'gray.900')}
        rounded={'xl'}
        boxShadow={'lg'}
        p={6}
        my={12}>
        <Heading lineHeight={1.1} fontSize={{ base: '2xl', md: '3xl' }}>
          {t("ResetPassword.Title")}
        </Heading>
        <Text
          fontSize={{ base: 'sm', sm: 'md' }}
          color={useColorModeValue('gray.800', 'gray.400')}>
          {t("ResetPassword.Subtitle")}
        </Text>
        <FormControl id="email">
          <Input
            placeholder={t("ResetPassword.EmailPlaceholder")}

            _placeholder={{ color: 'gray.500' }}
            type="email"
          />
        </FormControl>
        <Stack spacing={6}>
          {/* TODO: agregar navigate a resetPassword/emailSent */}
          <Button
            bg={'blue.400'}
            color={'white'}
            _hover={{
              bg: 'blue.500',
            }}>
          {t("ResetPassword.RequestReset")}
          </Button>
        </Stack>
      </Stack>
    </Flex>
  );
}