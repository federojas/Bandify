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
} from "@chakra-ui/react";
import { ViewIcon, ViewOffIcon } from "@chakra-ui/icons";

interface FormData {
  email: string;
  password: string;
  passwordConfirmation: string;
  name: string;
  surname: string;
}

const RegisterArtistForm = () => {
  const { t } = useTranslation();
  const [showPassword, setShowPassword] = useState(false);
  const [showPasswordConfirmation, setShowPasswordConfirmation] =
    useState(false);

  const {
    register,
    handleSubmit,
    setError,
    formState: { errors },
  } = useForm<FormData>();

  const onSubmit = (data: FormData) => {
    console.log(data);
  };

  const registerArtistOptions = {
    email: {
      required: "Email is required",
      maxLength: {
        value: 254,
        message: "Email cannot be larger than 254 characters",
      },
      pattern: /^\S+@\S+$/i,
    },
    password: {
      required: "Password is required",
      minLength: {
        value: 8,
        message: "Password must be at least 8 characters",
      },
      maxLength: {
        value: 25,
        message: "Password cannot be larger than 25 characters",
      },
    },
    passwordConfirmation: {
      required: "Password confirmation is required",
      minLength: {
        value: 8,
        message: "Password must be at least 8 characters",
      },
      maxLength: {
        value: 25,
        message: "Password cannot be larger than 25 characters",
      },
    },
    name: {
      required: "Name is required",
      maxLength: {
        value: 50,
        message: "Name cannot be larger than 50 characters",
      },
    },
    surname: {
      required: "Surname is required",
      maxLength: {
        value: 50,
        message: "Surname cannot be larger than 50 characters",
      },
    },
  };

  return (
    <Box rounded={"lg"} boxShadow={"lg"} p={8}>
      <form onSubmit={handleSubmit(onSubmit)}>
        <Stack spacing={4}>
          <HStack>
            <Box>
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
                  {...register("name", registerArtistOptions.name)}
                />
                <FormErrorMessage>{errors.name?.message}</FormErrorMessage>
              </FormControl>
            </Box>
            <Box>
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
                  {...register("surname", registerArtistOptions.surname)}
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
              {...register("email", registerArtistOptions.email)}
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
                {...register("password", registerArtistOptions.password)}
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
                  registerArtistOptions.passwordConfirmation
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
              Already a user? <Link color={"blue.400"} href={'/login'}>Login</Link>
            </Text>
          </Stack>
        </Stack>
      </form>
    </Box>
  );
};

export default RegisterArtistForm;
