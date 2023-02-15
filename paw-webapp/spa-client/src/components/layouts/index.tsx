import { Box, useColorModeValue } from "@chakra-ui/react";
import React from "react";
import { Outlet } from "react-router-dom";
import ContextProviderWrapper from "../../contexts/ContextProviderWrapper";
import Footer from "../Footer";
import Nav from "../NavBar";
import { PageOrganizer } from "./styles";



function MainLayout() {
  return (
    <ContextProviderWrapper>
      <PageOrganizer>
        <Nav />
        <Box as='main' bg={useColorModeValue("gray.50", "gray.800")}>
          <Outlet />
        </Box>
        <Footer />
      </PageOrganizer>
    </ContextProviderWrapper>
  );
}

export default MainLayout;
