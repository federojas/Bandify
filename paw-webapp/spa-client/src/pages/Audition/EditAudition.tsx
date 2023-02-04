import {
  Avatar,
  Box,
  Button,
  Flex,
  FormControl,
  FormErrorMessage,
  FormHelperText,
  FormLabel,
  GridItem,
  Heading,
  HStack,
  Input,
  SimpleGrid,
  Stack,
  Text,
  Textarea,
  useColorModeValue,
  Icon,
  useToast
} from "@chakra-ui/react";
import { useForm } from "react-hook-form";
import { useTranslation } from "react-i18next";
import { FaUser } from "react-icons/fa";
import newAuditionOptions from "./validations"

import {
  Select,
  CreatableSelect,
  AsyncSelect,
  OptionBase,
  GroupBase,
} from "chakra-react-select";
import { LocationGroup, GenreGroup, RoleGroup } from "./EntitiesGroups";
import { genreService, roleService, locationService } from "../../services";
import { useEffect, useState } from "react";
import { serviceCall } from "../../services/ServiceManager";
import {useNavigate, useParams} from "react-router-dom";
import { AuditionInput } from "../../api/types/Audition"
import { useAuditionService } from "../../contexts/AuditionService";

interface FormData {
  title: string;
  description: string;
  location: string;
  genres: string[];
  roles: string[];
}


const EditAudition = () => {
  const { t } = useTranslation()

  const [locationOptions, setLocationOptions] = useState<LocationGroup[]>([]);
  const [genreOptions, setGenreOptions] = useState<GenreGroup[]>([]);
  const [roleOptions, setRoleOptions] = useState<RoleGroup[]>([]);
  const toast = useToast();
  const navigate = useNavigate();
  const {id} = useParams();
  console.log(id)
  const [location, setLocation] = useState<LocationGroup>({ label: "Buenos Aires", value: "Buenos Aires" });
  const [genres, setGenres] = useState<GenreGroup[]>([]);
  const [roles, setRoles] = useState<RoleGroup[]>([]);

  const auditionService = useAuditionService();

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


  const onSubmit = async (data: FormData) => {
    const auditionInput: AuditionInput = {
      title: data.title,
      description: data.description,
      location: location.value,
      musicGenres: genres.map((genre) => genre.value),
      lookingFor: roles.map((role) => role.value),
    }

    serviceCall(
      auditionService.updateAudition(Number(id), auditionInput),
      navigate,
      () => {
      }
    ).then((response) => {
      if(response.hasFailed()){
        toast({
          title: t("Register.error"),
          status: "error",
          description: t("EditAudition.error"),
          isClosable: true,
        })
      } else {
        toast({
          title: t("Register.success"),
          status: "success",
          description: t("EditAudition.success"),
          isClosable: true,
        })
        navigate("/auditions");
      }
    })
  };

  const onCancel = () => {
    navigate(-1);
  };

  // EditAudition: {
  //   title: "Edit audition",
  //   subtitle: "Edit your audition",
  //   auditionTitle: "Audition title",
  //   auditionDescription: "Audition description",
  //   auditionDescriptionHelper: "Describe the audition, the experience you are looking for, etc.",
  //   auditionLocation: "Audition location",
  //   auditionGenre: "Desired genres",
  //   auditionRole: "Roles you are looking for",
  //   save: "Save",
  //   cancel: "Cancel",
  // }

  return <Box
    rounded={"lg"}
    bg={useColorModeValue("gray.100", "gray.900")}
    p={10}
    m={10}
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
              {t("EditAudition.title")}
            </Heading>
            <Text
              mt={1}
              fontSize="lg"
              color="gray.600"
              _dark={{
                color: "gray.400",
              }}
            >
              {t("EditAudition.subtitle")}
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
              bg={useColorModeValue("gray.100", "gray.900")}
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
                  // maxLength={50}
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
                  {...register("description", newAuditionOptions.description)}
                />
                <FormErrorMessage>{errors.description?.message}</FormErrorMessage>
                <FormHelperText>
                  {t("EditAudition.auditionDescriptionHelper")}
                </FormHelperText>
              </FormControl>

              {/* <FormControl>
                <FormLabel>{t("AuditionSearchBar.location")}</FormLabel>
                <Select<LocationGroup, false, GroupBase<LocationGroup>>
                  name="locations"
                  options={locationOptions}
                  placeholder={t("AuditionSearchBar.locationPlaceholder")}
                  closeMenuOnSelect={true}
                  variant="filled"
                  tagVariant="solid"
                  onChange={(loc) => {

                    setLocation(loc);
                  }}
                />
              </FormControl> */}

              <FormControl>
                <FormLabel>{t("EditAudition.genre")}</FormLabel>
                <Select<GenreGroup, true, GroupBase<GenreGroup>>
                  isMulti
                  name="genres"
                  options={genreOptions}
                  placeholder={t("AuditionSearchBar.genrePlaceholder")}
                  closeMenuOnSelect={false}
                  variant="filled"
                  tagVariant="solid"
                  onChange={(event) => {
                    setGenres(event.flatMap((e) => e));
                  }}
                />
              </FormControl>
              <FormControl>
                <FormLabel>{t("EditAudition.role")}</FormLabel>
                <Select<RoleGroup, true, GroupBase<RoleGroup>>
                  isMulti
                  name="roles"
                  options={roleOptions}
                  placeholder={t("AuditionSearchBar.rolePlaceholder")}
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
              bg={useColorModeValue("gray.100", "gray.900")}
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
                {t("EditAudition.save")}
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



  </Box>;
}

export default EditAudition