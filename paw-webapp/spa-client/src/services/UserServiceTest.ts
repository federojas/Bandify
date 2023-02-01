import ApiResult from "../api/types/ApiResult";
import UserApiTest from "../api/UserApiTest";
import {User} from "../models";
import {ErrorService} from "./ErrorService";

export default class UserService {

    private userApi: UserApiTest;

    constructor(userApi: UserApiTest) {
        this.userApi = userApi;
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
                    profileImage: current.profileImage,
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

    public async getUsers(page?: number, query?: string, genre?: string[], role?: string[], location?: string[]): Promise<ApiResult<User[]>> {
        try {
            const current = await this.userApi.getUsers(page, query, genre, role, location);
            return new ApiResult(
                current.map(u => {const user: User = {
                    applications: u.applications,
                    available: u.available,
                    band: u.band,
                    description: u.description,
                    enabled: u.enabled,
                    genres: u.genres,
                    id: u.id,
                    location: u.location,
                    name: u.name,
                    profileImage: u.profileImage,
                    roles: u.roles,
                    socialMedia: u.socialMedia,
                    surname: u.surname
                }; return user}),
                false,
                null as any
            );
        } catch (error: any) {
            return ErrorService.returnApiError(error);
        }
    }
}