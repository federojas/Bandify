import ApiResult from "../api/types/ApiResult";
import {
    UserCreateInput,
    UserPasswordResetInput,
    UserPasswordResetRequestInput,
    UserUpdateInput
} from "../api/types/User";
import UserApi from "../api/UserApi";
import {Audition, User, Application} from "../models";
import { ErrorService } from "./ErrorService";
import PagedContent from "../api/types/PagedContent";

export default class UserService {

  private userApi: UserApi;

  constructor(userApi: UserApi) {
    this.userApi = userApi;
  }



  public async createUser(input: UserCreateInput): Promise<ApiResult<User>> {
    try {
      const current = await this.userApi.createNewUser(input);
      return new ApiResult(
        {} as User,
        false,
        null as any
      );
    } catch (error: any) {
      return ErrorService.returnApiError(error);
    }
  }

  public async resendVerificationEmail(input: string): Promise<ApiResult<User>> {
      try {
          const current = await this.userApi.resendVerificationEmail(input);
          return new ApiResult(
              {} as User,
              false,
              null as any
          );
      } catch (error: any) {
          return ErrorService.returnApiError(error);
      }
  }

    public async updateUser(userId: number, input: UserUpdateInput): Promise<ApiResult<User>> {
        try {
            const current = await this.userApi.updateUser(userId, input);
            return new ApiResult(
                {} as User,
                false,
                null as any
            );
        } catch (error: any) {
            return ErrorService.returnApiError(error);
        }
    }

  public async getUserById(id: number): Promise<ApiResult<User>> {
    try {
      const current = await this.userApi.getUserById(id);
      return new ApiResult(
        {
          applications: current.applications,
          available: current.available,
          band: current.band,
          description: current.description,
          enabled: current.enabled,
          genres: current.genres,
          id: current.id,
          location: current.location,
          name: current.name,
          roles: current.roles,
          socialMedia: current.socialMedia,
          surname: current.surname,
          profileImage: current.profileImage
        } as User,
        false,
        null as any
      );
    } catch (error: any) {
      return ErrorService.returnApiError(error);
    }
  }

    public async getUsersByUrl(url: string): Promise<ApiResult<PagedContent<User[]>>> {
        try {
            const response = await this.userApi.getUsersWithUrl(url);
            return new ApiResult( new PagedContent(
                    response.getContent().map(u => {
                        const user: User = {
                            applications: u.applications,
                            available: u.available,
                            band: u.band,
                            description: u.description,
                            enabled: u.enabled,
                            genres: u.genres,
                            id: u.id,
                            location: u.location,
                            name: u.name,
                            roles: u.roles,
                            socialMedia: u.socialMedia,
                            surname: u.surname,
                            profileImage: u.profileImage
                        }; return user
                    }), response.getMaxPage(), response.getNextPage(), response.getPreviousPage()),
                false,
                null as any
            );
        } catch (error) {
            return ErrorService.returnApiError(error);
        }
    }

  public async getUsers(page?: number, query?: string, genre?: string[], role?: string[], location?: string[]): Promise<ApiResult<PagedContent<User[]>>> {
    try {
      const current = await this.userApi.getUsers(page, query, genre, role, location);
      return new ApiResult( new PagedContent(
        current.getContent().map(u => {
          const user: User = {
            applications: u.applications,
            available: u.available,
            band: u.band,
            description: u.description,
            enabled: u.enabled,
            genres: u.genres,
            id: u.id,
            location: u.location,
            name: u.name,
            roles: u.roles,
            socialMedia: u.socialMedia,
            surname: u.surname,
            profileImage: u.profileImage
        }; return user
        }), current.getMaxPage(), current.getNextPage(), current.getPreviousPage()),
        false,
        null as any
      );
    } catch (error: any) {
      return ErrorService.returnApiError(error);
    }
  }

  public async updateUserProfileImage(id: number, image: File) {
      try {
          const formData = new FormData();
          formData.append("image", image, image.name);
          const response = await this.userApi.updateProfileImage(id, formData);
          return new ApiResult(
              response,
              false,
              null as any)
      } catch (error: any) {
          return ErrorService.returnApiError(error);
      }
  }

  public async getUserApplications(userId: number, state: string): Promise<ApiResult<Application[]>> {
    try {
      const applications = await this.userApi.getUserApplications(userId, state);
      return new ApiResult(
        applications.map(a => {
          const aux : Application = {
            id: a.id,
            creationDate: a.creationDate,
            message: a.message,
            audition: a.audition,
            applicant: a.applicant,
            state: a.state
          }; return aux
        }),
        false,
        null as any)
    } catch (error: any) {
      return ErrorService.returnApiError(error);
    }
  }

  public async generateUserPassword(data: UserPasswordResetRequestInput) {
    try {
        const response = await this.userApi.generateUserPassword(data);
        return new ApiResult(
            response,
            false,
            null as any)
    } catch (error: any) {
        return ErrorService.returnApiError(error);
    }
  }

  public async changeUserPassword(token: string, data: UserPasswordResetInput) {
    try {
        const response = await this.userApi.changeUserPassword(token, data);
        return new ApiResult(
            response,
            false,
            null as any)
    } catch (error: any) {
        return ErrorService.returnApiError(error);
    }
  }
}