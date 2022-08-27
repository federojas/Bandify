import styled from "styled-components";

export const NavBarContainer = styled.div`
  width: 100%;
  background-color: #1c041c !important;
  padding-bottom: 10px !important;
  display: flex !important;
  align-items: center !important;
  height: 80px !important;
  justify-content: space-between !important;
  height: 80px !important;
  padding: 0 !important;
  padding-top: 10px !important;

  //   @media (min-width: 640px) {
  //     max-width: 640px;
  //   }
  //   @media (min-width: 768px) {
  //     max-width: 768px;
  //   }
  //   @media (min-width: 1024px) {
  //     max-width: 1024px;
  //   }
  //   @media (min-width: 1280px) {
  //     max-width: 1280px;
  //   }
  //   @media (min-width: 1536px) {
  //     max-width: 1536px;
  //   }
  //     TODO: ESTO NO FUNCA BIEN
`;
export const NavBarLogoSection = styled.a`
  display: flex;
  align-items: center;
  height: 40px !important;
  width: 40px !important;
  margin-left: 2rem;
`;
export const NavBarBandifyLogo = styled.span`
  align-self: center !important;
  line-height: 2rem !important;
  margin-left: 10px;
  font-weight: 600 !important;
  white-space: nowrap !important;
  --tw-text-opacity: 1 !important;
  color: rgb(255 255 255 / var(--tw-text-opacity)) !important;
  font-size: 1.75rem !important;
`;

export const NavBarItemList = styled.ul`
  display: flex;
  flex-direction: column;
  margin-top: 1rem;
  @media (min-width: 768px) {
    font-weight: 500;
    flex-direction: row;
    font-size: 0.875rem /* 14px */;
    line-height: 1.25rem /* 20px */;
    margin-top: 0px;
  }
`;

export const NavBarItem = styled.a`
  margin: 2rem;
  font-size: 23px;
  color: white;
`;
