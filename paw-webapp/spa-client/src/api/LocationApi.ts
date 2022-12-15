import api from "./api";
import Location from "./types/Location";

const LocationApi = (() => {
  const endpoint = "/locations";

  interface Params {
    auditionId?: number;
    userId?: number;
  }

  const getLocations = (params: Params = {}) => {
    return api
      .get(endpoint, {
        params: {
          audition: params.auditionId,
          user: params.userId,
        },
      })
      .then((response) => {
        const data = response.data;
        const locations: Location[] = Array.isArray(data)
          ? data.map((location: any) => {
              return {
                id: location.id,
                locName: location.locName,
                self: location.self,
              };
            })
          : [];
        return Promise.resolve(locations);
      });
  };

  const getLocationById = (id: number) => {
    return api.get(`${endpoint}/${id}`).then((response) => {
        const data = response.data;
        const location: Location = {
            id: data.id,
            locName: data.locName,
            self: data.self,
        };
    
        return Promise.resolve(location);
    });
  };

  return {
    getLocations,
    getLocationById,
  };
})();

export default LocationApi;
