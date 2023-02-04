import React, { useEffect } from "react";
import "../../styles/searchBar.css";
import {
  Container,
  FormControl,
  FormLabel,
  Code,
  Link,
  FormErrorMessage,
  Input,
  Button,
  Box,
  useColorModeValue,
  HStack,
  VStack,
  Flex,
  Text,
} from "@chakra-ui/react";
import {
  Select,
  CreatableSelect,
  AsyncSelect,
  OptionBase,
  GroupBase,
} from "chakra-react-select";
import { useTranslation } from "react-i18next";
import { SearchIcon } from "@chakra-ui/icons";
import { serviceCall } from "../../services/ServiceManager";
import {useRoleService} from "../../contexts/RoleService";
import {useGenreService} from "../../contexts/GenreService";
import {useLocationService} from "../../contexts/LocationService";
import {useLocation, useNavigate} from "react-router-dom";
import {LocationGroup, GenreGroup, RoleGroup, OrderGroup} from "./EntitiesGroups";
import {getQueryOrDefault, getQueryOrDefaultArray, useQuery} from "../../hooks/useQuery";

// TODO: Move these to another folder
const AuditionSearchBar = () => {
  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    let searchParams = new URLSearchParams(location.search);
    searchParams.delete('location');
    searchParams.delete('genre');
    searchParams.delete('role');
    searchParams.delete('query');
    searchParams.delete('order');
    searchParams.delete('page');
    if(input && input.length > 0)
      searchParams.set('query', input);
    if(order && order.value.length > 0)
      searchParams.set('order', order.value);
    if(locations && locations.length > 0)
      locations.map((location) => searchParams.append('location', location.value));
    if(genres && genres.length > 0)
      genres.map((genre) => searchParams.append('genre', genre.value));
    if(roles && roles.length > 0)
      roles.map((role) => searchParams.append('role', role.value));
    navigate( {
          pathname: "/auditions/search",
          search: searchParams.toString(),
        }
    );
  };

  const { t } = useTranslation();
  const query = useQuery();
  const [input, setInput] = React.useState<string>(getQueryOrDefault(query, "query", ""));
  const [order, setOrder] = React.useState<OrderGroup>(() => {  return getQueryOrDefault(query, "order", "") === "" ? {value: "DESC", label: t("AuditionSearchBar.desc")} : { value: "ASC", label: t("AuditionSearchBar.asc") }});
  const [locations, setLocations] = React.useState<LocationGroup[]>(getQueryOrDefaultArray(query, "location").map(l => { return { value: l, label: l }} ));
  const [genres, setGenres] = React.useState<GenreGroup[]>(getQueryOrDefaultArray(query, "genre").map(g => { return { value: g, label: g }} ));
  const [roles, setRoles] = React.useState<RoleGroup[]>(getQueryOrDefaultArray(query, "role").map(r => { return { value: r, label: r }} ));
  const [filters, setFilters] = React.useState(false);
  const navigate = useNavigate();
  const location = useLocation();
  const genreService = useGenreService();
  const roleService = useRoleService();
  const locationService = useLocationService();
  const [locationOptions, setLocationOptions] = React.useState<LocationGroup[]>([]);
  const [genreOptions, setGenreOptions] = React.useState<GenreGroup[]>([]);
  const [roleOptions, setRoleOptions] = React.useState<RoleGroup[]>([]);

  const orderByOptions = [
    { value: "ASC", label: t("AuditionSearchBar.asc") },
    { value: "DESC", label: t("AuditionSearchBar.desc")},
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
  }, [locationService, navigate, genreService, roleService]);

  return (
    <Box
      bgColor={useColorModeValue("gray.100", "gray.900")}
      rounded="lg"
      p={5}
      boxShadow="xl"
    >
      <form onSubmit={handleSubmit} >
        <VStack w={"full"} p={2} spacing={3}>
          <div className="searchBarAndOrderBy">
            <div className="auditionSearchBar-1">
              <FormLabel>{t("AuditionSearchBar.search")}</FormLabel>

              <Input
                type="text"
                placeholder={t("AuditionSearchBar.searchPlaceholder")}
                name="query"
                variant="filled"
                defaultValue={input}
                onChange={(event) => {
                    setInput(event.target.value);
                }}
              />
            </div>
            <div className="auditionSearchBar-2">
              <FormLabel>{t("AuditionSearchBar.orderByPlaceholder")}</FormLabel>
              <Select
                name="order"
                options={orderByOptions}
                placeholder={t("AuditionSearchBar.orderByPlaceholder")}
                size="md"
                className="orderBy"
                variant="filled"
                defaultValue={order}
                onChange={(selection) => {
                  if(selection)
                    setOrder(selection);
                }}
              />
            </div>
          </div>
          <Text
            as='u'
            cursor='pointer'
            color={useColorModeValue("blue.500", "blue.200")}
            alignSelf='flex-start'
            onClick={() => setFilters(!filters)}>Filters</Text>

          {filters && (
            <>
              <Container>
                <FormLabel>{t("AuditionSearchBar.location")}</FormLabel>
                <Select<LocationGroup, true, GroupBase<LocationGroup>>
                  isMulti
                  name="location"
                  options={locationOptions}
                  placeholder={t("AuditionSearchBar.locationPlaceholder")}
                  closeMenuOnSelect={false}
                  variant="filled"
                  tagVariant="solid"
                  defaultValue={locations}
                  onChange={(event) => {
                      setLocations(event.flatMap((e) => e));
                  }}
                />
              </Container>

              <Container>
                <FormLabel>{t("AuditionSearchBar.genre")}</FormLabel>
                <Select<GenreGroup, true, GroupBase<GenreGroup>>
                  isMulti
                  name="genre"
                  options={genreOptions}
                  placeholder={t("AuditionSearchBar.genrePlaceholder")}
                  closeMenuOnSelect={false}
                  variant="filled"
                  tagVariant="solid"
                  defaultValue={genres}
                  onChange={(event) => {
                      setGenres(event.flatMap((e) => e));
                  }}
                />
              </Container>
              <Container>
                <FormLabel>{t("AuditionSearchBar.role")}</FormLabel>
                <Select<RoleGroup, true, GroupBase<RoleGroup>>
                  isMulti
                  name="role"
                  options={roleOptions}
                  placeholder={t("AuditionSearchBar.rolePlaceholder")}
                  closeMenuOnSelect={false}
                  variant="filled"
                  tagVariant="solid"
                  defaultValue={roles}
                  onChange={(event) => {
                      setRoles(event.flatMap((e) => e));
                  }}
                />
              </Container>
            </>
          )}

          <Container>
            <HStack justifyContent={"center"}>
              <Button
                leftIcon={<SearchIcon />}
                w={"35%"}
                colorScheme="blue"
                type="submit"
                marginTop={6}
                alignSelf={"center"}
              >
                {t("AuditionSearchBar.search")}
              </Button>
            </HStack>
          </Container>
        </VStack>
      </form>
    </Box>
  );
};

export default AuditionSearchBar;
