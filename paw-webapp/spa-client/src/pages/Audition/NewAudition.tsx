import { Avatar, Box, Button, Flex, FormControl, FormErrorMessage, FormHelperText, FormLabel, GridItem, Heading, HStack, Input, SimpleGrid, Stack, Text, Textarea, useColorModeValue, Icon, useToast } from "@chakra-ui/react";
import { useForm } from "react-hook-form";
import { useTranslation } from "react-i18next";
import { FaUser } from "react-icons/fa";
import {newAuditionOptions} from "./validations"

import {
  Select,
  CreatableSelect,
  AsyncSelect,
  OptionBase,
  GroupBase,
} from "chakra-react-select";
import { LocationGroup, GenreGroup, RoleGroup } from "./EntitiesGroups";
import { useRoleService } from "../../contexts/RoleService";
import { useLocationService } from "../../contexts/LocationService";
import { useGenreService } from "../../contexts/GenreService";
import React, { useEffect, useState } from "react";
import { serviceCall } from "../../services/ServiceManager";
import { useNavigate } from "react-router-dom";
import { AuditionInput } from "../../api/types/Audition"
import { useAuditionService } from "../../contexts/AuditionService";
import { Helmet } from "react-helmet";

interface FormData {
  title: string;
  description: string;
  location: string;
  genres: string[];
  roles: string[];
}

