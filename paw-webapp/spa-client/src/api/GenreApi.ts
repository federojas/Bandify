import { AxiosInstance } from "axios";
import Genre from "./types/Genre";



interface Params {
    auditionId?: number;
    userId?: number;
}

class GenreApi {

    private axiosPrivate: AxiosInstance;

    constructor(axiosPrivate: AxiosInstance) {
        this.axiosPrivate = axiosPrivate;
    }
    
    private endpoint: string = "/genres";

    public getGenres = async (params: Params = {}) => {
        return this.axiosPrivate
            .get(this.endpoint, {
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

    public getGenreById = async (id: number) => {
        // Call the API to get the genre data
        return this.axiosPrivate.get(`${this.endpoint}/${id}`).then((response) => {
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
};

export default GenreApi;
