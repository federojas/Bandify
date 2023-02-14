import axios from "axios";
import GenreService from "../../services/GenreService";
import GenreApi from "../../api/GenreApi";
import { genre1, genre1Response, genre2, genre2Response } from "../__mocks__";

jest.mock("axios");

const genreApi = new GenreApi(axios);
const genreService = new GenreService(genreApi);

afterEach(() => {(axios.get as any).mockClear();});
afterEach(() => {(axios.post as any).mockClear();});
afterEach(() => {(axios.put as any).mockClear();});
afterEach(() => {(axios.delete as any).mockClear();});

const axiosGet = (axios.get as any);

describe("getGenres()", () => {
    it("should return genres", async () => {
        
        axiosGet.mockResolvedValue({ data: [genre1Response, genre2Response] });
        
        await genreService.getGenres().then((result) => {

            expect(result.getData()).toEqual([genre1, genre2]);
            expect(axiosGet).toHaveBeenCalledTimes(1);
            expect(axiosGet).toHaveBeenCalledWith("/genres",
            {params:{audition:undefined,user:undefined}}
            );
        });
    });

    it("should return error", async () => {
        axiosGet.mockRejectedValue({response: {data: {status: 400, title: "Bad Request"}}});
        await genreService.getGenres().then((result) => {
            expect(result.hasFailed()).toEqual(true);
            expect(axiosGet).toHaveBeenCalledTimes(1);
        });
    });
});

describe("getGenreById()", () => {
    it("should return genre", async () => {
        axiosGet.mockResolvedValue({ data: genre1Response });
        await genreService.getGenreById(1).then((result) => {
            expect(result.getData()).toEqual(genre1);
            expect(axiosGet).toHaveBeenCalledTimes(1);
            expect(axiosGet).toHaveBeenCalledWith("/genres/1");
        });
    });

    it("should return error", async () => {
        axiosGet.mockRejectedValue({response: {data: {status: 400, title: "Bad Request"}}});
        await genreService.getGenreById(1).then((result) => {
            expect(result.hasFailed()).toEqual(true);
            expect(axiosGet).toHaveBeenCalledTimes(1);
        });
    });
});