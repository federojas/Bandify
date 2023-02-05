
import React from "react";
import { useTranslation } from "react-i18next";
import "../../common/i18n/index";
import { getQueryOrDefault, useQuery } from "../../hooks/useQuery";
import { useNavigate } from "react-router-dom";
import { Container, Stack, Heading } from "@chakra-ui/react";

function Error() {
    const { t } = useTranslation();
    const navigate = useNavigate();
    const query = useQuery();

    let error = getQueryOrDefault(query, "code", "404");
    if (error === "NaN") {
        error = "404";
    }
    const image = require(`../../images/${error}.png`)
    return (
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
                    Error
                    <img src={image}/>
                </Heading>
                
            </Stack>
        </Container>
    );
}

export default Error;
