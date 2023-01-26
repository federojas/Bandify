import {auditionApi} from "../api";
import {Audition, Application} from "../models";

export class AuditionService {
    public async getAuditionById(id : number): Promise<Audition | undefined>{
        try {
            const response = await auditionApi.getAuditionById(id);
            return {
                id:response.id,
                title:response.title,
                description:response.description,
                creationDate:response.creationDate,
                location:response.location,
                lookingFor:response.lookingFor,
                musicGenres:response.musicGenres,
                applications:response.applications,
                owner:response.owner
            } as Audition;
        } catch (error) {
            console.log("error audition");
        }
    }

    public async getAuditions(page : number,
                              query?: string,
                              roles?: string[],
                              genres?: string[],
                              locations?: string[],
                              bandId?: number): Promise<Audition[] | undefined>{
        try {
            const response = await auditionApi.getAuditions(page, query, roles, genres, locations, bandId);
            return response.map(a => { const aud: Audition = {
                id:a.id,
                title:a.title,
                description:a.description,
                creationDate:a.creationDate,
                location:a.location,
                lookingFor:a.lookingFor,
                musicGenres:a.musicGenres,
                applications:a.applications,
                owner:a.owner
            }; return aud });
        } catch (error) {
            console.log("error audition");
        }
    }

    public async getApplication(auditionId : number, applicationId: number): Promise<Application | undefined>{
        try {
            const response = await auditionApi.getApplication(auditionId, applicationId);
            return {
                id: response.id,
                creationDate: response.creationDate,
                message: response.message,
                audition: response.audition,
                applicant: response.applicant,
                state: response.state
            } as Application;
        } catch (error) {
            console.log("error audition");
        }
    }

    public async getAuditionApplications(auditionId : number, page: number, state: string): Promise<Application[] | undefined>{
        try {
            const response = await auditionApi.getApplications(auditionId, page, state);
            return response.map(a => { const app: Application = {
                id: a.id,
                creationDate: a.creationDate,
                message: a.message,
                audition: a.audition,
                applicant: a.applicant,
                state: a.state
            }; return app });
        } catch (error) {
            console.log("error audition");
        }
    }
}