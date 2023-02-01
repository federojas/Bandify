import { UserCreateInput, UserUpdateInput, User } from "./types/User";
import { Application } from "../api/types/Application";
import api from "./api";
import { AxiosResponse } from "axios";

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
  // TODO: Revisar todos los metodos, algunos estan mal.
  
  private endpoint: string = "/users";

  private config = {
      headers: {
          'Accept': 'application/vnd.user.v1+json',
          'Content-Type': 'application/vnd.user.v1+json'
      }
  }

  public createNewUser = async (user: UserCreateInput) => {
    return api
      .post(this.endpoint, user, this.config)
      .then((response) => {
        return true;
      })
  };

  public updateUser = async (id: number, user: UserUpdateInput) => {
    return api
      .put(`${this.endpoint}/${id}`, user)
      .then((response) => {
        return true;
      })
  };

  public getUserById = async (id: number): Promise<User> => {
    try {
      const response: AxiosResponse<User> = await api.get<User>(`${this.endpoint}/${id}`);
      return response.data;
    } catch (error) {
      console.log(error);
      throw new Error("Error getting user by id");
    }
  };

  public getProfileImageByUserId = async (id: number) => {
    return api
      .get(`${this.endpoint}/${id}/profile-image`)
      .then((response) => {
        return response.data;
      })
  };

  public updateProfileImage = async (id: number, image: File) => {
    return api
      .get(`${this.endpoint}/${id}/profile-image`)
      .then((response) => {
        return true;
      })
  };


  public getUsers = async (page?: number, query?: string, genre?: string[], role?: string[], location?: string[]) => {
    return api
      .get(this.endpoint, { params: {
          page: page,
          query: query,
          genre: genre,
          role: role,
          location: location
      } })
      .then((response) => {
        const data = response.data;
        const users: User[] = Array.isArray(data)
          ? data.map((user) => {
              return { ...user };
            })
          : [];
          return users;
      })
  };

  public getUserApplications = async (id: number) => {
    return api
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
    return api.get(`${this.endpoint}/${id}/social-media/${socialMediaId}`)
  };
  
  public getUserSocialMediaList = async (id: number) => {
    return api.get(`${this.endpoint}/${id}/social-media`); 
      
  };

  public updateUserSocialMedia = async (
    id: number,
    input: UpdateUserSocialMediaInput
  ) => {
    return api
      .put(`${this.endpoint}/${id}/social-media`, input)
      .then((response) => {
        return true;
      })
  };

  

};

export default UserApi;
