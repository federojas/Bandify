import {
  Badge,
  Button,
  Center,
  Flex,
  Heading,
  HStack,
  Image,
  Link,
  Stack,
  Text,
  useColorModeValue,
} from '@chakra-ui/react';
import { useEffect, useState } from 'react';
import { useTranslation } from 'react-i18next';
import { BiBullseye } from 'react-icons/bi';
import { FiMusic } from 'react-icons/fi';
import { useNavigate } from 'react-router-dom';
import { useUserService } from '../../contexts/UserService';
import { User } from '../../models';
import { serviceCall } from '../../services/ServiceManager';
import ArtistTag from '../Tags/ArtistTag';
import BandTag from '../Tags/BandTag';
import GenreTag, { GenreCount } from '../Tags/GenreTag';
import RoleTag, { RoleCount } from '../Tags/RoleTag';
import {useEffect, useState} from "react";
import {serviceCall} from "../../services/ServiceManager";
import {useUserService} from "../../contexts/UserService";
import {useNavigate} from "react-router-dom";

const ProfileCard: React.FC<User> = ({
  name,
  surname,
  band,
  location,
  genres,
  roles,
  id,
  description
}) => {

  const { t } = useTranslation();

  const [showMore, setShowMore] = useState(false);

  const filteredRoles = showMore ? roles : roles.slice(0, 1);
  const roleCount = roles.length - filteredRoles.length;

  const filteredGenres = showMore ? genres : genres.slice(0, 1);
  const genreCount = genres.length - filteredGenres.length;
  const [userImg, setUserImg] = useState<string | undefined>(undefined)
  const userService = useUserService();
  const navigate = useNavigate();

  useEffect(() => {
    if (id) {
      serviceCall(
        userService.getProfileImageByUserId(id),
        navigate,
        (response) => {
          setUserImg(
            response
          )
        },
      )
    }
  }, [id, navigate])



  const navigate = useNavigate();
  const userService = useUserService();
  const [userImg, setUserImg] = useState<string | undefined>(undefined);

  useEffect(() => {
    if(id) {
      serviceCall(
          userService.getProfileImageByUserId(id),
          navigate,
          (response) => {
            setUserImg(
                response
            )
          },
      )
    }
  }, [id, navigate])
  return (
    <Center py={6}>
      <Stack
        borderWidth="1px"
        borderRadius="lg"
        w={{ sm: '100%', md: '540px' }}
        height={{ sm: '476px', md: '20rem' }}
        direction={{ base: 'column', md: 'row' }}
        bg={useColorModeValue('white', 'gray.900')}
        boxShadow={'2xl'}
        padding={4}>
        <Center flex={1}>
          <Image
            objectFit="cover"
            boxSize="100%"
            src={`data:image/png;base64,${userImg}`} //TODO: revisar posible mejora a link
            src={`data:image/png;base64,${userImg}`} //TODO: revisar posible mejora a link
            alt="Profile Picture"
            borderRadius="full"
            boxSize="150px"
            shadow="lg"
            border="5px solid"
            borderColor="gray.800"
            _dark={{
              borderColor: "grayu.200",
            }}
          />
        </Center>
        <Stack
          flex={2}
          flexDirection="column"
          justifyContent="center"
          alignItems="center"
          p={1}
          pt={2}>
          <Heading fontSize={'2xl'} fontFamily={'body'}>
            {name} {surname? ' ' + surname : ''}
          </Heading>

          {
            band ? <BandTag /> : <ArtistTag />
          }

          {roles.length > 0 && <HStack spacing={4}>
            <BiBullseye />
            <HStack spacing="2" wrap="nowrap" style={{ maxWidth: "100%" }}>
              {filteredRoles.map((role) => (
                <RoleTag role={role} />
              ))}
              {roleCount > 0 && (
                <RoleCount count={roleCount} />
              )}
            </HStack>
          </HStack>}
          {genres.length > 0 && <HStack spacing={4}>
            <FiMusic />
            <HStack spacing="2" wrap="nowrap" style={{ maxWidth: "100%" }}>
              {filteredGenres.map((genre) => (
                <GenreTag genre={genre} />
              ))}
              {genreCount > 0 && (
                <GenreCount count={genreCount} />
              )}
            </HStack>
          </HStack>}


          <Stack
            flex={'flex'}
            width={'100%'}
            mt={'2rem'}
            direction={'row'}
            padding={2}
            justifyContent={'center'}
            alignItems={'center'}>
            <Button
              as='a'
              href={`/users/${id}`}
              flexBasis={'50%'}
              fontSize={'sm'}
              rounded={'full'}
              bg={'blue.400'}
              color={'white'}
              boxShadow={
                '0px 1px 25px -5px rgb(66 153 225 / 48%), 0 10px 10px -5px rgb(66 153 225 / 43%)'
              }
              _hover={{
                bg: 'blue.500',
              }}
              _focus={{
                bg: 'blue.500',
              }}>
              {t("ProfileCard.Profile")}
            </Button>

          </Stack>
        </Stack>
      </Stack>
    </Center>
  );
}

export default ProfileCard;