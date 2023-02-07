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
    Center,
    useToast, Image
} from "@chakra-ui/react";
import { useForm } from "react-hook-form";
import { useTranslation } from "react-i18next";
import {editOptions, editOptionsES} from "./validations"
import {
  Select,
  CreatableSelect,
  AsyncSelect,
  OptionBase,
  GroupBase,
} from "chakra-react-select";
import { LocationGroup, GenreGroup, RoleGroup, AvailableGroup } from "./EntitiesGroups";
import React, {useContext, useEffect, useState} from "react";
import { serviceCall } from "../../services/ServiceManager";
import { useNavigate } from "react-router-dom";
import AuthContext from "../../contexts/AuthContext";
import {User} from "../../models";
import {useUserService} from "../../contexts/UserService";
import {useGenreService} from "../../contexts/GenreService";
import {useRoleService} from "../../contexts/RoleService";
import {useLocationService} from "../../contexts/LocationService";
import {UserUpdateInput} from "../../api/types/User";

interface FormData {
  name: string;
  surname: string;
  description: string;
  location: string;
  genres: string[];
  roles: string[];
  available: boolean;
  image: FileList;
}

const EditArtist = () => {
  const { t } = useTranslation();
  const locationService = useLocationService();
  const roleService = useRoleService();
  const genreService = useGenreService();
  const userService = useUserService();
  const [user, setUser] = useState<User>();
  const [isLoading, setIsLoading] = useState(true);
  const [locationOptions, setLocationOptions] = useState<LocationGroup[]>([]);
  const [genreOptions, setGenreOptions] = useState<GenreGroup[]>([]);
  const [roleOptions, setRoleOptions] = useState<RoleGroup[]>([]);
  const navigate = useNavigate();
  const [location, setLocation] = useState<LocationGroup>();
  const [genres, setGenres] = useState<GenreGroup[]>([]);
  const [roles, setRoles] = useState<RoleGroup[]>([]);
  const [available, setAvailable] = useState<AvailableGroup>();
  const { userId } = useContext(AuthContext);
  const [userImg, setUserImg] = useState<string | undefined>(undefined);
  const bg19=useColorModeValue("white", "gray.900");
  const bg27 = useColorModeValue("gray.200", "gray.700");
  const toast = useToast();
  const options = localStorage.getItem('i18nextLng') === 'es' ? editOptionsES : editOptions;
  const filterAvailable = require(`../../images/available.png`);


  const onCancel = () => {
    navigate(-1);
  };

  const availableOptions = [
    { value: true, label: t("Edit.availableTrue") },
    { value: false, label: t("Edit.availableFalse")},
  ];

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

  useEffect(() => {
    serviceCall(
          userService.getUserById(Number(userId)),
          navigate,
          (user) => {
              setUser(user)
              setUserImg(user.profileImage)
              setLocation({label:user.location, value:user.location} as LocationGroup)
              setGenres(user.genres.map(r => {return {value: r, label: r}}))
              setRoles(user.roles.map(r => {return {value: r, label: r}}))
              setAvailable(user.available ? {value:true, label:t("Edit.availableTrue")} as AvailableGroup : {value:false, label:t("Edit.availableFalse")} as AvailableGroup)
              setIsLoading(false)
          }
    )
  }, [userService]);

    const handlePicture = (image: Blob) => {
        setUserImg(URL.createObjectURL(image));
    };


  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<FormData>();

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

        if (roles.length > 15) {
            toast({
                title: t("EditAudition.maxRoles"),
                status: "error",
                duration: 9000,
                isClosable: true,
            });
            return false;
        }

        if(genres.length > 15) {
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
      const userInput: UserUpdateInput = {
          name: data.name,
          surname: data.surname,
          description: data.description,
          location: location!.value,
          genres: genres.map((genre) => genre.value),
          roles: roles.map((role) => role.value),
          available: available!.value,
      }
      if(data.image && data.image[0]) {
          serviceCall(
              userService.updateUserProfileImage(Number(userId), data.image[0]),
              navigate,
              () => {
              }
          ).then((response) => {
              if(response.hasFailed()){
                  toast({
                      title: t("Register.error"),
                      status: "error",
                      description: t("Edit.error"),
                      isClosable: true,
                  })
              }
          })
      }
      serviceCall(
          userService.updateUser(Number(userId), userInput),
          navigate,
          () => {
          }
      ).then((response) => {
          if(response.hasFailed()){
              toast({
                  title: t("Register.error"),
                  status: "error",
                  description: t("Edit.error"),
                  isClosable: true,
              })
          } else {
              toast({
                  title: t("Register.success"),
                  status: "success",
                  description: t("Edit.success"),
                  isClosable: true,
              })
              navigate("/profile");
          }
      })
  };

  return (
    <Box
    rounded={"lg"}
    bg={bg19}
    p={10}
    m={10}
  >
        {isLoading ? <Center mt={'15%'}><span className="loader"></span></Center> :(
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
              {t("Edit.title")}
            </Heading>
            <Text
                mt={1}
                fontSize="lg"
                color="gray.600"
                _dark={{
                  color: "gray.400",
                }}
            >
              {t("Edit.subtitle")}
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
              bg={bg19}
              border={'1px'}
              borderColor={bg27}
              px={4}
              py={5}
              roundedTop={'md'}
              spacing={6}
              p={{
                sm: 6,
              }}
            >

              <HStack>
                <Box flex={1}>
                  <FormControl
                    id="name"
                    isRequired
                    isInvalid={Boolean(errors.name)}
                  >
                    <FormLabel fontSize={16} fontWeight="bold">
                      {t("Edit.name")}
                    </FormLabel>
                    <Input
                      type="text"
                      maxLength={50}
                      placeholder={t("EditAudition.titlePlaceholder")}
                      defaultValue={user?.name}
                      {...register("name", options.name)}
                    />
                    <FormErrorMessage>{errors.name?.message}</FormErrorMessage>
                  </FormControl>
                </Box>
                <Box flex={1}>
                  <FormControl
                    id="surname"
                    isRequired
                    isInvalid={Boolean(errors.surname)}
                  >
                    <FormLabel fontSize={16} fontWeight="bold">
                      {t("Edit.surname")}
                    </FormLabel>
                    <Input
                      type="text"
                      maxLength={50}
                      placeholder={t("EditAudition.titlePlaceholder")}
                      defaultValue={user?.surname}
                      {...register("surname", options.surname)}
                    />
                    <FormErrorMessage>{errors.surname?.message}</FormErrorMessage>
                  </FormControl>
                </Box>
              </HStack>
              <FormControl id="description" mt={1} isInvalid={Boolean(errors.description)}>
                <FormLabel
                  fontSize={16} fontWeight="bold"
                >
                  {t("Edit.description")}
                </FormLabel>
                <Textarea
                  mt={1}
                  rows={3}
                  maxLength={500}
                  shadow="sm"
                  defaultValue={user?.description}
                  placeholder={t("Edit.descriptionPlaceholder")}
                  {...register("description", options.description)}
                />
                  <FormErrorMessage>{errors.description?.message}</FormErrorMessage>
              </FormControl>

              <FormControl id="image" isInvalid={Boolean(errors.image)}>
                <FormLabel
                  fontSize={16} fontWeight="bold"
                >
                  {t("Edit.picture")}
                </FormLabel>
                <Flex alignItems="center" mt={1}>
                    <Flex>
                  <Avatar
                    boxSize={40}
                    fontSize={16} fontWeight="bold"
                    bg={bg19}
                    mr={8}
                    borderColor="gray.800"
                    _dark={{
                        borderColor: "gray.200",
                        backgroundColor: "white"
                    }}
                    src={userImg} //TODO ALT
                  />
                    {available!.value ? <Image
                        src={filterAvailable}
                        alt={t("Alts.available")}
                        boxSize="150px"
                        ml={0.7}
                        mt={1.4}
                        borderRadius="full"
                        position={"absolute"}
                        /> : <></>
                    }
                    </Flex>
                    <Flex>
                        <Stack>
                            <Input
                                variant="unstyled"
                                type="file"
                                accept='image/png, image/jpeg'
                                onInput={(event) => {
                                    if(event.currentTarget.files)
                                        handlePicture(event.currentTarget.files[0]);
                                }}
                                {...register('image',  {
                                    validate: {
                                        size: (image) =>
                                            image && image[0] && image[0].size / (1024 * 1024) < 1,
                                    },
                                })}
                            />
                            {errors.image?.type === 'size' && (
                                <FormErrorMessage>{options.image?.size.message}</FormErrorMessage>
                            )}
                        </Stack>
                    </Flex>
                </Flex>
              </FormControl>
              <FormControl>
                <FormLabel fontSize={16} fontWeight="bold">{t("Edit.available")}</FormLabel>
                <Select
                    name="available"
                    options={availableOptions}
                    closeMenuOnSelect={false}
                    variant="filled"
                    tagVariant="solid"
                    defaultValue={available}
                    onChange={(selection) => {
                      if(selection)
                        setAvailable(selection);
                    }}
                />
              </FormControl>

              <FormControl isRequired>
                <FormLabel fontSize={16} fontWeight="bold">{t("Edit.location")}</FormLabel>
                <Select<LocationGroup, false, GroupBase<LocationGroup>>
                  name="locations"
                  options={locationOptions}
                  placeholder={t("AuditionSearchBar.locationPlaceholder")}
                  closeMenuOnSelect={true}
                  variant="filled"
                  tagVariant="solid"
                  defaultValue={location}
                  onChange={(loc) => {
                    setLocation(loc!);
                  }}
                />
              </FormControl>

              <FormControl>
                <FormLabel fontSize={16} fontWeight="bold" >{t("Edit.genreArtist")}</FormLabel>
                <Select<GenreGroup, true, GroupBase<GenreGroup>>
                  isMulti
                  name="genres"
                  options={genreOptions}
                  placeholder={t("Edit.genrePlaceholder")}
                  closeMenuOnSelect={false}
                  variant="filled"
                  tagVariant="solid"
                  defaultValue={genres}
                  onChange={(event) => {
                    setGenres(event.flatMap((e) => e));
                  }}
                />
              </FormControl>
              <FormControl>
                <FormLabel fontSize={16} fontWeight="bold" >{t("Edit.roleArtist")}</FormLabel>
                <Select<RoleGroup, true, GroupBase<RoleGroup>>
                  isMulti
                  name="roles"
                  options={roleOptions}
                  placeholder={t("Edit.rolePlaceholder")}
                  closeMenuOnSelect={false}
                  variant="filled"
                  tagVariant="solid"
                  defaultValue={roles}
                  onChange={(event) => {
                    setRoles(event.flatMap((e) => e));
                  }}
                />
              </FormControl>
              {/*<FormControl>
                <FormLabel
                  fontSize="sm"
                  fontWeight="md"
                  color="gray.700"
                  _dark={{
                    color: "gray.50",
                  }}
                >
                  Cover photo
                </FormLabel>
                <Flex
                  mt={1}
                  justify="center"
                  px={6}
                  pt={5}
                  pb={6}
                  borderWidth={2}
                  _dark={{
                    color: "gray.500",
                  }}
                  borderStyle="dashed"
                  rounded="md"
                >
                  <Stack spacing={1} textAlign="center">
                    <Icon
                      mx="auto"
                      boxSize={12}
                      color="gray.400"
                      _dark={{
                        color: "gray.500",
                      }}
                      stroke="currentColor"
                      fill="none"
                      viewBox="0 0 48 48"
                      aria-hidden="true"
                    >
                      <path
                        d="M28 8H12a4 4 0 00-4 4v20m32-12v8m0 0v8a4 4 0 01-4 4H12a4 4 0 01-4-4v-4m32-4l-3.172-3.172a4 4 0 00-5.656 0L28 28M8 32l9.172-9.172a4 4 0 015.656 0L28 28m0 0l4 4m4-24h8m-4-4v8m-12 4h.02"
                        strokeWidth="2"
                        strokeLinecap="round"
                        strokeLinejoin="round"
                      />
                    </Icon>
                    <Flex
                      fontSize="sm"
                      color="gray.600"
                      _dark={{
                        color: "gray.400",
                      }}
                      alignItems="baseline"
                    >
                      <chakra.label
                        htmlFor="file-upload"
                        cursor="pointer"
                        rounded="md"
                        fontSize="md"
                        color="brand.600"
                        _dark={{
                          color: "brand.200",
                        }}
                        pos="relative"
                        _hover={{
                          color: "brand.400",
                          _dark: {
                            color: "brand.300",
                          },
                        }}
                      >
                        <span>Upload a file</span>
                        <VisuallyHidden>
                          <input
                            id="file-upload"
                            name="file-upload"
                            type="file"
                          />
                        </VisuallyHidden>
                      </chakra.label>
                      <Text pl={1}>or drag and drop</Text>
                    </Flex>
                    <Text
                      fontSize="xs"
                      color="gray.500"
                      _dark={{
                        color: "gray.50",
                      }}
                    >
                      PNG, JPG, GIF up to 10MB
                    </Text>
                  </Stack>
                </Flex>
              </FormControl> */}
            </Stack>
            <Box
              bg={bg19}
              border={'1px'}
              borderColor={bg27}
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
                mr={4}
                bg={"blue.400"}
                color={"white"}
                _hover={{
                  bg: "blue.500",
                }}
                _focus={{
                  shadow: "",
                }}
                fontWeight="md"
              >
                {t("Button.save")}
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
                {t("Button.cancel")}
              </Button>
            </Box>
          </form>
        </GridItem>
      </SimpleGrid>

    </Box>

    /* TODO: CODIGO COMENTADO
    <Divider
      my="5"
      borderColor="gray.300"
      _dark={{
        borderColor: "whiteAlpha.300",
      }}
      visibility={{
        base: "hidden",
        sm: "visible",
      }}
    />

    <Box mt={[10, 0]}>
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
            <Heading fontSize="lg" fontWeight="medium" lineHeight="6">
              Personal Information
            </Heading>
            <Text
              mt={1}
              fontSize="sm"
              color="gray.600"
              _dark={{
                color: "gray.400",
              }}
            >
              Use a permanent address where you can receive mail.
            </Text>
          </Box>
        </GridItem>
        <GridItem
          mt={[5, null, 0]}
          colSpan={{
            md: 2,
          }}
        >
          <chakra.form
            method="POST"
            shadow="base"
            rounded={[null, "md"]}
            overflow={{
              sm: "hidden",
            }}
          >
            <Stack
              px={4}
              py={5}
              p={[null, 6]}
              bg="white"
              _dark={{
                bg: "#141517",
              }}
              spacing={6}
            >
              <SimpleGrid columns={6} spacing={6}>
                <FormControl as={GridItem} colSpan={[6, 3]}>
                  <FormLabel
                    htmlFor="first_name"
                    fontSize="sm"
                    fontWeight="md"
                    color="gray.700"
                    _dark={{
                      color: "gray.50",
                    }}
                  >
                    First name
                  </FormLabel>
                  <Input
                    type="text"
                    name="first_name"
                    id="first_name"
                    autoComplete="given-name"
                    mt={1}
                    focusBorderColor="brand.400"
                    shadow="sm"
                    size="sm"
                    w="full"
                    rounded="md"
                  />
                </FormControl>

                <FormControl as={GridItem} colSpan={[6, 3]}>
                  <FormLabel
                    htmlFor="last_name"
                    fontSize="sm"
                    fontWeight="md"
                    color="gray.700"
                    _dark={{
                      color: "gray.50",
                    }}
                  >
                    Last name
                  </FormLabel>
                  <Input
                    type="text"
                    name="last_name"
                    id="last_name"
                    autoComplete="family-name"
                    mt={1}
                    focusBorderColor="brand.400"
                    shadow="sm"
                    size="sm"
                    w="full"
                    rounded="md"
                  />
                </FormControl>

                <FormControl as={GridItem} colSpan={[6, 4]}>
                  <FormLabel
                    htmlFor="email_address"
                    fontSize="sm"
                    fontWeight="md"
                    color="gray.700"
                    _dark={{
                      color: "gray.50",
                    }}
                  >
                    Email address
                  </FormLabel>
                  <Input
                    type="text"
                    name="email_address"
                    id="email_address"
                    autoComplete="email"
                    mt={1}
                    focusBorderColor="brand.400"
                    shadow="sm"
                    size="sm"
                    w="full"
                    rounded="md"
                  />
                </FormControl>

                <FormControl as={GridItem} colSpan={[6, 3]}>
                  <FormLabel
                    htmlFor="country"
                    fontSize="sm"
                    fontWeight="md"
                    color="gray.700"
                    _dark={{
                      color: "gray.50",
                    }}
                  >
                    Country / Region
                  </FormLabel>
                  <Select
                    id="country"
                    name="country"
                    autoComplete="country"
                    placeholder="Select option"
                    mt={1}
                    focusBorderColor="brand.400"
                    shadow="sm"
                    size="sm"
                    w="full"
                    rounded="md"
                  >
                    <option>United States</option>
                    <option>Canada</option>
                    <option>Mexico</option>
                  </Select>
                </FormControl>

                <FormControl as={GridItem} colSpan={6}>
                  <FormLabel
                    htmlFor="street_address"
                    fontSize="sm"
                    fontWeight="md"
                    color="gray.700"
                    _dark={{
                      color: "gray.50",
                    }}
                  >
                    Street address
                  </FormLabel>
                  <Input
                    type="text"
                    name="street_address"
                    id="street_address"
                    autoComplete="street-address"
                    mt={1}
                    focusBorderColor="brand.400"
                    shadow="sm"
                    size="sm"
                    w="full"
                    rounded="md"
                  />
                </FormControl>

                <FormControl as={GridItem} colSpan={[6, 6, null, 2]}>
                  <FormLabel
                    htmlFor="city"
                    fontSize="sm"
                    fontWeight="md"
                    color="gray.700"
                    _dark={{
                      color: "gray.50",
                    }}
                  >
                    City
                  </FormLabel>
                  <Input
                    type="text"
                    name="city"
                    id="city"
                    autoComplete="city"
                    mt={1}
                    focusBorderColor="brand.400"
                    shadow="sm"
                    size="sm"
                    w="full"
                    rounded="md"
                  />
                </FormControl>

                <FormControl as={GridItem} colSpan={[6, 3, null, 2]}>
                  <FormLabel
                    htmlFor="state"
                    fontSize="sm"
                    fontWeight="md"
                    color="gray.700"
                    _dark={{
                      color: "gray.50",
                    }}
                  >
                    State / Province
                  </FormLabel>
                  <Input
                    type="text"
                    name="state"
                    id="state"
                    autoComplete="state"
                    mt={1}
                    focusBorderColor="brand.400"
                    shadow="sm"
                    size="sm"
                    w="full"
                    rounded="md"
                  />
                </FormControl>

                <FormControl as={GridItem} colSpan={[6, 3, null, 2]}>
                  <FormLabel
                    htmlFor="postal_code"
                    fontSize="sm"
                    fontWeight="md"
                    color="gray.700"
                    _dark={{
                      color: "gray.50",
                    }}
                  >
                    ZIP / Postal
                  </FormLabel>
                  <Input
                    type="text"
                    name="postal_code"
                    id="postal_code"
                    autoComplete="postal-code"
                    mt={1}
                    focusBorderColor="brand.400"
                    shadow="sm"
                    size="sm"
                    w="full"
                    rounded="md"
                  />
                </FormControl>
              </SimpleGrid>
            </Stack>
            <Box
              px={{
                base: 4,
                sm: 6,
              }}
              py={3}
              bg="gray.50"
              _dark={{
                bg: "#121212",
              }}
              textAlign="right"
            >
              <Button
                type="submit"
                colorScheme="brand"
                _focus={{
                  shadow: "",
                }}
                fontWeight="md"
              >
                Save
              </Button>
            </Box>
          </chakra.form>
        </GridItem>
      </SimpleGrid>
    </Box>

    <Divider
      my="5"
      borderColor="gray.300"
      _dark={{
        borderColor: "whiteAlpha.300",
      }}
      visibility={{
        base: "hidden",
        sm: "visible",
      }}
    />

    <Box mt={[10, 0]}>
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
            <Heading fontSize="lg" fontWeight="medium" lineHeight="6">
              Notifications
            </Heading>
            <Text
              mt={1}
              fontSize="sm"
              color="gray.600"
              _dark={{
                color: "gray.400",
              }}
            >
              Decide which communications you'd like to receive and how.
            </Text>
          </Box>
        </GridItem>
        <GridItem
          mt={[5, null, 0]}
          colSpan={{
            md: 2,
          }}
        >
          <chakra.form
            method="POST"
            shadow="base"
            rounded={[null, "md"]}
            overflow={{
              sm: "hidden",
            }}
          >
            <Stack
              px={4}
              py={5}
              p={[null, 6]}
              bg="white"
              _dark={{
                bg: "#141517",
              }}
              spacing={6}
            >
              <chakra.fieldset>
                <Box
                  as="legend"
                  fontSize="md"
                  color="gray.900"
                  _dark={{
                    color: "gray.50",
                  }}
                >
                  By Email
                </Box>
                <Stack mt={4} spacing={4}>
                  <Flex alignItems="start">
                    <Flex alignItems="center" h={5}>
                      <Checkbox
                        colorScheme="brand"
                        borderColor="brand.700"
                        _dark={{
                          borderColor: "gray.50",
                        }}
                        id="comments"
                        rounded="md"
                      />
                    </Flex>
                    <Box ml={3} fontSize="sm">
                      <chakra.label
                        htmlFor="comments"
                        fontWeight="md"
                        color="gray.700"
                        _dark={{
                          color: "gray.50",
                        }}
                      >
                        Comments
                      </chakra.label>
                      <Text
                        color="gray.500"
                        _dark={{
                          color: "gray.400",
                        }}
                      >
                        Get notified when someones posts a comment on a posting.
                      </Text>
                    </Box>
                  </Flex>
                  <Flex alignItems="start">
                    <Flex alignItems="center" h={5}>
                      <Checkbox
                        colorScheme="brand"
                        borderColor="brand.700"
                        _dark={{
                          borderColor: "gray.50",
                        }}
                        id="candidates"
                        rounded="md"
                      />
                    </Flex>
                    <Box ml={3} fontSize="sm">
                      <chakra.label
                        htmlFor="candidates"
                        fontWeight="md"
                        color="gray.700"
                        _dark={{
                          color: "gray.50",
                        }}
                      >
                        Candidates
                      </chakra.label>
                      <Text
                        color="gray.500"
                        _dark={{
                          color: "gray.400",
                        }}
                      >
                        Get notified when a candidate applies for a job.
                      </Text>
                    </Box>
                  </Flex>
                  <Flex alignItems="start">
                    <Flex alignItems="center" h={5}>
                      <Checkbox
                        colorScheme="brand"
                        borderColor="brand.700"
                        _dark={{
                          borderColor: "gray.50",
                        }}
                        id="offers"
                        rounded="md"
                      />
                    </Flex>
                    <Box ml={3} fontSize="sm">
                      <chakra.label
                        htmlFor="offers"
                        fontWeight="md"
                        color="gray.700"
                        _dark={{
                          color: "gray.50",
                        }}
                      >
                        Offers
                      </chakra.label>
                      <Text
                        color="gray.500"
                        _dark={{
                          color: "gray.400",
                        }}
                      >
                        Get notified when a candidate accepts or rejects an offer.
                      </Text>
                    </Box>
                  </Flex>
                </Stack>
              </chakra.fieldset>
              <chakra.fieldset>
                <Box
                  as="legend"
                  fontSize="md"
                  color="gray.900"
                  _dark={{
                    color: "gray.50",
                  }}
                >
                  Push Notifications
                  <Text
                    fontSize="sm"
                    color="gray.500"
                    _dark={{
                      color: "gray.400",
                    }}
                  >
                    These are delivered via SMS to your mobile phone.
                  </Text>
                </Box>
                <RadioGroup
                  fontSize="sm"
                  color="gray.700"
                  _dark={{
                    color: "gray.50",
                    borderColor: "gray.50",
                  }}
                  colorScheme="brand"
                  mt={4}
                  defaultValue="1"
                  borderColor="brand.700"
                >
                  <Stack spacing={4}>
                    <Radio spacing={3} value="1">
                      Everything
                    </Radio>
                    <Radio spacing={3} value="2">
                      Same as email
                    </Radio>
                    <Radio spacing={3} value="3">
                      No push notifications
                    </Radio>
                  </Stack>
                </RadioGroup>
              </chakra.fieldset>
            </Stack>
            <Box
              px={{
                base: 4,
                sm: 6,
              }}
              py={3}
              bg="gray.50"
              _dark={{
                bg: "#121212",
              }}
              textAlign="right"
            >
              <Button
                type="submit"
                colorScheme="brand"
                _focus={{
                  shadow: "",
                }}
                fontWeight="md"
              >
                Save
              </Button>
            </Box>
          </chakra.form>
        </GridItem>
      </SimpleGrid>
    </Box> */
        )}
  </Box>);
}

export default EditArtist