import {genreApi} from "../api";
import {Genre} from "../models";

export class GenreService {
    public async getGenres(): Promise<Genre[] | undefined>{
        try {
            const response = await genreApi.getGenres();
            return response.map(r => { const genre: Genre = {id: r.id, name: r.genreName}; return genre });
        } catch (error) {
            console.log("error genres");
        }
    }


    public async getRoleById(id : number): Promise<Genre | undefined> {
        try {
            const current = await genreApi.getGenreById(id);
            return {id:current.id, name:current.genreName} as Genre;
        } catch (error) {
            console.log("error genres");
        }
    }
}