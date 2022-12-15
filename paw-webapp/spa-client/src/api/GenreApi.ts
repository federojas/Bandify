import api from "./api";
import Genre from "./types/Genre";

const GenreApi = (() => {
  const endpoint = "/genres";

  interface Params {
    auditionId?: number;
    userId?: number;
  }

  const getGenres = (params: Params | undefined) => {
    if (!params) {
      return api.get(endpoint).then((response) => {
        const data = response.data;
        const genres: Genre[] = Array.isArray(data)
          ? data.map((genre: any) => {
              return {
                id: genre.id,
                genreName: genre.genreName,
                self: genre.self,
              };
            })
          : [];
        return Promise.resolve(genres);
      });
    }

    return api
      .get(endpoint, {
        params: {
          audition: params.auditionId,
          user: params.userId,
        },
      })
      .then((response) => {
        const data = response.data;

        const genres: Genre[] = Array.isArray(data)
          ? data.map((genre: any) => {
              return {
                id: genre.id,
                genreName: genre.genreName,
                self: genre.self,
              };
            })
          : [];
        return Promise.resolve(genres);
      });
  };

  const getGenreById = (id: number) => {
    // Call the API to get the genre data
    return api.get(`${endpoint}/${id}`).then((response) => {
      // Extract the data for the genre from the response
      const data = response.data;
      // Create a new object with the structure of a Genre object
      const genre: Genre = {
        id: data.id,
        genreName: data.genreName,
        self: data.self,
      };

      // Return a new promise that resolves with the genre object
      return Promise.resolve(genre);
    });
  };

  return {
    getGenres,
    getGenreById,
  };
})();

export default GenreApi;
