import { UserCreateInput, UserUpdateInput, User } from "./types/User";
import { Application } from "../api/types/Application";
import { AxiosInstance, AxiosResponse } from "axios";

interface GetUserParams {
  page?: number;
  query?: string;
  genre?: string;
  role?: string;
  location?: string;
}

type UpdateUserSocialMediaInput = {
  twitterUrl: string;
  spotifyUrl: string;
};

class UserApi {

  private axiosPrivate: AxiosInstance;

  constructor(axiosPrivate: AxiosInstance) {
    this.axiosPrivate = axiosPrivate;
  }

  private endpoint: string = "/users";

  private config = {
    headers: {
      'Content-Type': 'application/vnd.user.v1+json'
    }
  }

  public createNewUser = async (user: UserCreateInput) => {
    return this.axiosPrivate
      .post(this.endpoint, user, this.config)
      .then((response) => {
        return true;
      })
  };

  public updateUser = async (id: number, user: UserUpdateInput) => {
    return this.axiosPrivate
      .put(`${this.endpoint}/${id}`, user)
      .then((response) => {
        return true;
      })
  };

  public getUserById = async (id: number): Promise<User> => {
    try {
      const response: AxiosResponse<User> = await this.axiosPrivate.get<User>(`${this.endpoint}/${id}`);
      return response.data;
    } catch (error) {
      console.log(error);
      throw new Error("Error getting user by id");
    }

    // return api.get(`${this.endpoint}/${id}`).then((response) => {
    //   const data = response.data;
    //   const user: User = {
    //     // applications: data.applications,
    //     // available: data.available,
    //     // band: data.band,
    //     // email: data.email,
    //     // enabled: data.enabled,
    //     // genres: data.genres,
    //     // id: data.id,
    //     // location: data.location,
    //     // name: data.name,
    //     // roles: data.roles,
    //     // self: data.self,
    //     // socialMedia: data.socialMedia,
    //     ...data,
    //   };
    // });
  };

  public getProfileImageByUserId = async (id: number) => {
    return this.axiosPrivate
      .get(`${this.endpoint}/${id}/profile-image`)
      .then((response) => {
        return response.data;
      })
  };

  public updateProfileImage = async (id: number, image: File) => {
    return this.axiosPrivate
      .get(`${this.endpoint}/${id}/profile-image`)
      .then((response) => {
        return true;
      })
  };


    public getUsers = async (page?: number, query?: string, genre?: string[], role?: string[], location?: string[]) => {
        return this.axiosPrivate
            .get(this.endpoint, {
                params: {
                    page: page,
                    query: query,
                    genre: genre,
                    role: role,
                    location: location
                },
                paramsSerializer: {
                    indexes: null
                }
            })
            .then((response) => {
                const data = response.data;
                const users: User[] = Array.isArray(data)
                    ? data.map((user : any) => {
                        return { ...user };
                    })
                    : [];
                return users;
            })
    };

  public getUserApplications = async (id: number) => {
    return this.axiosPrivate
      .get(`${this.endpoint}/${id}/applications`)
      .then((response) => {
        const data = response.data;
        const applications: Application[] = Array.isArray(data)
          ? data.map((application) => {
            return { ...application };
          })
          : [];
        return applications;
      })
  };

  public getSocialMediaById = async (id: number, socialMediaId: number) => {
    return this.axiosPrivate.get(`${this.endpoint}/${id}/social-media/${socialMediaId}`)
  };

  public getUserSocialMediaList = async (id: number) => {
    return this.axiosPrivate.get(`${this.endpoint}/${id}/social-media`); //TODO: falta mapear? 

  };

  public updateUserSocialMedia = async (
    id: number,
    input: UpdateUserSocialMediaInput
  ) => {
    return this.axiosPrivate
      .put(`${this.endpoint}/${id}/social-media`, input)
      .then((response) => {
        return true;
      })
  };



};

export default UserApi;
