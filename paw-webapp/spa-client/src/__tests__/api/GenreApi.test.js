import {genreApi} from "../../api";

//TODO: TESTS


describe("getGenres()", () => {
  it("should return a list of genres", async () => {

    const genres = await genreApi.getGenres();
    console.log(genres);
  });
});

describe("getGenres() with params", () => {
    it("should return a list of genres", async () => {
  
      // Call the getGenres() function with a params object
      const genres = await genreApi.getGenres({ auditionId: 4, userId: 2 });
      console.log(genres);
    });
  });
  

describe("getGenreById()", () => {
  it("should return a genre by its ID", async () => {

    // Call the getGenreById() function
    const genre = await genreApi.getGenreById(2);
    console.log(genre);
  });
});
