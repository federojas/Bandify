import { createContext, useContext } from "react";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import LocationServiceTest from "../services/LocationServiceTest";
import LocationApiTest from "../api/LocationApiTest";

const LocationServiceContext = createContext<LocationServiceTest>(null!);

export const useLocationService = () => useContext(LocationServiceContext);

export const LocationServiceProvider = ( { children }: { children: React.ReactNode }) => {
    const axiosPrivate = useAxiosPrivate();

    const locationApi = new LocationApiTest(axiosPrivate);
    const locationService = new LocationServiceTest(locationApi);

    return (
        <LocationServiceContext.Provider value={locationService} >
            {children}
        </LocationServiceContext.Provider>
    )
}