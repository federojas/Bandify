import NavBar from "../NavBar";
// import Footer from "../../Footer";
import React from "react";
import { Outlet } from "react-router-dom";
// @ts-ignore
import { Box } from "@chakra-ui/react";
import { ReactNode } from "react";

const PageOrganizer = ({ children }: { children: ReactNode }) => {
  return (
    <Box>
      {children}
    </Box>
  );
};

const PageContainer = ({ children }: { children: ReactNode }) => {
  return <Box>{children}</Box>;
};

function MainLayout() {
  return (
    <>
      <NavBar />
      <PageOrganizer>
        <PageContainer>
          <Outlet />
        </PageContainer>
        {/* <Footer /> */}
      </PageOrganizer>
    </>
  );
}

export default MainLayout;
