import { UserCreateInput, UserUpdateInput, User } from "./types/User";
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
  private endpoint: string = "/users";

  public createNewUser = async (user: UserCreateInput) => {
    return api
      .post(this.endpoint, user)
      .then((response) => {
        return true;
      })
      .catch((error) => {
        console.log(error);
        return false;
      });
  };

  public updateUser = async (id: number, user: UserUpdateInput) => {
    return api
      .put(`${this.endpoint}/${id}`, user)
      .then((response) => {
        return true;
      })
      .catch((error) => {
        console.log(error.response.data);
        return false;
      });
  };

  public getUserById = async (id: number): Promise<User> => {
    try {
      const response: AxiosResponse<User> = await api.get<User>(`${this.endpoint}/${id}`);
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
    return api
      .get(`${this.endpoint}/${id}/profile-image`)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        console.log(error.response.data);
        return null;
      });
  };

  public updateProfileImage = async (id: number, image: File) => {
    return api
      .get(`${this.endpoint}/${id}/profile-image`)
      .then((response) => {
        return true;
      })
      .catch((error) => {
        console.log(error.response.data);
        return false;
      });
  };


  public getUsers = async (params: GetUserParams = {}) => {
    return api
      .get(this.endpoint, { params: { ...params } })
      .then((response) => {
        const data = response.data;
        const users: User[] = Array.isArray(data)
          ? data.map((user) => {
              return { ...user };
            })
          : [];
      })
      .catch((error) => {
        console.log(error.response.data);
        return null;
      });
  };

  public getUserApplications = async (id: number) => {
    return api
      .get(`${this.endpoint}/${id}/applications`)
      .then((response) => {
        const data = response.data;
        // TODO: Falta crear el Type Application
        const applications = Array.isArray(data)
          ? data.map((application) => {
              return { ...application };
            })
          : [];
      })
      .catch((error) => {
        console.log(error.response.data);
        return null;
      });
  };

  public getUserSocialMediaList = async (id: number) => {
    return api
      .get(`${this.endpoint}/${id}/social-media`)
      .then((response) => {
        const data = response.data;
        // TODO
      })
      .catch((error) => {
        console.log(error.response.data);
        return null;
      });
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
      .catch((error) => {
        console.log(error.response.data);
        return false;
      });
  };

  public getSocialMediaById = async (id: number, socialMediaId: number) => {
    return api
      .get(`${this.endpoint}/${id}/social-media/${socialMediaId}`)
      .then((response) => {
        const data = response.data;
        // TODO
      })
      .catch((error) => {
        console.log(error.response.data);
        return null;
      });
  };

};

export default UserApi;
