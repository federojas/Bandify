import api from './api';
import {Audition, AuditionInput} from './types/Audition';

const AuditionApi = (() => {
    const endpoint = '/auditions';

    interface Params {
        auditionId?: number;
        userId?: number;
    }

    const getAuditions = (params: Params = {}) => {
        return api
            .get(endpoint, {
                params: {
                    audition: params.auditionId,
                    user: params.userId,
                },
            })
            .then((response) => {
                const data = response.data;
                const auditions: Audition[] = Array.isArray(data)
                    ? data.map((audition: any) => {
                          return {
                              id: audition.id,
                              title: audition.title,
                              description: audition.description,
                              creationDate: audition.creationDate,
                              location: audition.location,
                              lookingFor: audition.lookingFor,
                              musicGenres: audition.musicGenres,
                              applications: audition.applications,
                              self: audition.self,
                          };
                      })
                    : [];
                return Promise.resolve(auditions);
            });
    };

    const getAuditionById = (id: number) => {
        // Call the API to get the audition data
        return api.get(`${endpoint}/${id}`).then((response) => {
            // Extract the data for the audition from the response
            const data = response.data;
            // Create a new object with the structure of a Audition object
            const audition: Audition = {
                id: data.id,
                title: data.title,
                description: data.description,
                creationDate: data.creationDate,
                location: data.location,
                lookingFor: data.lookingFor,
                musicGenres: data.musicGenres,
                applications: data.applications,
                self: data.self,
            };

            // Return a new promise that resolves with the audition object
            return Promise.resolve(audition);
        });
    };

    const createAudition = (input: AuditionInput) => {
        return api.post(endpoint, input).then((response) => {
            console.log(response);

            return null;
            // return Promise.resolve(audition);
        }).catch((error) => {
            console.log(error.response.data);
            return null;
        });
    }

    const editAudition = (id: number, input: AuditionInput) => {
        return api.put(`${endpoint}/${id}`, input).then((response) => {
            console.log(response);

            return null;
            // return Promise.resolve(audition);
        }).catch((error) => {
            console.log(error.response.data);
            return null;
        });
    }

    return {
        createAudition,
        editAudition,
        getAuditions,
        getAuditionById,
    };
})();

export default AuditionApi;