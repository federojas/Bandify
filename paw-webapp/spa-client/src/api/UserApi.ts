import { UserCreateInput, UserUpdateInput, User } from "./types/User";
import api from "./api";

const UserApi = (() => {
  const endpoint = "/users";

  const createNewUser = async (user: UserCreateInput) => {
    return api
      .post(endpoint, user)
      .then((response) => {
        return true;
      })
      .catch((error) => {
        console.log(error);
        return false;
      });
  };

  const updateUser = (id: number, user: UserUpdateInput) => {
    return api
      .put(`${endpoint}/${id}`, user)
      .then((response) => {
        return true;
      })
      .catch((error) => {
        console.log(error.response.data);
        return false;
      });
  };

  const getUserById = (id: number) => {
    return api.get(`${endpoint}/${id}`).then((response) => {
      const data = response.data;
      const user: User = {
        // applications: data.applications,
        // available: data.available,
        // band: data.band,
        // email: data.email,
        // enabled: data.enabled,
        // genres: data.genres,
        // id: data.id,
        // location: data.location,
        // name: data.name,
        // roles: data.roles,
        // self: data.self,
        // socialMedia: data.socialMedia,
        ...data,
      };
    });
  };

  const getProfileImageByUserId = (id: number) => {
    return api
      .get(`${endpoint}/${id}/profile-image`)
      .then((response) => {
        return response.data;
      })
      .catch((error) => {
        console.log(error.response.data);
        return null;
      });
  };

  const updateProfileImage = (id: number, image: File) => {
    return api
      .get(`${endpoint}/${id}/profile-image`)
      .then((response) => {
        return true;
      })
      .catch((error) => {
        console.log(error.response.data);
        return false;
      });
  };

  interface GetUserParams {
    page?: number;
    query?: string;
    genre?: string;
    role?: string;
    location?: string;
  }

  const getUsers = (params: GetUserParams = {}) => {
    return api
      .get(endpoint, { params: { ...params } })
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

  const getUserApplications = (id: number) => {
    return api
      .get(`${endpoint}/${id}/applications`)
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

  const getUserSocialMediaList = (id: number) => {
    return api
      .get(`${endpoint}/${id}/social-media`)
      .then((response) => {
        const data = response.data;
        // TODO
      })
      .catch((error) => {
        console.log(error.response.data);
        return null;
      });
  };

  type UpdateUserSocialMediaInput = {
    twitterUrl: string;
    spotifyUrl: string;
  };

  const updateUserSocialMedia = (
    id: number,
    input: UpdateUserSocialMediaInput
  ) => {
    return api
      .put(`${endpoint}/${id}/social-media`, input)
      .then((response) => {
        return true;
      })
      .catch((error) => {
        console.log(error.response.data);
        return false;
      });
  };

  const getSocialMediaById = (id: number, socialMediaId: number) => {
    return api
      .get(`${endpoint}/${id}/social-media/${socialMediaId}`)
      .then((response) => {
        const data = response.data;
        // TODO
      })
      .catch((error) => {
        console.log(error.response.data);
        return null;
      });
  };

  return {
    createNewUser,
    updateUser,
    getUserById,
    getProfileImageByUserId,
    updateProfileImage,
    getUsers,
    getUserApplications,
    getUserSocialMediaList,
    updateUserSocialMedia,
    getSocialMediaById,
  };
})();

export default UserApi;
