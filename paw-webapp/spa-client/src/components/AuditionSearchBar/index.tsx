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
} from "@chakra-ui/react";
import {
  Select,
  CreatableSelect,
  AsyncSelect,
  OptionBase,
  GroupBase,
} from "chakra-react-select";

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

  return (
    <div className="search-general-div">
      <form onSubmit={handleSubmit}>
        <FormControl p={2}>
          {/* <form action="/auditions/search" method="get" className="searchForm"> */}
          <div className="searchBarAndOrderBy">
            {/* <div className="search auditionSearchBar-1">
            <input
              id="inputfield"
              type="text"
              maxLength={80}
              size={43}
              placeholder="Search"
              name="query"
            />
          </div> */}
            <div className="auditionSearchBar-1">
              <FormLabel>Search</FormLabel>

              <Input
                type="text"
                placeholder="Search"
                name="query"
                variant="filled"
                onChange={(event) => {
                  setInput(event.target.value);
                }}
              />
            </div>
            <div className="auditionSearchBar-2">
              <FormLabel>Order by</FormLabel>
              <Select
                name="orderBy"
                options={orderByOptions}
                placeholder="Order by..."
                size="md"
                className="orderBy"
                variant="filled"
              />
            </div>

            {/* <div id="orderBy-filter" className="orderBy">
            <select name="order">
              <option value="DESC">Descending</option>
              <option value="ASC">Ascending</option>
            </select>
          </div> */}
          </div>

          <FormLabel>Location</FormLabel>
          <Select<LocationGroup, true, GroupBase<LocationGroup>>
            isMulti
            name="locations"
            options={locationOptions}
            placeholder="Select a Location..."
            closeMenuOnSelect={false}
            variant="filled"
            tagVariant="solid"
            onChange={(event) => {
              setLocations(event.flatMap((e) => e));
            }}
          />
          
          <FormLabel>Genre</FormLabel>
          <Select<GenreGroup, true, GroupBase<GenreGroup>>
            isMulti
            name="genres"
            options={genreOptions}
            placeholder="Select a Genre..."
            closeMenuOnSelect={false}
            variant="filled"
            tagVariant="solid"
            onChange={(event) => {
              setGenres(event.flatMap((e) => e));
            }}
          />
          <FormLabel>Role</FormLabel>
          <Select<RoleGroup, true, GroupBase<RoleGroup>>
            isMulti
            name="roles"
            options={roleOptions}
            placeholder="Select a Role..."
            closeMenuOnSelect={false}
            variant="filled"
            tagVariant="solid"
            onChange={(event) => {
              setRoles(event.flatMap((e) => e));
            }}
          />

          {/* <div className="filters">
          <div className="filter-by">
            <b>
              <p>Filters</p>
            </b>
          </div>
          <div>
            <select id="location-filter" multiple name="location">
              <option disabled>Location</option>
              {locationList.map((location, index) => (
                <option key={index} value={location}>
                  {location}
                </option>
              ))}
            </select>
          </div>
          <div>
            <select id="genre-filter" multiple name="genre">
              <option disabled>Genres</option>
              {genreList.map((genre, index) => (
                <option key={index} value={genre}>
                  {genre}
                </option>
              ))}
            </select>
          </div>
          <div>
            <select id="role-filter" multiple name="role">
              <option disabled>Roles</option>
              {roleList.map((role, index) => (
                <option key={index} value={role}>
                  {role}
                </option>
              ))}
            </select>
          </div>
        </div> */}
          <Button colorScheme="blue" type="submit">
            Search
          </Button>
          {/* <div className="search-button-container">
          <button className="search-button" onClick={search}>
            Search
          </button>
        </div> */}
        </FormControl>
      </form>

      {/* </form> */}
    </div>
  );
};

export default AuditionSearchBar;
