import GenreApi from "../../api/GenreApi";

describe("GenreApi", () => {

    test("getGenres", async () => {
        return GenreApi.getGenres().then((genres) => {
            console.log(genres)
            // expect(genres.data.length).toBeGreaterThan(0);
        });
    });

    test("getGenres by auditionId", async () => {
        return GenreApi.getGenres({auditionId: 1}).then((genres) => {
            console.log(genres.data)
            // expect(genres.data[0].auditionId).toEqual(1);
        });
    })

    test("getGenres by userId", async () => {
        return GenreApi.getGenres({userId: 1}).then((genres) => {
            console.log(genres.data)
            // expect(genres.data[0].auditionId).toEqual(1);
        });
    })

    test("getGenreById", async () => {
        return GenreApi.getGenreById(1).then((genre) => {
            console.log(genre)
            // expect(genre.data.id).toEqual(1);
        });
    })
});