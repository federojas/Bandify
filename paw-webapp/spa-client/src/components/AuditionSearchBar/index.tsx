import React from "react";
import "../../styles/searchBar.css";

const AuditionSearchBar = () => {
  const locationList = [
    "CABA",
    "Buenos Aires",
    "Rosario",
    "Cordoba",
    "Mendoza",
    "San Juan",
    "San Luis",
    "Santa Fe",
    "Tucuman",
    "Chaco",
    "Chubut",
    "Corrientes",
    "Entre Rios",
    "Formosa",
    "Jujuy",
    "La Pampa",
    "La Rioja",
    "Misiones",
  ];
  const genreList = [
    "Rock",
    "Pop",
    "Jazz",
    "Blues",
    "Folk",
    "Reggae",
    "Soul",
    "Rap",
    "Hip Hop",
    "Metal",
    "Punk",
    "Electronica",
    "Funk",
    "Reggaeton",
    "Salsa",
    "Cumbia",
    "Tango",
    "Clasica",
    "Country",
    "Indie",
    "Folklore",
    "Otros",
  ];
  const roleList = [
    "Vocalista",
    "Guitarrista",
    "Bajista",
    "Baterista",
    "Tecladista",
    "Pianista",
    "Violinista",
    "Saxofonista",
    "Flautista",
    "Trompetista",
    "Trombonista",
    "Otro",
  ];

  const search = () => {
    // TODO: Add code to search for auditions
  };

  return (
    <div className="search-general-div">
      <form action="/auditions/search" method="get" className="searchForm">
        <div className="searchBarAndOrderBy">
          <div className="search">
            <input
              id="inputfield"
              type="text"
              maxLength={80}
              size={43}
              placeholder="Search"
              name="query"
            />
          </div>
          <div id="orderBy-filter" className="orderBy">
            <select name="order">
              <option value="DESC">Descending</option>
              <option value="ASC">Ascending</option>
            </select>
          </div>
        </div>
        <div className="filters">
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
        </div>
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
