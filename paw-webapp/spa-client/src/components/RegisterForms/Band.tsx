import "../../styles/forms.css";
import "../../styles/alerts.css";
import "../../js/register.js";
import React from "react";
import { useTranslation } from "react-i18next";
import { useForm } from "react-hook-form";
import { useState } from "react";
import { ViewIcon, ViewOffIcon } from "@chakra-ui/icons";
import { UserCreateInput } from "../../api/types/User";
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

import {registerOptions, registerOptionsES} from "./validations";

import { serviceCall } from "../../services/ServiceManager";
import { useUserService } from "../../contexts/UserService";
import { useNavigate } from "react-router-dom";

interface FormData {
  email: string;
  password: string;
  passwordConfirmation: string;
  name: string;
}

const options = localStorage.getItem('i18nextLng') === 'es' ? registerOptionsES : registerOptions;

const RegisterBandForm = () => {
  const { t } = useTranslation();
  const [showPassword, setShowPassword] = useState(false);
  const [showPasswordConfirmation, setShowPasswordConfirmation] =useState(false);
  const userService = useUserService()
  const navigate = useNavigate()
  const {
    register,
    handleSubmit,
    setError,
    formState: { errors },
  } = useForm<FormData>();
  const toast = useToast()

  const onSubmit = async (data: FormData) => {
    const newUser: UserCreateInput = { ...data, band: true};
    serviceCall(
      userService.createUser(newUser),
      navigate
    ).then((response) => {
      if (response.hasFailed()) {
        toast({
          title: t("Register.error"),
          status: "error",
          description: t("Register.alreadyRegistered"),
          isClosable: true,
        });//todo: distintos mensajes?
      } else {
        navigate('/auditions', {replace: true}) //todo: redirect a auditions?
      }
    })
    
  };

  return (
    <Box
      rounded={"lg"}
      boxShadow={"lg"}
      p={8}
      w={'xl'}
      bg={useColorModeValue("gray.100", "gray.900")}
    >
      <form onSubmit={handleSubmit(onSubmit)}>
        <Stack spacing={4}>
          <FormControl id="name" isRequired isInvalid={Boolean(errors.name)}>
            <FormLabel fontSize={16} fontWeight="bold">
              {t("Register.band_name")}
            </FormLabel>
            <Input
              type="text"
              // maxLength={50}
              {...register("name", options.name)}
            />
            <FormErrorMessage>{errors.name?.message}</FormErrorMessage>
          </FormControl>

          <FormControl id="email" isRequired isInvalid={Boolean(errors.email)}>
            <FormLabel fontSize={16} fontWeight="bold">
              {t("Register.email")}
            </FormLabel>
            <Input
              type="email"
              maxLength={255}
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
              <Link color={"blue.400"} href={"/login"}>
              {t("Register.login")}
              </Link>
            </Text>
          </Stack>
        </Stack>
      </form>
    </Box>
  );
};

export default RegisterBandForm;
