import Footer from "../Footer";
import React from "react";
import { Outlet } from "react-router-dom";
import NavBar from "../NavBar";
import { PageOrganizer, PageContainer } from "./styles";

function MainLayout() {
  return (
    <PageOrganizer>
      <NavBar/>
      <PageContainer>
        <Outlet />
      </PageContainer>

      <Footer />
    </PageOrganizer>
  );
}

export default MainLayout;
