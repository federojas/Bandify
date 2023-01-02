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

//TODO: REVISAR SI HAY QUE TRANSLATEAR ESTOS O NO
const locationOptions: LocationGroup[] = [
  { value: "CABA", label: "CABA" },
  { value: "Buenos Aires", label: "Buenos Aires" },
  { value: "Rosario", label: "Rosario" },
  { value: "Cordoba", label: "Cordoba" },
  { value: "Mendoza", label: "Mendoza" },
];

const genreOptions: GenreGroup[] = [
  { value: "Rock", label: "Rock" },
  { value: "Pop", label: "Pop" },
  { value: "Jazz", label: "Jazz" },
  { value: "Blues", label: "Blues" },
  { value: "Folk", label: "Folk" },
];

const roleOptions: RoleGroup[] = [
  { value: "Vocalista", label: "Vocalista" },
  { value: "Guitarrista", label: "Guitarrista" },
  { value: "Bajista", label: "Bajista" },
  { value: "Baterista", label: "Baterista" },
];

interface LocationGroup extends OptionBase {
  label: string;
  value: string;
}

interface GenreGroup extends OptionBase {
  label: string;
  value: string;
}

interface RoleGroup extends OptionBase {
  label: string;
  value: string;
}

const SearchForm: React.FC = () => {
  const [input, setInput] = React.useState("");
  const [locations, setLocations] = React.useState<LocationGroup[]>([]);
  const [genres, setGenres] = React.useState<GenreGroup[]>([]);
  const [roles, setRoles] = React.useState<RoleGroup[]>([]);

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    // prevent form submission
    event.preventDefault();

    // perform search using the form values
  };
  const { t } = useTranslation();

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
            <FormLabel>{t("AuditionSearchBar.search")}</FormLabel>

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

export default SearchForm;
