import api from "./api";
import Location from "./types/Location";

interface Params {
    auditionId?: number;
    userId?: number;
}

class LocationApi {
  private endpoint: string = "/locations";

  public getLocations = async (params: Params = {}) => {
    return api
      .get(this.endpoint, {
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

  public getLocationById = async (id: number) => {
    return api.get(`${this.endpoint}/${id}`).then((response) => {
        const data = response.data;
        const location: Location = {
            id: data.id,
            locName: data.locName,
            self: data.self,
        };
    
        return Promise.resolve(location);
    });
  };
};

export default LocationApi;
