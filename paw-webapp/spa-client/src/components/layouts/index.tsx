import React from "react";
import { Outlet } from "react-router-dom";
import Footer from "../Footer";
import Nav from "../NavBar";
import { PageOrganizer, PageContainer } from "./styles";

function MainLayout() {
  return (
    <PageOrganizer>
      <Nav/>
      <PageContainer>
        <Outlet />
      </PageContainer>

      <Footer />
    </PageOrganizer>
  );
}

export default MainLayout;
