import React, { ReactNode } from 'react';
import {
  IconButton,
  Box,
  Flex,
  Icon,
  useColorModeValue,
  Link,
  Text,
  useDisclosure,
  BoxProps,
  FlexProps,
  Grid,
} from '@chakra-ui/react';
import {
  FiHome,
  FiTrendingUp,
  FiMenu,
  FiMusic,
} from 'react-icons/fi';
import { IconType } from 'react-icons';
import { ReactText } from 'react';
import { useTranslation } from 'react-i18next';
import { AiOutlineUserAdd } from 'react-icons/ai';
import { useLocation, useNavigate } from 'react-router-dom';

interface LinkItemProps {
  name: string;
  icon: IconType;
}

export default function SidenavLayout({ children }: { children: ReactNode }) {
  const { onClose } = useDisclosure();
  const bg = useColorModeValue('white', 'gray.900');
  return (
    <Grid templateColumns={{ base: 'full', md: 'auto 1fr' }} 
    boxShadow={'lg'}
   m={'10'} rounded={'xl'} minH="fit-content" bg={bg}>
      <SidebarContent
        onClose={() => onClose}
        display={{ md: 'block' }}
      />
      <Box p="8" minW="full">
        {children}
      </Box>
    </Grid>
  );
}

interface SidebarProps extends BoxProps {
  onClose: () => void;
}

const SidebarContent = ({ onClose, ...rest }: SidebarProps) => {
  const {t} = useTranslation();
  return (
    <Box
      bg={useColorModeValue('white', 'gray.900')}
      borderRight="1px"
      borderRightColor={useColorModeValue('gray.200', 'gray.700')}
      w={{ base: 'full', md: 60 }}
      py={'4'}
      h='full'
      roundedLeft={'xl'}
      {...rest}>

      <NavItem key={'applications'} href={'/profile/applications'} icon={FiMusic}>
        {t("Hub.Applications")}
      </NavItem>
      <NavItem key={'invites'} href={'/profile/invites'} icon={AiOutlineUserAdd}>
        {t("Hub.Invites")} 
      </NavItem>

    </Box>
  );
};

interface NavItemProps extends FlexProps {
  icon: IconType;
  children: ReactText;
  href: string;
}
const NavItem = ({ icon, children, href, ...rest }: NavItemProps) => {
  const location = useLocation();
  const isSelected = location.pathname === href;
  const navigate = useNavigate();

  return (
    <Link onClick={() => {
      navigate(href)
    }} style={{ textDecoration: 'none' }} _focus={{ boxShadow: 'none' }}>
      <Flex
        align="center"
        p="4"
        mx="4"
        mb="4"
        borderRadius="lg"
        role="group"
        cursor="pointer"
        bg={isSelected ? 'cyan.400' : undefined}
        color={useColorModeValue('black', 'white')}
        _hover={{
          bg: 'cyan.400',
          color: 'white',
        }}
        {...rest}>
        {icon && (
          <Icon
            mr="4"
            fontSize="16"
            _groupHover={{
              color: 'white',
            }}
            as={icon}
          />
        )}
        {children}
      </Flex>
    </Link>
  );
};

interface MobileProps extends FlexProps {
  onOpen: () => void;
}
