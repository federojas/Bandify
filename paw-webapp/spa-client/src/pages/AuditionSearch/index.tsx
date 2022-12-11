import "../../styles/welcome.css";
import "../../styles/auditions.css";
import NextIcon from '../../assets/icons/page-next.png';
import PostCard from "../../components/PostCard/PostCard";
import AuditionSearchBar from "../../components/AuditionSearchBar";

type Audition = {
  band: {
    name: string;
    id: number;
  };
  id: number;
  creationDate: Date;
  title: string;
  roles: string[];
  genres: string[];
  location: string;
};

// type AuditionListPaginated = {
//   auditionList: Audition[];
//   currentPage: number;
//   totalPages: number;
// };

const AuditionSearch = () => {
  const auditionList = {
    auditionList: [
      {
        band: {
          name: "My Band",
          id: 1,
        },
        id: 1,
        creationDate: new Date(),
        title: "My Band is looking for a drummer",
        roles: ['Drummer'],
        genres: ['Rock'],
        location: "Buenos Aires",

      },
      {
        band: {
          name: "My Band",
          id: 1,
        },
        id: 2,
        creationDate: new Date(),
        title: "My Band is looking for a guitarist",
        roles: ['Guitarist'],
        genres: ['Rock'],
        location: "Buenos Aires",

      },
    ],
    currentPage: 1,
    totalPages: 1,
  };

  const getPaginationURL = (page: number) => {
    // TODO: Add code to get pagination URL
  };

  return (
    <div className="auditions-content">
      <h2 className="black-title">Search</h2>

      <AuditionSearchBar />

      <div className="posts">
        {auditionList.auditionList.length === 0 && (
          <b>
            <p className="no-results">No results found</p>
          </b>
        )}
        {auditionList.auditionList.map((audition, index) => (
          <PostCard
              auditionTitle={audition.title}
              bandName={audition.band.name}
              location={audition.location}
              roles={audition.roles}
              genres={audition.genres}
          />
        ))}
      </div>
      <div className="pagination">
        {auditionList.currentPage > 1 && (
          <a onClick={() => getPaginationURL(auditionList.currentPage - 1)}>
            <img
              src={NextIcon}
              alt="previous"
              className="pagination-next rotate"
            />
          </a>
        )}
        <b>
          Page {auditionList.currentPage} of {auditionList.totalPages}
        </b>
        {auditionList.currentPage < auditionList.totalPages && (
          <a onClick={() => getPaginationURL(auditionList.currentPage + 1)}>
            <img src={NextIcon} alt="next" className="pagination-next" />
          </a>
        )}
      </div>
    </div>
  );
};

export default AuditionSearch;
