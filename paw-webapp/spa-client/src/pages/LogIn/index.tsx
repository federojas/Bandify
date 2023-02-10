import React, { useEffect, useState } from "react";
import "../../styles/login.css";
import "../../styles/welcome.css";
import "../../styles/forms.css";
import "../../styles/alerts.css";
import "../../styles/forms.css";
import { loginService } from "../../services";
import { useForm } from "react-hook-form";
import { useTranslation } from "react-i18next";
import "../../common/i18n/index";
import AuthContext from "../../contexts/AuthContext";
import {Helmet} from "react-helmet";
import {
  Flex,
  Box,
  FormControl,
  FormLabel,
  Input,
  Checkbox,
  Stack,
  Link,
  Button,
  Heading,
  Text,
  useColorModeValue,
  FormErrorMessage,
  InputGroup,
  InputRightElement,
  useToast,
} from "@chakra-ui/react";
import { ViewIcon, ViewOffIcon } from "@chakra-ui/icons";
import { useNavigate, useLocation } from "react-router-dom";
import { serviceCall } from "../../services/ServiceManager";

interface FormData {
  email: string;
  password: string;
}

const LoginBox = () => {
  const { t } = useTranslation();
  const [showPassword, setShowPassword] = React.useState(false);
  const [invalidCredentials, setInvalidCredentials] = React.useState(false);
  const authContext = React.useContext(AuthContext);
  const navigate = useNavigate();
  const location = useLocation();
  const toast = useToast();

  useEffect(() => {
    if (authContext.isAuthenticated) {
      navigate("/auditions");
    }
  }, [authContext.isAuthenticated]);

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<FormData>();

  const onSubmit = async (data: FormData) => {
    const { email, password } = data;
    setInvalidCredentials(false);
    serviceCall(
      loginService.login(email, password),
      navigate
    ).then((response) => {
      if (response.hasFailed()) {
        toast({
          title: t("Login.error"),
          status: "error",
          description: t("Login.invalidCredentials"),
          isClosable: true,
        });
      } else {
        const headers: any = response.getData().headers

        if (response) authContext.login(headers['x-jwt'], headers['x-refresh-token'])
        navigate("/auditions", { replace: true });
      }
    })
  };


  const loginValidations = {
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
  };//TODO: ESTAS VALIDACIONES NO VAN ACA

  return (
    <>
      <Helmet>
        <title>{t("Login.login")}</title>
      </Helmet>
      <Flex
        align={"center"}
        justify={"center"}
        bg={useColorModeValue("gray.50", "gray.800")}
      >
        <Stack spacing={8} mx={"auto"} maxW={"lg"} py={12} px={6} minW={"40vw"}>
          <Stack align={"center"}>
            <Heading fontSize={"4xl"}>{t("Login.title")} ✌️</Heading>
          </Stack>
          <Box
            rounded={"lg"}
            bg={useColorModeValue("white", "gray.900")}
            boxShadow={"lg"}
            p={8}
          >
            <form onSubmit={handleSubmit(onSubmit)}>
              <Stack spacing={4}>
                <FormControl
                  id="email"
                  isRequired
                  isInvalid={Boolean(errors.email)}
                >
                  <FormLabel>{t("Login.email")}</FormLabel>
                  <Input
                    type="email"
                    {...register("email", loginValidations.email)}
                  />
                  <FormErrorMessage>{errors.email?.message}</FormErrorMessage>
                </FormControl>
                <FormControl
                  id="password"
                  isRequired
                  isInvalid={Boolean(errors.password)}
                >
                  <FormLabel>{t("Login.password")}</FormLabel>
                  <InputGroup>
                    <Input
                      type={showPassword ? "text" : "password"}
                      {...register("password", loginValidations.password)}
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
                <Stack spacing={10}>
                  <Stack
                    direction={{ base: "column", sm: "row" }}
                    align={"start"}
                    justify={"space-between"}
                  >
                    <Link color={"blue.400"} onClick={() => {
                      navigate("/resetPassword")
                    }}>
                      {t("Login.forgotPassword")}
                    </Link>
                  </Stack>
                  <Button
                    bg={"blue.400"}
                    color={"white"}
                    _hover={{
                      bg: "blue.500",
                    }}
                    type="submit"
                  >
                    {t("Login.login")}
                  </Button>
                </Stack>
              </Stack>
            </form>
          </Box>
        </Stack>
      </Flex>
    </>
    );
};

const Login = () => {
  return <LoginBox />;
};

export default Login;
