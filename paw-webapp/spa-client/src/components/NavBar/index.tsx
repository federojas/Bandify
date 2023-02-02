//i18 translations
import React, {ReactElement, useEffect, useState} from "react";
import { useTranslation } from "react-i18next";
import { useLocation, useNavigate } from "react-router-dom";
import BandifyLogo from "../../images/logo.png";
// import "../../styles/navbar.css";
import "../../styles/profile.css";
import ManagerIcon from "../../assets/icons/manager.svg";
import LogoutIcon from "../../assets/icons/logout.svg";
import ProfileIcon from "../../assets/icons/user.svg";
import ToggleColorMode from "../ToggleColorMode";
import { ReactNode } from "react";
import {
  Box,
  Flex,
  Avatar,
  HStack,
  Link,
  IconButton,
  Button,
  Menu,
  MenuButton,
  MenuList,
  MenuItem,
  MenuDivider,
  useDisclosure,
  useColorModeValue,
  Stack,
  Image,
} from "@chakra-ui/react";
import { HamburgerIcon, CloseIcon, AddIcon } from "@chakra-ui/icons";
import { FiMusic, FiUsers } from "react-icons/fi";
import AuthContext from "../../contexts/AuthContext";
import { useContext } from "react";
import {serviceCall} from "../../services/ServiceManager";
import {useUserService} from "../../contexts/UserService";

const NavLink = ({
  children,
  link,
  icon,
}: {
  children: ReactNode;
  link: string;
  icon: ReactElement;
}) => {
  const location = useLocation();
  const toHighlight = location.pathname.startsWith(link);

  return (
    <Button
      as="a"
      px={3}
      py={1}
      rounded={"md"}
      variant={toHighlight ? "solid" : "ghost"}
      _hover={{
        textDecoration: "none",
        bg: useColorModeValue("gray.200", "gray.700"),
      }}
      href={link}
      leftIcon={icon}
    >
      {children}
    </Button>
  );
};

