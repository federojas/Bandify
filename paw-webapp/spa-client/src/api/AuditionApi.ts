import {Audition, AuditionInput} from './types/Audition';
import {Application} from './types/Application';
import { AxiosInstance } from 'axios';
import PagedContent from "./types/PagedContent";
import {parseLinkHeader} from '@web3-storage/parse-link-header'

class AuditionApi {
    private axiosPrivate: AxiosInstance;

    constructor(axiosPrivate: AxiosInstance) {
      this.axiosPrivate = axiosPrivate;
    }

    private endpoint: string = '/auditions';

    private config = {
        headers: {
            'Content-Type': 'application/vnd.audition.v1+json'
        }
    }

    private applicationConfig = {
        headers: {
            'Content-Type': 'application/vnd.application.v1+json'
        }
    }

    public getAuditionById = async (id: number) => {
        return this.axiosPrivate.get(`${this.endpoint}/${id}`).then((response) => {
            const data = response.data;
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
            return Promise.resolve(audition);
        });
    };

    //TODO: codigo repetido
    public getAuditions = async (page?: number, query?: string, roles?: string[], genres?: string[], locations?: string[], order?: string) => {
        return this.axiosPrivate
            .get(this.endpoint, {
                params: {
                    page: page,
                    query: query,
                    role: roles,
                    genre: genres,
                    location: locations,
                    order: order
                },
                paramsSerializer: {
                    indexes: null
                }
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
                let maxPage = 1;
                let previousPage = {};
                let nextPage = {};
                let parsed;
                if(response.headers) {
                    parsed = parseLinkHeader(response.headers.link);
                    if(parsed) {
                        maxPage = parseInt(parsed.last.page);
                        if(parsed.prev)
                            previousPage = parsed.prev;
                        if(parsed.next)
                            nextPage = parsed.next;
                    }
                }
                return Promise.resolve(new PagedContent(auditions, maxPage, nextPage, previousPage));
            });
    };

    public getAuditionsByBandId = async (page?: number, bandId?: number) => {
        return this.axiosPrivate
            .get(this.endpoint, {
                params: {
                    page: page,
                    bandId: bandId,
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
                let maxPage = 1;
                let previousPage = {};
                let nextPage = {};
                let parsed;
                if(response.headers) {
                    parsed = parseLinkHeader(response.headers.link);
                    if(parsed) {
                        maxPage = parseInt(parsed.last.page);
                        if(parsed.prev)
                            previousPage = parsed.prev;
                        if(parsed.next)
                            nextPage = parsed.next;
                    }
                }
                return Promise.resolve(new PagedContent(auditions, maxPage, nextPage, previousPage));
            });
    };

    public getApplication = async (auditionId: number, applicationId: number) => {
        return this.axiosPrivate.get(`${this.endpoint}/${auditionId}/applications/${applicationId}`)
            .then((response) => {
            const data = response.data;
            const application: Application = {
                id: data.id,
                state: data.state,
                creationDate: data.creationDate,
                message: data.message,
                self: data.self,
                audition: data.audition,
                applicant: data.applicant
            };
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
        return this.axiosPrivate.post(this.endpoint, input, this.config).then((response) => {
            return Promise.resolve(response);
        });
    }

    public editAudition = async (id: number, input: AuditionInput) => {
        return this.axiosPrivate.put(`${this.endpoint}/${id}`, input, this.config).then((response) => {
            return Promise.resolve(response);
        });
    }

    public apply = async(auditionId:number, message: string) => {
        return this.axiosPrivate.post(`${this.endpoint}/${auditionId}/applications`,
        {message: message},this.applicationConfig).then((response) => {
            return Promise.resolve(response);
        });
    }

    public deleteAuditionById = async(auditionId:number) => {
        return this.axiosPrivate.delete(`${this.endpoint}/${auditionId}`,this.applicationConfig)
            .then((response) => {
            return Promise.resolve(response);
        });
    }

}

export default AuditionApi;