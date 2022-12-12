import api from './api';

const LocationApi = (() => {
    const endpoint = '/locations';

    interface Params {
        auditionId?: number;
        userId?: number;
    }

    const getLocations = (params : Params | undefined) => {
        if (!params) {
            return api.get(endpoint);
        }

        return api.get(endpoint, {params: {
            audition: params.auditionId, 
            user: params.userId
        }});
    }

    const getLocationById = (id: number) => {
        return api.get(`${endpoint}/${id}`);
    }

    return {
        getLocations,
        getLocationById
    }
}
)();

export default LocationApi;