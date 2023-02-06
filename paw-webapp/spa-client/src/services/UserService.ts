import ApiResult from "../api/types/ApiResult";
import {UserCreateInput, UserUpdateInput} from "../api/types/User";
import UserApi from "../api/UserApi";
import { User } from "../models";
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
          surname: current.surname
        } as User,
        false,
        null as any
      );
    } catch (error: any) {
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
            surname: u.surname
          }; return user
        }), current.getMaxPage(), current.getNextPage(), current.getPreviousPage()),
        false,
        null as any
      );
    } catch (error: any) {
      return ErrorService.returnApiError(error);
    }
  }

  public async getProfileImageByUserId(id: number): Promise<ApiResult<Blob>> {
    try {
      const image = await this.userApi.getProfileImageByUserId(id);
      return new ApiResult(
        image as Blob,
        false,
        null as any)
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
}