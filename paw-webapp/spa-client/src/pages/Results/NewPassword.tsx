import { ViewIcon, ViewOffIcon } from '@chakra-ui/icons';
import {
  Button,
  Flex,
  FormControl,
  FormErrorMessage,
  FormLabel,
  Heading,
  Input,
  InputGroup,
  Stack,
  InputRightElement,
  useColorModeValue,
} from '@chakra-ui/react';
import { useState } from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import { useNavigate } from 'react-router-dom';
import { useUserService } from '../../contexts/UserService';
import { registerOptions, registerOptionsES } from '../../components/RegisterForms/validations';

interface FormData {
  password: string;
  passwordConfirmation: string;
}

const options = localStorage.getItem('i18nextLng') === 'es' ? registerOptionsES : registerOptions;

export default function NewPassword(): JSX.Element {
  const { t } = useTranslation()
  const [showPassword, setShowPassword] = useState(false);
  const [showPasswordConfirmation, setShowPasswordConfirmation] = useState(false);
  const userService = useUserService()
  const navigate = useNavigate()

  const {
    register,
    handleSubmit,
    setError,
    formState: { errors },
  } = useForm<FormData>();

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
{t("NewPassword.Title")}        </Heading>
        <FormControl
          id="password"
          isRequired
          isInvalid={Boolean(errors.password)}
        >
          <FormLabel fontSize={16} fontWeight="bold">
            {t("Register.password")}
          </FormLabel>
          <InputGroup>
            <Input
              type={showPassword ? "text" : "password"}
              {...register("password", options.password)}
              placeholder={t("Register.pwd")}
            />
            <InputRightElement h={"full"}>
              <Button
                variant={"ghost"}
                onClick={() =>
                  setShowPassword((showPassword) => !showPassword)
                }
              >
                {showPassword ? <ViewIcon /> : <ViewOffIcon />}
              </Button>
            </InputRightElement>
          </InputGroup>
          <FormErrorMessage>{errors.password?.message}</FormErrorMessage>
        </FormControl>
        <FormControl
          id="passwordConfirmation"
          isRequired
          isInvalid={Boolean(errors.passwordConfirmation)}
        >
          <FormLabel fontSize={16} fontWeight="bold">
            {t("Register.confirmPassword")}
          </FormLabel>
          <InputGroup>
            <Input
              type={showPasswordConfirmation ? "text" : "password"}
              placeholder={t("Register.pwd")}
              {...register(
                "passwordConfirmation",
                options.passwordConfirmation
              )}
            />
            <InputRightElement h={"full"}>
              <Button
                variant={"ghost"}
                onClick={() =>
                  setShowPasswordConfirmation(
                    (showPasswordConfirmation) => !showPasswordConfirmation
                  )
                }
              >
                {showPasswordConfirmation ? <ViewIcon /> : <ViewOffIcon />}
              </Button>
            </InputRightElement>
          </InputGroup>
          <FormErrorMessage>
            {errors.passwordConfirmation?.message}
          </FormErrorMessage>
        </FormControl>
        <Stack spacing={6}>
          <Button
            bg={'blue.400'}
            color={'white'}
            _hover={{
              bg: 'blue.500',
            }}>
{t("NewPassword.Submit")}          </Button>
        </Stack>
      </Stack>
    </Flex>
  );
}