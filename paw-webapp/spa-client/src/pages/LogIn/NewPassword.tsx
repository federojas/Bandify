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
  useColorModeValue, useToast,
} from '@chakra-ui/react';
import {useContext, useState} from 'react';
import { useForm } from 'react-hook-form';
import { useTranslation } from 'react-i18next';
import {useNavigate} from 'react-router-dom';
import { useUserService } from '../../contexts/UserService';
import {UserPasswordResetInput} from "../../api/types/User";
import {serviceCall} from "../../services/ServiceManager";
import {getQueryOrDefault, useQuery} from "../../hooks/useQuery";
import {newPasswordOptions, newPasswordOptionsES} from "./validations";
import AuthContext from "../../contexts/AuthContext";

interface FormData {
  newPassword: string;
  newPasswordConfirmation: string;
}

const options = localStorage.getItem('i18nextLng') === 'es' ? newPasswordOptionsES : newPasswordOptions;

export default function NewPassword(): JSX.Element {
  const { t } = useTranslation();
  const [showPassword, setShowPassword] = useState(false);
  const [showPasswordConfirmation, setShowPasswordConfirmation] = useState(false);
  const userService = useUserService();
  const navigate = useNavigate();
  const toast = useToast();
  const query = useQuery();
  const token = getQueryOrDefault(query, "token" ,"");
  const auth  = useContext(AuthContext);

  const {
    register,
    handleSubmit,
    setError,
    formState: { errors },
  } = useForm<FormData>();

  const onSubmit = async (data: FormData) => {
    console.log("llegue")
    const input: UserPasswordResetInput = {
      newPassword: data.newPassword,
      newPasswordConfirmation: data.newPasswordConfirmation
    }
    serviceCall(
        userService.changeUserPassword(token, input),
        navigate
    ).then((response) => {
          if (response.hasFailed()) {
            toast({
              title: t("Register.error"),
              status: "error",
              description: t("ResetPassword.errorChange"),
              isClosable: true,
            });
          } else {
            toast({
              title: t("Register.success"),
              status: "success",
              description: t("ResetPassword.successChange"),
              isClosable: true,
            })
            auth.login(response.getData().headers.get("X-JWT"), response.getData().headers.get("X-Refresh-Token"))
            navigate("/auditions", {replace: true})
          }
        }
    ).catch((error) => { console.log("error:"+error) });
  };

  return (
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
            {t("NewPassword.Title")}
        </Heading>
        <FormControl
          id="password"
          isRequired
          isInvalid={Boolean(errors.newPassword)}
        >
          <FormLabel fontSize={16} fontWeight="bold">
            {t("Register.password")}
          </FormLabel>
          <InputGroup>
            <Input
              type={showPassword ? "text" : "password"}
              {...register("newPassword", options.newPassword)}
              placeholder={t("Register.pwd")}
              mb={4}
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
          <FormErrorMessage>{errors.newPassword?.message}</FormErrorMessage>
        </FormControl>
        <FormControl
          id="passwordConfirmation"
          isRequired
          isInvalid={Boolean(errors.newPasswordConfirmation)}
        >
          <FormLabel fontSize={16} fontWeight="bold">
            {t("Register.confirmPassword")}
          </FormLabel>
          <InputGroup>
            <Input
              type={showPasswordConfirmation ? "text" : "password"}
              placeholder={t("Register.pwd")}
              {...register(
                "newPasswordConfirmation",
                options.newPasswordConfirmation
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
            {errors.newPasswordConfirmation?.message}
          </FormErrorMessage>
        </FormControl>

        <Stack spacing={6}>
          <Button
            type="submit"
            bg={'blue.400'}
            color={'white'}
            _hover={{
              bg: 'blue.500',
            }}>
            {t("NewPassword.Submit")}
          </Button>
        </Stack>
      </Stack>
    </form>
    </Flex>
  );
}