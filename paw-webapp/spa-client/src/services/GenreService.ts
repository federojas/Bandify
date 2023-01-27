import { genreApi } from "../api";
import ApiResult from "../api/types/ApiResult";
import ErrorResponse from "../api/types/ErrorResponse";
import { Genre } from "../models";

export class GenreService {
    public async getGenres(): Promise<ApiResult<Genre[]>> {
        try {
            const response = await genreApi.getGenres();
            return new ApiResult(
                response.map(r => { const genre: Genre = { id: r.id, name: r.genreName }; return genre }),
                false,
                null as any);
        } catch (error: any) {
            return new ApiResult(null as any,
                true, new ErrorResponse(error.response.data.status, error.response.data.title,
                    error.response.data.path, error.response.data.message));
        }
    }


    public async getGenreById(id: number): Promise<ApiResult<Genre>> {
        try {
            const current = await genreApi.getGenreById(id);
            return new ApiResult(
                { id: current.id, name: current.genreName } as Genre, false,
                null as any
            );
        } catch (error: any) {
            return new ApiResult(null as any,
                true, new ErrorResponse(error.response.data.status, error.response.data.title,
                    error.response.data.path, error.response.data.message));
        }
    }
}