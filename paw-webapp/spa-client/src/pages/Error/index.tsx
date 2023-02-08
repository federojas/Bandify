
import React from "react";
import { useTranslation } from "react-i18next";
import "../../common/i18n/index";
import { getQueryOrDefault, useQuery } from "../../hooks/useQuery";
import { useNavigate } from "react-router-dom";
import { Container, Stack, Heading, Button } from "@chakra-ui/react";
import { Helmet } from "react-helmet";

function Error() {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const query = useQuery();
    const onCancel = () => {
        navigate(-1);
      };

    let error = getQueryOrDefault(query, "code", "404");
    if (error === "NaN") {
        error = "404";
    }
    const image = require(`../../images/${error}.png`)

    let message = "Not Found";
    if (error === '500') message = "Internal Server Error"
    else if (error === '403') message = "Forbidden";
    else if (error === '401') message = "Unauthorized";

    return (
        <>
        <Helmet>
          <title>{t("Login.error")}</title>
        </Helmet>
        <Container maxW={"5xl"}>
            <Stack
                textAlign={"center"}
                align={"center"}
                spacing={{ base: 8, md: 10 }}
                py={{ base: 20, md: 28 }}
            >
                <Heading
                    fontWeight={600}
                    fontSize={{ base: "3xl", sm: "4xl", md: "6xl" }}
                    lineHeight={"110%"}
                >
                    {message}
                </Heading>
                <img src={image} />
                <Button
                    onClick={onCancel}
                    bg={"blue.400"}
                    color={"white"}
                    _hover={{
                        bg: "blue.500",
                    }}
                    _focus={{
                        shadow: "",
                    }}
                    fontWeight="md"
                    mr={4}
                >
                    {t("Button.back")}
                </Button>
            </Stack>
        </Container>
    </>
    );
}

export default Error;
