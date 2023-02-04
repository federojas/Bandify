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

const DiscoverSearchBar: React.FC = () => {

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    let searchParams = new URLSearchParams(location.search);
    searchParams.delete('location');
    searchParams.delete('genre');
    searchParams.delete('role');
    searchParams.delete('query');
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
      bgColor={useColorModeValue("gray.100", "gray.900")}
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
              placeholder={t("AuditionSearchBar.searchPlaceholder")}
              name="query"
              variant="filled"
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
    // <div className="search-general-div">
    //   <form
    //     action="/users/search"
    //     method="get"
    //     className="searchForm"
    //     onSubmit={handleSearch}
    //   >
    //     <div className="searchBarAndOrderBy">
    //       <div className="search">
    //         <input
    //           id="inputfield"
    //           type="text"
    //           maxLength={80}
    //           size={43}
    //           placeholder={t("DiscoverSearchBar.searchUsers")}
    //           name="query"
    //           value={query}
    //           onChange={(event) => setQuery(event.target.value)}
    //         />
    //       </div>
    //     </div>
    //     <div className="filters">
    //       <div className="filter-by">
    //         <b>
    //           <p>{t("DiscoverSearchBar.filter")}</p>
    //         </b>
    //       </div>
    //       <div>
    //         <select
    //           id="location-filter"
    //           multiple
    //           name="location"
    //           value={selectedLocation}
    //           onChange={(event) => {
    //             const options = event.target.options;
    //             const selectedValues = [];
    //             for (let i = 0; i < options.length; i++) {
    //               if (options[i].selected) {
    //                 selectedValues.push(options[i].value);
    //               }
    //             }
    //             setSelectedLocation(selectedValues);
    //           }}
    //         >
    //           <option disabled selected>
    //           {t("DiscoverSearchBar.location")}
    //           </option>
    //           {locationList.map((location, index) => (
    //             <option key={index} value={location}>
    //               {location}
    //             </option>
    //           ))}
    //         </select>
    //       </div>
    //       <div>
    //         <select
    //           id="genre-filter"
    //           multiple
    //           name="genre"
    //           value={selectedGenre}
    //           onChange={(event) => {
    //             const options = event.target.options;
    //             const selectedValues = [];
    //             for (let i = 0; i < options.length; i++) {
    //               if (options[i].selected) {
    //                 selectedValues.push(options[i].value);
    //               }
    //             }
    //             setSelectedGenre(selectedValues);
    //           }}
    //         >
    //           <option disabled selected>
    //           {t("DiscoverSearchBar.genres")}
    //           </option>
    //           {genreList.map((genre, index) => (
    //             <option key={index} value={genre}>
    //               {genre}
    //             </option>
    //           ))}
    //         </select>
    //       </div>
    //       <div>
    //         <select
    //           id="role-filter"
    //           multiple
    //           name="role"
    //           value={selectedRole}
    //           onChange={(event) => {
    //             const options = event.target.options;
    //             const selectedValues = [];
    //             for (let i = 0; i < options.length; i++) {
    //               if (options[i].selected) {
    //                 selectedValues.push(options[i].value);
    //               }
    //             }
    //             setSelectedRole(selectedValues);
    //           }}
    //         >
    //           <option disabled selected>
    //           {t("DiscoverSearchBar.roles")}
    //           </option>
    //           {roleList.map((role, index) => (
    //             <option key={index} value={role}>
    //               {role}
    //             </option>
    //           ))}
    //         </select>
    //       </div>
    //     </div>
    //     <div className="search-button-container">
    //       <button className="search-button" type="submit">
    //       {t("DiscoverSearchBar.search")}
    //       </button>
    //     </div>
    //   </form>
    // </div>
  );
};

export default DiscoverSearchBar;
