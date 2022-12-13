import React from "react";
import { Outlet } from "react-router-dom";
import ContextProviderWrapper from "../../contexts/ContextProviderWrapper";
import Footer from "../Footer";
import Nav from "../NavBar";
import { PageOrganizer, PageContainer } from "./styles";

function MainLayout() {
  return (
    <ContextProviderWrapper>
      <PageOrganizer>
        <Nav />
        <PageContainer>
          <Outlet />
        </PageContainer>

        <Footer />
      </PageOrganizer>
    </ContextProviderWrapper>
  );
}

export default MainLayout;
