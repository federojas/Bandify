//i18 translations
import React, { ReactElement, useEffect, useState } from "react";
import { useTranslation } from "react-i18next";
import { useLocation, useNavigate } from "react-router-dom";
import BandifyLogo from "../../images/logo.png";
import "../../styles/profile.css";
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
import { FiLogOut, FiMusic, FiUsers } from "react-icons/fi";
import AuthContext from "../../contexts/AuthContext";
import { useContext } from "react";
import { serviceCall } from "../../services/ServiceManager";
import { useUserService } from "../../contexts/UserService";
import { RiAppsFill } from "react-icons/ri";
import { User } from "../../models";
import { FaUserCircle } from "react-icons/fa";
import { BiUser } from "react-icons/bi";
const NavLink = ({
  children,
  link,
  icon,
}: {
  children?: ReactNode;
  link: string;
  icon: ReactElement;
}) => {
  const location = useLocation();
  const toHighlight = location.pathname.startsWith(link);
  const navigate = useNavigate();
  return (
    <Button
      cursor={'pointer'}
      px={3}
      py={1}
      rounded={"md"}
      variant={toHighlight ? "solid" : "ghost"}
      _hover={{
        textDecoration: "none",
        bg: useColorModeValue("gray.200", "gray.700"),
      }}
      onClick={() => {
        navigate(link)
      }}
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
  const [user, setUser] = useState<User>();
  const sections = [
    { path: "/auditions", name: t("NavBar.auditions"), icon: <FiMusic /> },
    { path: "/users", name: t("NavBar.discover"), icon: <FiUsers /> },
    // { path: "/newAudition", name: t("NavBar.post") },

    // TODO: PONER BIEN LOS LINKS DESPUES
  ];
  const bg27 = useColorModeValue("gray.200", "gray.700");

  useEffect(() => {
    if (userId) {
      serviceCall(
        userService.getUserById(userId),
        navigate,
        (response) => {
          setUser(
            response
          )
        },
      )
    }
  }, [userId, navigate])

  const { isOpen, onOpen, onClose } = useDisclosure();

  return (
    <Box bg={useColorModeValue("white", "gray.900")} px={4}>
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
            <a style={{ cursor: "pointer" }} onClick={() => { navigate(isAuthenticated ? '/auditions' : '/') }} className="logo-section">
              <HStack>
                <Image src={BandifyLogo} w={8} alt={t("Alts.bandify")} />
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
                  variant={"solid"}
                  colorScheme={"teal"}
                  size={"md"}
                  mx={4}
                  px={6}
                  leftIcon={<AddIcon />}
                  onClick={() => {
                    navigate('/newAudition')
                  }
                  }
                >
                  {t("NavBar.post")}
                </Button>
              }

              <Button
                px={4}
                py={1}
                rounded={"md"}
                mr={'6'}
                _hover={{
                  textDecoration: "none",
                  bg: bg27,
                }}
                onClick={() => {
                  navigate(isBand ? '/profile/auditions' : '/applications')
                }}
              >
                <RiAppsFill />
              </Button>

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
                  />
                </MenuButton>
                <MenuList>
                  <MenuItem gap={2} onClick={() => { navigate('/profile') }}>
                    <BiUser />
                    {t("NavBar.profileAlt")}
                  </MenuItem>
                  <MenuDivider />
                  <MenuItem gap={2} onClick={() => { logout(); navigate('/welcome'); }}>
                    <FiLogOut />                    {t("NavBar.logoutAlt")}</MenuItem>
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
                style={{ cursor: "pointer" }}
                onClick={() => { navigate('/login') }}
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
                style={{cursor: "pointer"}}
                onClick={() => { navigate('/register') }}
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

  );
}

export default Nav;
