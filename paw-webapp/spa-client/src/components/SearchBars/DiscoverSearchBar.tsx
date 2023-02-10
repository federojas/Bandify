import "../../styles/searchBar.css";
import * as React from "react";
import { useState } from "react";
import { useTranslation } from "react-i18next";
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
import { SearchIcon } from "@chakra-ui/icons";
import { useNavigate, useLocation } from "react-router-dom";
import { serviceCall } from "../../services/ServiceManager";
import { useEffect } from "react";
import { LocationGroup, GenreGroup, RoleGroup } from "./EntitiesGroups";
import {getQueryOrDefault, getQueryOrDefaultArray, useQuery} from "../../hooks/useQuery";
import {useGenreService} from "../../contexts/GenreService";
import {useRoleService} from "../../contexts/RoleService";
import {useLocationService} from "../../contexts/LocationService";
interface SearchParams {
  searchTerms?: string;
  roles?: string[];
  genres?: string[];
  locations?: string[];
}
interface DiscoverSearchBarProps {
  onSubmit?: (searchParams: SearchParams) => void;
}

const DiscoverSearchBar = ({ onSubmit = () => {} }: DiscoverSearchBarProps) => {

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    let searchParams = new URLSearchParams(location.search);
    searchParams.delete('location');
    searchParams.delete('genre');
    searchParams.delete('role');
    searchParams.delete('query');
    searchParams.delete('page');
    if(input && input.length > 0)
      searchParams.set('query', input);
    if(locations && locations.length > 0)
      locations.map((location) => searchParams.append('location', location.value));
    if(genres && genres.length > 0)
      genres.map((genre) => searchParams.append('genre', genre.value));
    if(roles && roles.length > 0)
      roles.map((role) => searchParams.append('role', role.value));
    navigate( {
          pathname: "/users/search",
          search: searchParams.toString(),
        }
    );
    const searchParameters = {
      searchTerms: input,
      roles: roles.map((role) => role.value),
      genres: genres.map((genre) => genre.value),
      locations: locations.map((location) => location.value),
    };
    onSubmit && onSubmit(searchParameters);
};

  const { t } = useTranslation();
  const navigate = useNavigate();
  const location = useLocation();
  const [locationOptions, setLocationOptions] = React.useState<LocationGroup[]>([]);
  const [genreOptions, setGenreOptions] = React.useState<GenreGroup[]>([]);
  const [roleOptions, setRoleOptions] = React.useState<RoleGroup[]>([]);
  const [filters, setFilters] = React.useState(false);
  const genreService = useGenreService();
  const roleService = useRoleService();
  const locationService = useLocationService();
  const query = useQuery();
  const [input, setInput] = React.useState<string>(getQueryOrDefault(query, "query", ""));
  const [locations, setLocations] = React.useState<LocationGroup[]>(getQueryOrDefaultArray(query, "location").map(l => { return { value: l, label: l }} ));
  const [genres, setGenres] = React.useState<GenreGroup[]>(getQueryOrDefaultArray(query, "genre").map(g => { return { value: g, label: g }} ));
  const [roles, setRoles] = React.useState<RoleGroup[]>(getQueryOrDefaultArray(query, "role").map(r => { return { value: r, label: r }} ));


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

  return (
    <Box
      bgColor={useColorModeValue("white", "gray.900")}
      rounded="lg"
      p={5}
      boxShadow="xl"
      w={'2xl'}
    >
      <form onSubmit={handleSubmit}>
        <VStack w={"full"} p={2} spacing={3}>
          <Container>
            <FormLabel>{t("DiscoverSearchBar.searchUsers")}</FormLabel>

            <Input
              type="text"
              placeholder={t("DiscoverSearchBar.searchPlaceholder")}
              name="query"
              variant="filled"
              defaultValue={input}
              onChange={(event) => {
                setInput(event.target.value);
              }}
            />
          </Container>

          <>
            <Container>
              <FormLabel>{t("AuditionSearchBar.location")}</FormLabel>
              <Select<LocationGroup, true, GroupBase<LocationGroup>>
                isMulti
                name="locations"
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
                name="genres"
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
                name="roles"
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

export default DiscoverSearchBar;