function Nav() {
  const { t } = useTranslation();
  const { isAuthenticated, logout, role, userId } = useContext(AuthContext);
  const isBand = role === "BAND";
  const navigate = useNavigate();
  const userService = useUserService();
  const [userImg, setUserImg] = useState<string | undefined>(undefined);
  const sections = [
    { path: "/auditions", name: t("NavBar.auditions"), icon: <FiMusic /> },
    { path: "/users", name: t("NavBar.discover"), icon: <FiUsers /> },
    // { path: "/newAudition", name: t("NavBar.post") },

    // TODO: PONER BIEN LOS LINKS DESPUES
  ];

  useEffect(() => {
    if(userId) {
      serviceCall(
          userService.getProfileImageByUserId(userId),
          navigate,
          (response) => {
            setUserImg(
                response
            )
          },
      )
    }
  }, [userId, navigate])

  const { isOpen, onOpen, onClose } = useDisclosure();

  return (
    <Box bg={useColorModeValue("gray.100", "gray.900")} px={4}>
      <Flex h={16} alignItems={"center"} justifyContent={"space-between"}>
        <IconButton
          size={"md"}
          width={10}
          icon={isOpen ? <CloseIcon /> : <HamburgerIcon />}
          aria-label={"Open Menu"}
          display={{ md: "none" }}
          onClick={isOpen ? onClose : onOpen}
        />
        <HStack spacing={8} alignItems={"center"}>
          <Box>
            <a href="/" className="logo-section">
              <HStack>
                <Image src={BandifyLogo} w={8} alt="Bandify" />
                <span className="bandify-title">bandify</span>
              </HStack>
            </a>
          </Box>
          <HStack as={"nav"} spacing={4} display={{ base: "none", md: "flex" }}>
            {sections.map((link) => (
              <NavLink key={link.name} link={link.path} icon={link.icon}>
                {link.name}
              </NavLink>
            ))}
          </HStack>
        </HStack>
        <Flex alignItems={"center"}>
          <ToggleColorMode />
          {isAuthenticated ? (
            <>
              {
                isBand &&
                <Button
                  as='a'
                  variant={"solid"}
                  colorScheme={"teal"}
                  size={"md"}
                  mx={4}
                  px={6}
                  leftIcon={<AddIcon />}
                  href={'/newAudition'}
                >
                  {t("NavBar.post")}
                </Button>
              }
              <Menu>
                <MenuButton
                  as={Button}
                  rounded={"full"}
                  variant={"link"}
                  cursor={"pointer"}
                  minW={0}
                >
                  <Avatar
                    size={"sm"}
                    src={`data:image/png;base64,${userImg}`} //TODO: revisar posible mejora a link
                  />
                </MenuButton>
                <MenuList>
                  <MenuItem as="a" href="/profile">
                    {t("NavBar.profileAlt")}
                  </MenuItem>
                  <MenuDivider />
                  <MenuItem onClick={() => logout()}>{t("NavBar.logoutAlt")}</MenuItem>
                </MenuList>
              </Menu>
            </>
          ) : (
            <Stack
              flex={{ base: 1, md: 0 }}
              justify={"flex-end"}
              direction={"row"}
              spacing={6}
            >
              <Button
                as={"a"}
                fontSize={"sm"}
                fontWeight={400}
                variant={"link"}
                href={"/login"}
              >
                {t("NavBar.login")}
              </Button>
              <Button
                display={{ base: "none", md: "inline-flex" }}
                fontSize={"sm"}
                fontWeight={600}
                color={"white"}
                bg={"blue.400"}
                _hover={{
                  bg: "blue.300",
                }}
                as={"a"}
                href={"/register"}
              >
                {t("NavBar.register")}
              </Button>
            </Stack>
          )}
        </Flex>
      </Flex>
      {isOpen ? (
        <Box pb={4} display={{ md: "none" }}>
          <Stack as={"nav"} spacing={4}>
            {sections.map((link) => (
              <NavLink key={link.name} link={link.path} icon={link.icon}>
                {link.name}
              </NavLink>
            ))}
          </Stack>
        </Box>
      ) : null}
    </Box>
    // <nav>
    //   <div className="nav-container nav-div">
    //     <a href="/" className="logo-section">
    //       <img src={BandifyLogo} className="bandify-logo" alt="Bandify" />
    //       <span className="bandify-title">bandify</span>
    //     </a>

    //     <div className="w-full md:block md:w-auto" id="mobile-menu">
    //       <ul>
    //         <ToggleColorMode />
    //         <li>
    //           <a
    //             href="/auditions"
    //             className="block py-2 pr-4 pl-3 text-white rounded text-2xl"
    //           >
    //             Auditions
    //           </a>
    //         </li>
    //         {/* isLogged && */}
    //         {
    //           <li>
    //             <a
    //               href="/users"
    //               className="block py-2 pr-4 pl-3 text-white rounded text-2xl"
    //             >
    //               Discover
    //             </a>
    //           </li>
    //         }
    //         {!isLogged && (
    //           <>
    //             <li>
    //               <a
    //                 href="/aboutUs"
    //                 className="block py-2 pr-4 pl-3 text-white rounded text-2xl"
    //               >
    //                 About Us
    //               </a>
    //             </li>
    //             {/* param.navItem !== 0 && param.navItem !== 5 && */}
    //             {
    //               <li>
    //                 <div className="flex">
    //                   <a href="/login" className="purple-login-button">
    //                     Login
    //                   </a>
    //                 </div>
    //               </li>
    //             }
    //           </>
    //         )}
    //         {/* TODO isLogged && */}
    //         {
    //           <>
    //             {/* hasRole('BAND') && */}
    //             {
    //               <li>
    //                 <a
    //                   href="/newAudition"
    //                   className="block py-2 pr-4 pl-3 text-white rounded text-2xl"
    //                 >
    //                   Post
    //                 </a>
    //               </li>
    //               // TODO: Add "Manager" con distinto href
    //             }
    //             {/* hasRole('ARTIST') && */}
    //             {
    //               <li>
    //                 <a
    //                   href="/profile/applications"
    //                   className="block py-2 pr-4 pl-3 text-white rounded text-2xl"
    //                 >
    //                   <img
    //                     src={ManagerIcon}
    //                     className="profile-icon-img"
    //                     alt="Manager"
    //                   />{" "}
    //                 </a>
    //               </li>
    //             }
    //             <li>
    //               <a
    //                 href="/profile"
    //                 className="block py-2 pr-4 pl-3 text-white rounded text-2xl"
    //               >
    //                 <img
    //                   src={ProfileIcon}
    //                   className="profile-icon-img"
    //                   alt="Profile"
    //                 />
    //               </a>
    //             </li>
    //             <li>
    //               <a
    //                 href="/logout"
    //                 className="block py-2 pr-4 pl-3 text-white rounded text-2xl"
    //               >
    //                 <img
    //                   src={LogoutIcon}
    //                   className="profile-icon-img"
    //                   alt="Logout"
    //                 />
    //               </a>
    //             </li>
    //           </>
    //         }
    //       </ul>
    //     </div>
    //   </div>
    // </nav>
  );
}

export default Nav;
