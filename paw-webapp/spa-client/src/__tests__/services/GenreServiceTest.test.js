import {genreService} from "../../services";

//TODO: TESTS

describe("getGenres()", () => {
    it("should return a list of genres", async () => {

        const genres = await genreService.getGenres();
        console.log(genres);
    });
});

describe("getGenreById()", () => {
    it("should return a genre", async () => {

        const genre = await genreService.getGenreById(2);
        console.log(genre);
    });
});