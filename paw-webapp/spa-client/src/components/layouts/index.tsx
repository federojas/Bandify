import React from "react";
import { Outlet } from "react-router-dom";
import Footer from "../Footer";
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