const NewAudition = () => {
  const { t } = useTranslation()

  const [locationOptions, setLocationOptions] = useState<LocationGroup[]>([]);
  const [genreOptions, setGenreOptions] = useState<GenreGroup[]>([]);
  const [roleOptions, setRoleOptions] = useState<RoleGroup[]>([]);

  const navigate = useNavigate();

  const [location, setLocation] = useState<LocationGroup>();
  const [genres, setGenres] = useState<GenreGroup[]>([]);
  const [roles, setRoles] = useState<RoleGroup[]>([]);

  const auditionService = useAuditionService();
  const roleService = useRoleService();
  const locationService = useLocationService();
  const genreService = useGenreService();
  const toast = useToast();

  useEffect(() => {
    serviceCall(
      genreService.getGenres(),
      navigate,
      (genres) => {
        const genreAux: GenreGroup[] = genres.map((genre) => {
          return { value: genre.name, label: genre.name };
        });
        setGenreOptions(genreAux);
      }

    )

    serviceCall(
      roleService.getRoles(),
      navigate,
      (roles) => {
        const roleAux: RoleGroup[] = roles.map((role) => {
          return { value: role.name, label: role.name };
        });
        setRoleOptions(roleAux);
      }
    )

    serviceCall(
      locationService.getLocations(),
      navigate,
      (locations) => {
        const locationAux: LocationGroup[] = locations.map((location) => {
          return { value: location.name, label: location.name };
        });
        setLocationOptions(locationAux);
      }
    )
  }, []);


  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<FormData>();

  const onCancel = () => {
    navigate(-1);
  };

  const isValidForm = (data: FormData) => {
    if (!location) {
      toast({
        title: t("EditAudition.locationRequired"),
        status: "error",
        duration: 9000,
        isClosable: true,
      });
      return false;
    }
    
    if (genres.length == 0) {
      toast({
        title: t("EditAudition.genresRequired"),
        status: "error",
        duration: 9000,
        isClosable: true,
      });
      return false;
    }

    if (roles.length == 0) {
      toast({
        title: t("EditAudition.rolesRequired"),
        status: "error",
        duration: 9000,
        isClosable: true,
      });
      return false;
    }
    
    if (roles.length > 5) {
      toast({
        title: t("EditAudition.maxRoles"),
        status: "error",
        duration: 9000,
        isClosable: true,
      });
      return false;
    }

    if(genres.length > 5) {
      toast({
        title: t("EditAudition.maxGenres"),
        status: "error",
        duration: 9000,
        isClosable: true,
      });
      return false;
    }

    return true;
  }

  const onSubmit = async (data: FormData) => {
    if (!isValidForm(data)) return;
    const auditionInput: AuditionInput = {
      title: data.title,
      description: data.description,
      location: location!.value,
      musicGenres: genres.map((genre) => genre.value),
      lookingFor: roles.map((role) => role.value),
    }

    serviceCall(
      auditionService.createAudition(auditionInput),
      navigate,
      (response) => {
        console.log(response)
      }
    ).then((r) => {
      if (r.hasFailed()) {
        toast({
          title: t("EditAudition.errorCreatingAudition"),
          description: t("EditAudition.errorCreatingAuditionMessage"),
          status: "error",
          duration: 9000,
          isClosable: true,
        });
      } else {
        toast({
          title: t("EditAudition.successCreatingAudition"),
          status: "success",
          duration: 9000,
          isClosable: true,
        });
        navigate("/auditions/" + r.getData().id);
      }
    })
  };

  return (
    <>
      <Helmet>
        <title>{t("EditAudition.newAuditionHeading")}</title>
      </Helmet>
      <Box
      rounded={"lg"}
      bg={useColorModeValue("white", "gray.900")}
      p={10}
      m={10}
      boxShadow={'lg'}
      >
      <Box>
        <SimpleGrid
          display={{
            base: "initial",
            md: "grid",
          }}
          columns={{
            md: 3,
          }}
          spacing={{
            md: 6,
          }}
        >
          <GridItem
            colSpan={{
              md: 1,
            }}
          >
            <Box px={[4, 0]}>
              <Heading fontSize={'x-large'} fontWeight="bold" lineHeight="6">
                {t("EditAudition.newAudition")}
              </Heading>
              <Text
                mt={1}
                fontSize="lg"
                color="gray.600"
                _dark={{
                  color: "gray.400",
                }}
              >
                {t("EditAudition.newAuditionDescription")}
              </Text>
            </Box>
          </GridItem>
          <GridItem
            mt={[5, null, 0]}
            colSpan={{
              md: 2,
            }}
          >
            <form

              onSubmit={handleSubmit(onSubmit)}

            >
              <Stack
                bg={useColorModeValue("white", "gray.900")}
                border={'1px'}
                borderColor={useColorModeValue("gray.200", "gray.700")}
                px={4}
                py={5}
                roundedTop={'md'}
                spacing={6}
                p={{
                  sm: 6,
                }}
              >

                <FormControl
                  id="title"
                  isRequired
                  isInvalid={Boolean(errors.title)}
                >
                  <FormLabel fontSize={16} fontWeight="bold">
                    {t("EditAudition.auditionTitle")}
                  </FormLabel>
                  <Input
                    type="text"
                    maxLength={50}
                    placeholder={t("EditAudition.titlePlaceholder")}
                    {...register("title", newAuditionOptions.title)}
                  />
                  <FormErrorMessage>{errors.title?.message}</FormErrorMessage>
                </FormControl>

                <FormControl id="description"
                  isRequired
                  isInvalid={Boolean(errors.description)}
                  mt={1}>
                  <FormLabel
                    fontSize={16} fontWeight="bold"
                  >
                    {t("EditAudition.auditionDescription")}
                  </FormLabel>
                  <Textarea
                    mt={1}
                    rows={3}
                    shadow="sm"
                    maxLength={300}
                    placeholder={t("EditAudition.descriptionPlaceholder")}
                    {...register("description", newAuditionOptions.description)}
                  />
                  <FormErrorMessage>{errors.description?.message}</FormErrorMessage>
                  <FormHelperText>
                    {t("EditAudition.descriptionHelp")}
                  </FormHelperText>
                </FormControl>

                <FormControl isRequired>
                  <FormLabel fontSize={16} fontWeight="bold">{t("EditAudition.location")}</FormLabel>
                  <Select<LocationGroup, false, GroupBase<LocationGroup>>
                    name="locations"
                    options={locationOptions}
                    placeholder={t("AuditionSearchBar.locationPlaceholder")}
                    closeMenuOnSelect={true}
                    variant="filled"
                    tagVariant="solid"
                    onChange={(loc) => {
                      setLocation(loc!);
                    }}
                  />
                </FormControl>

                <FormControl isRequired>
                  <FormLabel fontSize={16} fontWeight="bold">{t("AuditionSearchBar.genre")}</FormLabel>
                  <Select<GenreGroup, true, GroupBase<GenreGroup>>
                    isMulti
                    name="genres"
                    options={genreOptions}
                    placeholder={t("EditAudition.genrePlaceholder")}
                    closeMenuOnSelect={false}
                    variant="filled"
                    tagVariant="solid"
                    onChange={(event) => {
                      setGenres(event.flatMap((e) => e));
                    }}
                  />
                </FormControl>
                <FormControl isRequired>
                  <FormLabel fontSize={16} fontWeight="bold">{t("AuditionSearchBar.role")}</FormLabel>
                  <Select<RoleGroup, true, GroupBase<RoleGroup>>
                    isMulti
                    name="roles"
                    options={roleOptions}
                    placeholder={t("EditAudition.rolePlaceholder")}
                    closeMenuOnSelect={false}
                    variant="filled"
                    tagVariant="solid"
                    onChange={(event) => {
                      setRoles(event.flatMap((e) => e));
                    }}
                  />
                </FormControl>

              </Stack>
              <Box
                bg={useColorModeValue("white", "gray.900")}
                border={'1px'}
                borderColor={useColorModeValue("gray.200", "gray.700")}
                roundedBottom={'md'}
                px={{
                  base: 4,
                  sm: 6,
                }}
                py={3}

                textAlign="right"
              >
                <Button
                  type="submit"
                  bg={"blue.400"}
                  color={"white"}
                  _hover={{
                    bg: "blue.500",
                  }}
                  _focus={{
                    shadow: "",
                  }}
                  fontWeight="md"
                  mr={4}
                >
                  {t("Button.post")}
                </Button>
                <Button
                    bg={"gray.400"}
                    color={"white"}
                    _hover={{
                      bg: "gray.500",
                    }}
                    _focus={{
                      shadow: "",
                    }}
                    fontWeight="md"
                    onClick={onCancel}
                >
                  {t("EditAudition.cancel")}
                </Button>
              </Box>
            </form>
          </GridItem>
        </SimpleGrid>
      </Box>
    </Box> 
  </>
  );
}

export default NewAudition