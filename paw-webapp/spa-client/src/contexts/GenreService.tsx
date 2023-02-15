import { createContext, useContext } from "react";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import GenreService from "../services/GenreService";
import GenreApi from "../api/GenreApi";

const GenreServiceContext = createContext<GenreService>(null!);

export const useGenreService = () => useContext(GenreServiceContext);

export const GenreServiceProvider = ( { children }: { children: React.ReactNode }) => {
    const axiosPrivate = useAxiosPrivate();

    const genreApi = new GenreApi(axiosPrivate);
    const genreService = new GenreService(genreApi);

    return (
        <GenreServiceContext.Provider value={genreService} >
            {children}
        </GenreServiceContext.Provider>
    )
}