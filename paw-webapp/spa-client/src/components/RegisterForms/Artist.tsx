import "../../styles/forms.css";
import "../../styles/alerts.css";
import "../../js/register.js";
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
} from "@chakra-ui/react";
import { ViewIcon, ViewOffIcon } from "@chakra-ui/icons";
import { UserCreateInput } from "../../api/types/User";
import { userService } from "../../services";
import registerOptions from "./validations";
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

const RegisterArtistForm = () => {
  const { t } = useTranslation();
  const navigate = useNavigate();
  const location = useLocation();
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

  const onSubmit = async (data: FormData) => {
    const newUser: UserCreateInput = { ...data, band: false };
    console.log("hello there: "+ JSON.stringify(newUser));
    serviceCall(
      userService.getUsers(),
      navigate,
      (response) => {
        console.log("response:"+response)
      }
     ).then((response) => {
      console.log("response:"+response);
     }
      ).catch((error) => { console.log("error:"+error) });
    // const res = await userService.createNewUser(newUser);
    // console.log("🚀 ~ file: index.tsx:54 ~ onSubmit ~ res", res);
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
                  // maxLength={50}
                  {...register("name", registerOptions.name)}
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
                  {...register("surname", registerOptions.surname)}
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
              maxLength={255}
              {...register("email", registerOptions.email)}
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
                {...register("password", registerOptions.password)}
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
                  registerOptions.passwordConfirmation
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
              Already a user?{" "}
              <Link color={"blue.400"} href={"/login"}>
                Login
              </Link>
            </Text>
          </Stack>
        </Stack>
      </form>
    </Box>
  );
};

export default RegisterArtistForm;
