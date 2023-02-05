import { createContext, useContext } from "react";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import LocationService from "../services/LocationService";
import LocationApiTest from "../api/LocationApiTest";

const LocationServiceContext = createContext<LocationService>(null!);

export const useLocationService = () => useContext(LocationServiceContext);

export const LocationServiceProvider = ( { children }: { children: React.ReactNode }) => {
    const axiosPrivate = useAxiosPrivate();

    const locationApi = new LocationApiTest(axiosPrivate);
    const locationService = new LocationService(locationApi);

    return (
        <LocationServiceContext.Provider value={locationService} >
            {children}
        </LocationServiceContext.Provider>
    )
}