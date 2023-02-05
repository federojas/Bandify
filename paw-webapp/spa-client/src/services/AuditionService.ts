// import {auditionApi} from "../api";
import { Audition, Application } from "../models";
import { ErrorService } from "./ErrorService";
import ApiResult from "../api/types/ApiResult";
import { AuditionInput } from "../api/types/Audition";
import PostResponse from "../api/types/PostResponse";
import AuditionApiTest from "../api/AuditionApiTest";
import PagedContent from "../api/types/PagedContent";

export default class AuditionService {
  private auditionApi: AuditionApiTest;

  constructor(auditionApi: AuditionApiTest) {
    this.auditionApi = auditionApi;
  }

  public async getAuditionById(id: number): Promise<ApiResult<Audition>> {
    try {
      const response = await this.auditionApi.getAuditionById(id);
      return new ApiResult(
        {
          id: response.id,
          title: response.title,
          description: response.description,
          creationDate: response.creationDate,
          location: response.location,
          lookingFor: response.lookingFor,
          musicGenres: response.musicGenres,
          applications: response.applications,
          ownerId: parseInt(response.owner.split('/')[response.owner.split('/').length - 1])
        } as Audition,
        false,
        null as any
      );
    } catch (error) {
      return ErrorService.returnApiError(error);
    }
  }

  public async getAuditions(page?: number,
    query?: string,
    roles?: string[],
    genres?: string[],
    locations?: string[],
    order?: string,
    ): Promise<ApiResult<PagedContent<Audition[]>>> {
    try {
      const response = await this.auditionApi.getAuditions(page, query, roles, genres, locations, order);
      return new ApiResult( new PagedContent(
          response.getContent().map(a => {
            const aud: Audition = {
              id: a.id,
              title: a.title,
              description: a.description,
              creationDate: a.creationDate,
              location: a.location,
              lookingFor: a.lookingFor,
              musicGenres: a.musicGenres,
              applications: a.applications,
              ownerId: parseInt(a.owner.split('/')[a.owner.split('/').length - 1])
            }; return aud
          }), response.getMaxPage()),
        false,
        null as any
      );
    } catch (error) {
      return ErrorService.returnApiError(error);
    }
  }

  public async getAuditionsByBandId(page?: number,
                            bandId?: number,
  ): Promise<ApiResult<PagedContent<Audition[]>>> {
    try {
      const response = await this.auditionApi.getAuditionsByBandId(page, bandId);
      return new ApiResult( new PagedContent(
              response.getContent().map(a => {
                const aud: Audition = {
                  id: a.id,
                  title: a.title,
                  description: a.description,
                  creationDate: a.creationDate,
                  location: a.location,
                  lookingFor: a.lookingFor,
                  musicGenres: a.musicGenres,
                  applications: a.applications,
                  ownerId: parseInt(a.owner.split('/')[a.owner.split('/').length - 1])
                }; return aud
              }), response.getMaxPage()),
          false,
          null as any
      );
    } catch (error) {
      return ErrorService.returnApiError(error);
    }
  }

  public async getApplication(auditionId: number, applicationId: number): Promise<ApiResult<Application>> {
    try {
      const response = await this.auditionApi.getApplication(auditionId, applicationId);
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

  public async getAuditionApplications(auditionId: number, page?: number, state?: string): Promise<ApiResult<Application[]>> {
    try {
      const response = await this.auditionApi.getApplications(auditionId, page, state);
      return new ApiResult(
        response.map(a => {
          const app: Application = {
            id: a.id,
            creationDate: a.creationDate,
            message: a.message,
            audition: a.audition,
            applicant: a.applicant,
            state: a.state
          }; return app
        }),
        false,
        null as any
      );
    } catch (error) {
      return ErrorService.returnApiError(error);
    }
  }

  public async createAudition(auditionInput: AuditionInput): Promise<ApiResult<PostResponse>> {
    try {
      const postResponse = await this.auditionApi.createAudition(auditionInput);
      const url: string = postResponse.headers!.location!;
      const tokens = url.split('/')
      const id: number = parseInt(tokens[tokens.length-1]);
      const data: PostResponse = { url: url, id: id };
      return new ApiResult(
        data,
        false,
        null as any
      );
    } catch (error) {
      return ErrorService.returnApiError(error);
    }
  }

  public async updateAudition(auditionId: number, auditionInput: AuditionInput) {
    try {
      await this.auditionApi.editAudition(auditionId, auditionInput); 
      return new ApiResult(
          null as any,
          false,
          null as any
      );
    } catch (error) {
      return ErrorService.returnApiError(error);
    }
  }

  public async apply(auditionId: number, message: string): Promise<ApiResult<PostResponse>> {
    try {
      const postResponse = await this.auditionApi.apply(auditionId, message);
      const url: string = postResponse.headers!.location!;
      const tokens = url.split('/')
      const id: number = parseInt(tokens[tokens.length-1]);
      const data: PostResponse = { url: url, id: id };
      return new ApiResult(
        data,
        false,
        null as any
      );
    } catch (error) {
      return ErrorService.returnApiError(error);
    }
  }

  public async deleteAuditionById(auditionId: number) {
    try {
      await this.auditionApi.deleteAuditionById(auditionId);
      return new ApiResult(
          null as any,
          false,
          null as any
      );
    } catch (error) {
      return ErrorService.returnApiError(error);
    }
  }
}