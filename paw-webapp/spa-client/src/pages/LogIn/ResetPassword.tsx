import {
  Button,
  FormControl,
  Flex,
  Heading,
  Input,
  Stack,
  Text,
  useColorModeValue, FormLabel, FormErrorMessage, useToast,
} from '@chakra-ui/react';
import { useTranslation } from 'react-i18next';
import {useForm} from "react-hook-form";
import {registerOptions, registerOptionsES} from "../../components/RegisterForms/validations";
import {UserPasswordResetRequestInput} from "../../api/types/User";
import {serviceCall} from "../../services/ServiceManager";
import { useNavigate } from "react-router-dom";
import {useUserService} from "../../contexts/UserService";
import {Helmet} from "react-helmet";

interface FormData {
  email: string;
}

export default function ForgotPasswordForm(): JSX.Element {
  const {t} = useTranslation();
  const options = localStorage.getItem('i18nextLng') === 'es' ? registerOptionsES : registerOptions
  const navigate = useNavigate();
  const toast = useToast();
  const userService = useUserService();

  const {
    register,
    handleSubmit,
    setError,
    formState: { errors },
  } = useForm<FormData>();

  const onSubmit = async (data: FormData) => {
    const input: UserPasswordResetRequestInput = {
      email: data.email
    }
    serviceCall(
        userService.generateUserPassword(input),
        navigate
    ).then((response) => {
          if (response.hasFailed()) {
            toast({
              title: t("Register.error"),
              status: "error",
              description: t("ResetPassword.error"),
              isClosable: true,
            });
          } else {
            toast({
              title: t("Register.success"),
              status: "success",
              description: t("ResetPassword.emailSent"),
              isClosable: true,
            })
            navigate('/resetPassword/emailSent',{
              state: {
                email: data.email,
              },
              replace: true
            })
          }
        }
    ).catch((error) => { console.log("error:"+error) });
  };

  return (
    <>
      <Helmet>
        <title>{t("ResetPassword.header")}</title>
      </Helmet>
      <Flex
        minH={'80vh'}
        align={'center'}
        justify={'center'}
      >
        <form onSubmit={handleSubmit(onSubmit)}>
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
          <FormControl id="email" isRequired isInvalid={Boolean(errors.email)}>
            <FormLabel fontSize={16} fontWeight="bold">
              {t("Register.email")}
            </FormLabel>
            <Input
              maxLength={255}
              {...register("email", options.email)}
              placeholder={t("ResetPassword.EmailPlaceholder")}
              _placeholder={{ color: 'gray.500' }}
              type="email"
            />
            <FormErrorMessage>{errors.email?.message}</FormErrorMessage>
          </FormControl>
          <Stack spacing={6}>
            <Button
              type="submit"
              bg={'blue.400'}
              color={'white'}
              _hover={{
                bg: 'blue.500',
              }}
            >
            {t("ResetPassword.RequestReset")}
            </Button>
          </Stack>
        </Stack>
        </form>
      </Flex>
      </>
  );
}