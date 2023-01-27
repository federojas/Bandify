import {auditionApi} from "../api";
import {Audition, Application} from "../models";
import {ErrorService} from "./ErrorService";
import ApiResult from "../api/types/ApiResult";

export class AuditionService {
    public async getAuditionById(id : number): Promise<ApiResult<Audition>>{
        try {
            const response = await auditionApi.getAuditionById(id);
            return new ApiResult(
                {
                    id:response.id,
                    title:response.title,
                    description:response.description,
                    creationDate:response.creationDate,
                    location:response.location,
                    lookingFor:response.lookingFor,
                    musicGenres:response.musicGenres,
                    applications:response.applications,
                    owner:response.owner
                } as Audition,
                false,
                null as any
            );
        } catch (error) {
            return ErrorService.returnApiError(error);
        }
    }

    public async getAuditions(page? : number,
                              query?: string,
                              roles?: string[],
                              genres?: string[],
                              locations?: string[],
                              bandId?: number): Promise<ApiResult<Audition[]>> {
        try {
            const response = await auditionApi.getAuditions(page, query, roles, genres, locations, bandId);
            return new ApiResult(
                    response.map(a => { const aud: Audition = {
                    id:a.id,
                    title:a.title,
                    description:a.description,
                    creationDate:a.creationDate,
                    location:a.location,
                    lookingFor:a.lookingFor,
                    musicGenres:a.musicGenres,
                    applications:a.applications,
                    owner:a.owner
                    }; return aud }),
                    false,
                    null as any
                );
        } catch (error) {
            return ErrorService.returnApiError(error);
        }
    }

    public async getApplication(auditionId : number, applicationId: number): Promise<ApiResult<Application>>{
        try {
            const response = await auditionApi.getApplication(auditionId, applicationId);
            return new ApiResult(
                {
                    id: response.id,
                    creationDate: response.creationDate,
                    message: response.message,
                    audition: response.audition,
                    applicant: response.applicant,
                    state: response.state
                } as Application,
                false,
                null as any
            );
        } catch (error) {
            return ErrorService.returnApiError(error);
        }
    }

    public async getAuditionApplications(auditionId : number, page?: number, state?: string): Promise<ApiResult<Application[]>>{
        try {
            const response = await auditionApi.getApplications(auditionId, page, state);
            return new ApiResult(
                response.map(a => { const app: Application = {
                    id: a.id,
                    creationDate: a.creationDate,
                    message: a.message,
                    audition: a.audition,
                    applicant: a.applicant,
                    state: a.state
                }; return app }),
                false,
                null as any
            );
        } catch (error) {
            return ErrorService.returnApiError(error);
        }
    }
}