import {Audition, AuditionInput} from './types/Audition';
import {Application} from './types/Application';
// import useAxiosPrivate from './hooks/useAxiosPrivate';
import { AxiosInstance } from 'axios';

interface Params {
    auditionId?: number;
    userId?: number;
}

class AuditionApi {
    private axiosPrivate: AxiosInstance;

    constructor(axiosPrivate: AxiosInstance) {
      this.axiosPrivate = axiosPrivate;
    }

    private endpoint: string = '/auditions';

    //debug
    private config = {
        headers: {
            'Content-Type': 'application/vnd.audition.v1+json'
        }
    }
    // TODO: Mejorar
    private applicationConfig = {
        headers: {
            'Content-Type': 'application/vnd.application.v1+json'
        }
    }

    public getAuditions = async (page?: number, query?: string, roles?: string[], genres?: string[], locations?: string[], bandId?: number) => {
        return this.axiosPrivate
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
        // Call the this.axiosPrivate to get the audition data
        return this.axiosPrivate.get(`${this.endpoint}/${id}`).then((response) => {
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

    public getApplication = async (auditionId: number, applicationId: number) => {
        return this.axiosPrivate.get(`${this.endpoint}/${auditionId}/applications/${applicationId}`)
            .then((response) => {
            // Extract the data for the audition from the response
            const data = response.data;
            // Create a new object with the structure of a Audition object
            const application: Application = {
                id: data.id,
                state: data.state,
                creationDate: data.creationDate,
                message: data.message,
                self: data.self,
                audition: data.audition,
                applicant: data.applicant
            };

            // Return a new promise that resolves with the application object
            return Promise.resolve(application);
        });
    };

    public getApplications = async (auditionId: number, page?: number, state?: string) => {
        return this.axiosPrivate.get(`${this.endpoint}/${auditionId}/applications`, {
            params: {
                page: page,
                state: state
            },
        }).then((response) => {
            const data = response.data;
            const applications: Application[] = Array.isArray(data)
                ? data.map((app: any) => {
                    return {
                        id: app.id,
                        state: app.state,
                        creationDate: app.creationDate,
                        message: app.message,
                        self: app.self,
                        audition: app.audition,
                        applicant: app.applicant
                    };
                })
                : [];
            return Promise.resolve(applications);
        });
    };

    public createAudition = async (input: AuditionInput) => {
        // const axiosPrivate = useAxiosPrivate();
        return this.axiosPrivate.post(this.endpoint, input, this.config).then((response) => {
            return Promise.resolve(response);
        });
    }

    public editAudition = async (id: number, input: AuditionInput) => {
        return this.axiosPrivate.put(`${this.endpoint}/${id}`, input, this.config).then((response) => {
            console.log(response);
            return true;
        }).catch((error) => {
            console.log(error.response.data);
            return false;
        });
    }

    public apply = async(auditionId:number, message: string) => {
        return this.axiosPrivate.post(`${this.endpoint}/${auditionId}/applications`,
        {message: message},this.applicationConfig).then((response) => {
            return Promise.resolve(response);
        });
    }

}

export default AuditionApi;