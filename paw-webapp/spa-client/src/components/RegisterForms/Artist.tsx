import "../../styles/forms.css";
import "../../styles/alerts.css";
import React from "react";
import { useTranslation } from "react-i18next";
import { useForm } from "react-hook-form";
import { useState } from "react";
import {
  Box,
  Button,
  FormControl,
  FormErrorMessage,
  FormLabel,
  HStack,
  Input,
  InputGroup,
  InputRightElement,
  Stack,
  Text,
  Link,
  useColorModeValue,
  useToast,
} from "@chakra-ui/react";
import { ViewIcon, ViewOffIcon } from "@chakra-ui/icons";
import { UserCreateInput } from "../../api/types/User";
import {registerOptions, registerOptionsES} from "./validations";
import { useLocation, useNavigate } from "react-router-dom";
import { useUserService } from "../../contexts/UserService";
import { serviceCall } from "../../services/ServiceManager";
 
interface FormData {
  email: string;
  password: string;
  passwordConfirmation: string;
  name: string;
  surname: string;
}
const options = localStorage.getItem('i18nextLng') === 'es' ? registerOptionsES : registerOptions;


export default function RegisterArtistForm (){
  const { t } = useTranslation();
  const navigate = useNavigate();
  const userService = useUserService();
  const [showPassword, setShowPassword] = useState(false);
  const [showPasswordConfirmation, setShowPasswordConfirmation] =
    useState(false);

  const {
    register,
    handleSubmit,
    setError,
    formState: { errors },
  } = useForm<FormData>();
  const toast = useToast()
  const onSubmit = async (data: FormData) => {
    if(data.password !== data.passwordConfirmation){
      setError("passwordConfirmation", {
        type: "manual",
        message: t("Register.passwordsDontMatch"),
      });
      return;
    }
    const newUser: UserCreateInput = { ...data, band: false };
    serviceCall(
      userService.createUser(newUser),
      navigate
     ).then((response) => {
      if (response.hasFailed()) {
        toast({
          title: t("Register.error"),
          status: "error",
          description: t("Register.errorRegister"),
          isClosable: true,
        });
      } else {
        toast({
          title: t("Register.success"),
          status: "success",
          description: t("Register.emailSent"),
          isClosable: true,
        })
        navigate('/emailSent', {
          state: {
            email: data.email,
          },
          replace: true
        })
      }
     }
      )
  };

  return (
    <Box
      rounded={"lg"}
      boxShadow={"lg"}
      p={8}
      w={'xl'}
      bg={useColorModeValue("white", "gray.900")}
    >
      <form onSubmit={handleSubmit(onSubmit)}>
        <Stack spacing={4}>
          <HStack>
            <Box flex={1}>
              <FormControl
                id="name"
                isRequired
                isInvalid={Boolean(errors.name)}
              >
                <FormLabel fontSize={16} fontWeight="bold">
                  {t("Register.artist_name")}
                </FormLabel>
                <Input
                  type="text"
                  maxLength={options.name.maxLength.value}
                  placeholder={t("EditAudition.titlePlaceholder")}
                  {...register("name", options.name)}
                />
                <FormErrorMessage>{errors.name?.message}</FormErrorMessage>
              </FormControl>
            </Box>
            <Box flex={1}>
              <FormControl
                id="surname"
                isRequired
                isInvalid={Boolean(errors.surname)}
              >
                <FormLabel fontSize={16} fontWeight="bold">
                  {t("Register.artist_surname")}
                </FormLabel>
                <Input
                  type="text"
                  maxLength={options.surname.maxLength.value}
                  placeholder={t("EditAudition.titlePlaceholder")}
                  {...register("surname", options.surname)}
                />
                <FormErrorMessage>{errors.surname?.message}</FormErrorMessage>
              </FormControl>
            </Box>
          </HStack>
          <FormControl id="email" isRequired isInvalid={Boolean(errors.email)}>
            <FormLabel fontSize={16} fontWeight="bold">
              {t("Register.email")}
            </FormLabel>
            <Input
              type="email"
              placeholder={t("Register.emailPlaceholder")}
              maxLength={options.email.maxLength.value}
              {...register("email", options.email)}
            />
            <FormErrorMessage>{errors.email?.message}</FormErrorMessage>
          </FormControl>
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

          <Stack spacing={10} pt={2}>
            <Button
              type="submit"
              loadingText="Submitting"
              size="lg"
              bg={"blue.400"}
              color={"white"}
              _hover={{
                bg: "blue.500",
              }}
            >
              {t("Register.button")}
            </Button>
          </Stack>
          <Stack pt={6}>
            <Text align={"center"}>
              {t("Register.alreadyUser")}{" "}
              <Link color={"blue.400"} onClick={() => {navigate('/login')}} >
                {t("Register.login")}
              </Link>
            </Text>
          </Stack>
        </Stack>
      </form>
    </Box>
  );
};

