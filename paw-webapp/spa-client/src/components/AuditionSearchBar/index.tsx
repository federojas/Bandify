import React from "react";
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
import { Search2Icon, SearchIcon } from "@chakra-ui/icons";

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

const orderByOptions = [
  { value: "ASC", label: "Ascending" },
  { value: "DESC", label: "Descending" },
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

const AuditionSearchBar = () => {
  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    // console.log(input, locations, genres, roles);
    const query = input;
    const locationsQuery = locations.map((location) => location.value);
    const genresQuery = genres.map((genre) => genre.value);
    const rolesQuery = roles.map((role) => role.value);
    console.log(query, locationsQuery, genresQuery, rolesQuery);
    const queryString = `query=${query}&locations=${locationsQuery}&genres=${genresQuery}&roles=${rolesQuery}`;
    window.location.href = `/auditions/search?${queryString}`;
  };

  const [input, setInput] = React.useState("");
  const [locations, setLocations] = React.useState<LocationGroup[]>([]);
  const [genres, setGenres] = React.useState<GenreGroup[]>([]);
  const [roles, setRoles] = React.useState<RoleGroup[]>([]);
  const [filters, setFilters] = React.useState(false);
  const { t } = useTranslation();

  return (
    <Box
      bgColor={useColorModeValue("gray.100", "gray.900")}
      rounded="lg"
      p={5}
      boxShadow="xl"
    >
      <form onSubmit={handleSubmit}>
        <VStack w={"full"} p={2} spacing={3}>
          <div className="searchBarAndOrderBy">
            <div className="auditionSearchBar-1">
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
            </div>
            <div className="auditionSearchBar-2">
              <FormLabel>{t("AuditionSearchBar.orderByPlaceholder")}</FormLabel>
              <Select
                name="orderBy"
                options={orderByOptions}
                placeholder={t("AuditionSearchBar.orderByPlaceholder")}
                size="md"
                className="orderBy"
                variant="filled"
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
