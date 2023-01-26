import api from './api';
import {Audition, AuditionInput} from './types/Audition';

interface Params {
    auditionId?: number;
    userId?: number;
}

class AuditionApi {
    private endpoint: string = '/auditions';

    public getAuditions = async (page: number, query?: string, roles?: string[], genres?: string[], locations?: string[], bandId?: number) => {
        return api
            .get(this.endpoint, {
                params: {
                    page: page,
                    query: query,
                    genre: genres,
                    role: roles,
                    location: locations,
                    bandId: bandId
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
                              owner: audition.owner
                          };
                      })
                    : [];
                return Promise.resolve(auditions);
            });
    };

    public getAuditionById = async (id: number) => {
        // Call the API to get the audition data
        return api.get(`${this.endpoint}/${id}`).then((response) => {
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
                owner: data.owner
            };

            // Return a new promise that resolves with the audition object
            return Promise.resolve(audition);
        });
    };

    public createAudition = async (input: AuditionInput) => {
        return api.post(this.endpoint, input).then((response) => {
            console.log(response);

            return null;
            // return Promise.resolve(audition);
        }).catch((error) => {
            console.log(error.response.data);
            return null;
        });
    }

    public editAudition = async (id: number, input: AuditionInput) => {
        return api.put(`${this.endpoint}/${id}`, input).then((response) => {
            console.log(response);

            return null;
            // return Promise.resolve(audition);
        }).catch((error) => {
            console.log(error.response.data);
            return null;
        });
    }

}

export default AuditionApi;