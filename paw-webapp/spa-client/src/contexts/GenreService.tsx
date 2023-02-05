import { createContext, useContext } from "react";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import GenreService from "../services/GenreService";
import GenreApiTest from "../api/GenreApiTest";

const GenreServiceContext = createContext<GenreService>(null!);

export const useGenreService = () => useContext(GenreServiceContext);

export const GenreServiceProvider = ( { children }: { children: React.ReactNode }) => {
    const axiosPrivate = useAxiosPrivate();

    const genreApi = new GenreApiTest(axiosPrivate);
    const genreService = new GenreService(genreApi);

    return (
        <GenreServiceContext.Provider value={genreService} >
            {children}
        </GenreServiceContext.Provider>
    )
}