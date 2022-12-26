import React from "react";
import "../../styles/searchBar.css";
import {
  Container,
  FormControl,
  FormLabel,
  Code,
  Link,
  FormErrorMessage,
} from "@chakra-ui/react";
import {
  Select,
  CreatableSelect,
  AsyncSelect,
  OptionBase,
  GroupBase,
} from "chakra-react-select";

const locationOptions = [
  {
    label: "Location",
    options: [
      { value: "CABA", label: "CABA" },
      { value: "Buenos Aires", label: "Buenos Aires" },
      { value: "Rosario", label: "Rosario" },
      { value: "Cordoba", label: "Cordoba" },
      { value: "Mendoza", label: "Mendoza" },
    ],
  },
];

const genreOptions = [
  {
    label: "Genre",
    options: [
      { value: "Rock", label: "Rock" },
      { value: "Pop", label: "Pop" },
      { value: "Jazz", label: "Jazz" },
      { value: "Blues", label: "Blues" },
      { value: "Folk", label: "Folk" },
    ],
  },
];

const roleOptions = [
  {
    label: "Role",
    options: [
      { value: "Vocalista", label: "Vocalista" },
      { value: "Guitarrista", label: "Guitarrista" },
      { value: "Bajista", label: "Bajista" },
      { value: "Baterista", label: "Baterista" },
    ],
  },
];

const orderByOptions = [
  {
    label: "Order by",
    options: [
      { value: "ASC", label: "Ascending" },
      { value: "DESC", label: "Descending" },
    ],
  },
];

interface FlavorOrColorOption extends OptionBase {
  label: string;
  value: string;
  color?: string;
  rating?: string;
}

const AuditionSearchBar = () => {
  const search = () => {
    // TODO: Add code to search for auditions
  };

  return (
    <div className="search-general-div">
      <form action="/auditions/search" method="get" className="searchForm">
        <div className="searchBarAndOrderBy">
          <div className="search auditionSearchBar-1">
            <input
              id="inputfield"
              type="text"
              maxLength={80}
              size={43}
              placeholder="Search"
              name="query"
            />
          </div>
          <div className="auditionSearchBar-2">
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
        
        <FormControl p={2}>
          <FormLabel>Location</FormLabel>
          <Select<FlavorOrColorOption, true, GroupBase<FlavorOrColorOption>>
            isMulti
            name="locations"
            options={locationOptions}
            placeholder="Select a Location..."
            closeMenuOnSelect={false}
            variant="filled"
            tagVariant="solid"
          />
        </FormControl>
        <FormControl p={2}>
          <FormLabel>Genre</FormLabel>
          <Select<FlavorOrColorOption, true, GroupBase<FlavorOrColorOption>>
            isMulti
            name="genres"
            options={genreOptions}
            placeholder="Select a Genre..."
            closeMenuOnSelect={false}
            variant="filled"
            tagVariant="solid"
          />
        </FormControl>
        <FormControl p={2}>
          <FormLabel>Role</FormLabel>
          <Select<FlavorOrColorOption, true, GroupBase<FlavorOrColorOption>>
            isMulti
            name="roles"
            options={roleOptions}
            placeholder="Select a Role..."
            closeMenuOnSelect={false}
            variant="filled"
            tagVariant="solid"
          />
        </FormControl>
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
        <div className="search-button-container">
          <button className="search-button" onClick={search}>
            Search
          </button>
        </div>
      </form>
    </div>
  );
};

export default AuditionSearchBar;
