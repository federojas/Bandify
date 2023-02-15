import {
  UserCreateInput,
  UserUpdateInput,
  User,
  UserPasswordResetRequestInput,
  UserPasswordResetInput
} from "./types/User";
import { Application } from "./types/Application";
import { AxiosInstance } from "axios";
import { parseLinkHeader } from "@web3-storage/parse-link-header";
import PagedContent from "./types/PagedContent";
import { UpdateUserSocialMediaInput } from "./types/SocialMedia";
import api from "./api";

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

  private socialMediaConfig = {
    headers: {
      'Content-Type': 'application/vnd.social-media-list.v1+json'
    }
  };

  public createNewUser = async (user: UserCreateInput) => {
    return this.axiosPrivate
      .post(this.endpoint, user, this.config)
      .then((response) => {
        return true;
      })
  };

  public resendVerificationEmail = async (user: string) => {
    return this.axiosPrivate.post(
      this.endpoint,
      {},
      {
        ...this.config,
        params: {
          email: user,
        }
      })
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
    return this.axiosPrivate.get(`${this.endpoint}/${id}`).then((response) => {
      const data = response.data;
      const user: User = {
        applications: data.applications,
        available: data.available,
        band: data.band,
        enabled: data.enabled,
        genres: data.genres,
        id: data.id,
        location: data.location,
        name: data.name,
        roles: data.roles,
        self: data.self,
        socialMedia: data.socialMedia,
        profileImage: data.profileImage,
        surname: data.surname,
        description: data.description
      };
      return Promise.resolve(user);
    });
  };

  public getUserByUrl = async (url: string): Promise<User> => {
    return this.axiosPrivate.get(url).then((response) => {
      const data = response.data;
      const user: User = {
        applications: data.applications,
        available: data.available,
        band: data.band,
        enabled: data.enabled,
        genres: data.genres,
        id: data.id,
        location: data.location,
        name: data.name,
        roles: data.roles,
        self: data.self,
        socialMedia: data.socialMedia,
        profileImage: data.profileImage,
        surname: data.surname,
        description: data.description
      };
      return Promise.resolve(user);
    });
  };

  public updateProfileImage = async (id: number, image: FormData) => {
    return this.axiosPrivate
      .put(`${this.endpoint}/${id}/profile-image`, image)
      .then((response) => {
        return true;
      })
  };


  public getUsersWithUrl = async (url: string) => {
    return this.axiosPrivate
      .get(url)
      .then((response) => {
        const data = response.data;
        const users: User[] = Array.isArray(data)
          ? data.map((user: any) => {
            return {
              applications: user.applications,
              available: user.available,
              band: user.band,
              enabled: user.enabled,
              genres: user.genres,
              id: user.id,
              location: user.location,
              name: user.name,
              roles: user.roles,
              self: user.self,
              socialMedia: user.socialMedia,
              profileImage: user.profileImage,
              surname: user.surname,
              description: user.description
            };
          })
          : [];
        let maxPage = 1;
        let previousPage = "";
        let nextPage = "";
        let lastPage = "";
        let firstPage = "";
        let parsed;
        if(response.headers) {
          parsed = parseLinkHeader(response.headers.link);
          if(parsed) {
            maxPage = parseInt(parsed.last.page);
            lastPage = parsed.last.url;
            firstPage = parsed.first.url;
            if(parsed.prev)
              previousPage = parsed.prev.url;
            if(parsed.next)
              nextPage = parsed.next.url;
          }
        }
        return Promise.resolve(new PagedContent(users, maxPage, nextPage, previousPage, lastPage, firstPage));
      });
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
          ? data.map((user: any) => {
            return {
              applications: user.applications,
              available: user.available,
              band: user.band,
              enabled: user.enabled,
              genres: user.genres,
              id: user.id,
              location: user.location,
              name: user.name,
              roles: user.roles,
              self: user.self,
              socialMedia: user.socialMedia,
              profileImage: user.profileImage,
              surname: user.surname,
              description: user.description
            };
          })
          : [];
        let maxPage = 1;
        let previousPage = "";
        let nextPage = "";
        let lastPage = "";
        let firstPage = "";
        let parsed;
        if(response.headers) {
          parsed = parseLinkHeader(response.headers.link);
          if(parsed) {
            maxPage = parseInt(parsed.last.page);
            lastPage = parsed.last.url;
            firstPage = parsed.first.url;
            if(parsed.prev)
              previousPage = parsed.prev.url;
            if(parsed.next)
              nextPage = parsed.next.url;
          }
        }
        return Promise.resolve(new PagedContent(users, maxPage, nextPage, previousPage, lastPage, firstPage));
      })
  };

  public getUserAuditionApplications = async (auditionId: number, userId: number) => {
    return this.axiosPrivate
        .get(`${this.endpoint}/${userId}/applications`, {
          params: {
            auditionId: auditionId
          },
        })
        .then((response) => {
          const data = response.data;
          const applications: Application[] = Array.isArray(data)
              ? data.map((application) => {
                return { ...application };
              })
              : [];
          let maxPage = 1;
          let previousPage = "";
          let nextPage = "";
          let lastPage = "";
          let firstPage = "";
          let parsed;
          if(response.headers) {
            parsed = parseLinkHeader(response.headers.link);
            if(parsed) {
              maxPage = parseInt(parsed.last.page);
              lastPage = parsed.last.url;
              firstPage = parsed.first.url;
              if(parsed.prev)
                previousPage = parsed.prev.url;
              if(parsed.next)
                nextPage = parsed.next.url;
            }
          }
          return Promise.resolve(new PagedContent(applications, maxPage, nextPage, previousPage, lastPage, firstPage));
        })
  };

  public getUserApplications = async (id: number, state: string, page: number) => {
    return this.axiosPrivate
      .get(`${this.endpoint}/${id}/applications`, {
        params: {
          state: state,
          page: page
        },
      })
      .then((response) => {
        const data = response.data;
        const applications: Application[] = Array.isArray(data)
          ? data.map((application) => {
            return { ...application };
          })
          : [];
        let maxPage = 1;
        let previousPage = "";
        let nextPage = "";
        let lastPage = "";
        let firstPage = "";
        let parsed;
        if(response.headers) {
          parsed = parseLinkHeader(response.headers.link);
          if(parsed) {
            maxPage = parseInt(parsed.last.page);
            lastPage = parsed.last.url;
            firstPage = parsed.first.url;
            if(parsed.prev)
              previousPage = parsed.prev.url;
            if(parsed.next)
              nextPage = parsed.next.url;
          }
        }
        return Promise.resolve(new PagedContent(applications, maxPage, nextPage, previousPage, lastPage, firstPage));
      })
  };

  public getUserApplicationsByUrl = async (url: string) => {
    return this.axiosPrivate
      .get(url)
      .then((response) => {
        const data = response.data;
        const applications: Application[] = Array.isArray(data)
          ? data.map((application) => {
            return { ...application };
          })
          : [];
        let maxPage = 1;
        let previousPage = "";
        let nextPage = "";
        let lastPage = "";
        let firstPage = "";
        let parsed;
        if(response.headers) {
          parsed = parseLinkHeader(response.headers.link);
          if(parsed) {
            maxPage = parseInt(parsed.last.page);
            lastPage = parsed.last.url;
            firstPage = parsed.first.url;
            if(parsed.prev)
              previousPage = parsed.prev.url;
            if(parsed.next)
              nextPage = parsed.next.url;
          }
        }
        return Promise.resolve(new PagedContent(applications, maxPage, nextPage, previousPage, lastPage, firstPage));
      })
  };


  public getSocialMediaById = async (id: number, socialMediaId: number) => {
    return this.axiosPrivate.get(`${this.endpoint}/${id}/social-media/${socialMediaId}`)
  };

  public getUserSocialMediaList = async (id: number) => {
    return this.axiosPrivate.get(`${this.endpoint}/${id}/social-media`);
  };

  public updateUserSocialMedia = async (
    id: number,
    input: UpdateUserSocialMediaInput
  ) => {
    return this.axiosPrivate
      .put(`${this.endpoint}/${id}/social-media`, input, this.socialMediaConfig)
      .then((response) => {
        return true;
      })
  };

  public generateUserPassword = async (email: UserPasswordResetRequestInput) => {
    const config = {
      headers: {
        'Content-Type': 'application/vnd.password-token.v1+json'
      }
    }
    return this.axiosPrivate
      .post(`${this.endpoint}/password-tokens`, email, config)
      .then((response) => {
        return true;
      })
  };

  public changeUserPassword = async (token: string, input: UserPasswordResetInput) => {
    const config = {
      headers: {
        'Content-Type': 'application/vnd.password-token.v1+json',
        'Authorization': "Basic " + btoa("mail:" + token)
      }
    }
    return api
      .put(`${this.endpoint}/password-tokens/${token}`, input, config)
      .then((response) => {
        return response;
      })
  };

  public resendUserVerification = async (email: UserPasswordResetRequestInput) => {
    const config = {
      headers: {
        'Content-Type': 'application/vnd.verify-token.v1+json'
      }
    }
    return this.axiosPrivate
      .post(`${this.endpoint}/verify-tokens`, email, config)
      .then((response) => {
        return response;
      })
  };

    public verifyUser = async (token: string) => {
        const config = {
            headers: {
                'Content-Type': 'application/vnd.verify-token.v1+json',
                'Authorization' : "Basic " + btoa( "mail:" + token)
            }
        }
        return api
            .put(`${this.endpoint}/verify-tokens/${token}`,{}, config)
            .then((response) => {
                return response;
            })
    };

};

export default UserApi;
