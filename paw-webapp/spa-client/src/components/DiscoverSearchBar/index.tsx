import "../../styles/searchBar.css";
import * as React from "react";
import { useState } from "react";
import { useTranslation } from "react-i18next";

const SearchForm: React.FC = () => {
  const [query, setQuery] = useState<string>("");
  const [selectedLocation, setSelectedLocation] = useState<string[]>([]);
  const [selectedGenre, setSelectedGenre] = useState<string[]>([]);
  const [selectedRole, setSelectedRole] = useState<string[]>([]);

  const locationList: string[] = ['CABA', 'Rosario', 'Cordoba'];
  const genreList: string[] = ['Rock', 'Jazz', 'Hip Hop'];
  const roleList: string[] = ['Baterista', 'Cantante'];

  const handleSearch = (event: React.FormEvent<HTMLFormElement>) => {
    // prevent form submission
    event.preventDefault();

    // perform search using the form values
  };
  const { t } = useTranslation();
  return (
    <div className="search-general-div">
      <form
        action="/users/search"
        method="get"
        className="searchForm"
        onSubmit={handleSearch}
      >
        <div className="searchBarAndOrderBy">
          <div className="search">
            <input
              id="inputfield"
              type="text"
              maxLength={80}
              size={43}
              placeholder={t("DiscoverSearchBar.searchUsers")}
              name="query"
              value={query}
              onChange={(event) => setQuery(event.target.value)}
            />
          </div>
        </div>
        <div className="filters">
          <div className="filter-by">
            <b>
              <p>{t("DiscoverSearchBar.filter")}</p>
            </b>
          </div>
          <div>
            <select
              id="location-filter"
              multiple
              name="location"
              value={selectedLocation}
              onChange={(event) => {
                const options = event.target.options;
                const selectedValues = [];
                for (let i = 0; i < options.length; i++) {
                  if (options[i].selected) {
                    selectedValues.push(options[i].value);
                  }
                }
                setSelectedLocation(selectedValues);
              }}
            >
              <option disabled selected>
              {t("DiscoverSearchBar.location")}
              </option>
              {locationList.map((location, index) => (
                <option key={index} value={location}>
                  {location}
                </option>
              ))}
            </select>
          </div>
          <div>
            <select
              id="genre-filter"
              multiple
              name="genre"
              value={selectedGenre}
              onChange={(event) => {
                const options = event.target.options;
                const selectedValues = [];
                for (let i = 0; i < options.length; i++) {
                  if (options[i].selected) {
                    selectedValues.push(options[i].value);
                  }
                }
                setSelectedGenre(selectedValues);
              }}
            >
              <option disabled selected>
              {t("DiscoverSearchBar.genres")}
              </option>
              {genreList.map((genre, index) => (
                <option key={index} value={genre}>
                  {genre}
                </option>
              ))}
            </select>
          </div>
          <div>
            <select
              id="role-filter"
              multiple
              name="role"
              value={selectedRole}
              onChange={(event) => {
                const options = event.target.options;
                const selectedValues = [];
                for (let i = 0; i < options.length; i++) {
                  if (options[i].selected) {
                    selectedValues.push(options[i].value);
                  }
                }
                setSelectedRole(selectedValues);
              }}
            >
              <option disabled selected>
              {t("DiscoverSearchBar.roles")}
              </option>
              {roleList.map((role, index) => (
                <option key={index} value={role}>
                  {role}
                </option>
              ))}
            </select>
          </div>
        </div>
        <div className="search-button-container">
          <button className="search-button" type="submit">
          {t("DiscoverSearchBar.search")}
          </button>
        </div>
      </form>
    </div>
  );
};

export default SearchForm;
