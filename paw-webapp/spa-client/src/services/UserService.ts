import { userApi } from "../api";
import { User, UserCreateInput, UserUpdateInput, UserSocialMedia } from "../api/types/User";
import { Application } from "../api/types/Application";
export class UserService {
  public async createNewUser(user: UserCreateInput) {
    try {
      return await userApi.createNewUser(user);
    } catch (err) {
      console.log(err)
    }
    // const res = await fetch("http://localhost:8080/users", {
    //     method: "POST",
    //     headers: {
    //         "Content-Type": "application/vnd.user.v1+json",
    //         Accept: "application/json",
    //     },
    //     body: JSON.stringify(user),
    // });
    // console.log("🚀 ~ file: UserService.ts:16 ~ UserService ~ createNewUser ~ res", res)
    // const data =  await res.json();
    // console.log("🚀 ~ file: UserService.ts:18 ~ UserService ~ createNewUser ~ data", data)
    // return data;
  }

  public async getUserById(id: number) {
    try {
      const user = await userApi.getUserById(id);
      console.log("🚀 ~ file: UserService.ts:24 ~ UserService ~ getUserById ~ user", user)

      return user;
    } catch (error) {
      console.log(error);
    }
    // return await userApi.getUserByEmail(email);
  }

  public async getUsers( page?: number, query?: string, genre?: string, role?: string, location?: string): Promise<User[] | undefined> {

    const users: User[] | null = await userApi.getUsers({page, query, genre, role, location})

    if(users !== null && users != undefined) {
      return users.map((user: User) => {
        return {
          applications: user.applications,
          available: user.available,
          band: user.band,
          email: user.email,
          enabled: user.enabled,
          genres: user.genres,
          id: user.id,
          location: user.location,
          name: user.name,
          roles: user.roles,
          self: user.self,
          socialMedia: user.socialMedia,
        } as User;
      });
    } else {
      return [];
    }

  }

  public async updateUser(id: number, user: UserUpdateInput) {
    return await userApi.updateUser(id, user);
  }

  public async getProfileImageByUserId(id: number) {
    const response = await userApi.getProfileImageByUserId(id); //todo: que response hay que devolver?

    return response != null ? response : undefined;
  }

  public async getUserApplications(id: number) {
    const applications: Application[] | null = await userApi.getUserApplications(id);
    if (applications != null) {
      applications.map((application: Application) => {
        return {
          id: application.id,
          state: application.state,
          creationDate: application.creationDate,
          message: application.message,
          self: application.self,
          audition: application.audition,
          applicant: application.applicant
        } as Application;
      });
    }
  }

  //TODO getUserSocialMediaList
  public async getUserSocialMediaList(id: number) {
    const socialMedia: UserSocialMedia[] = await userApi.getUserSocialMediaList(id) as any; //todo: as any medio feo
    return socialMedia.map((socialMedia) => {
      return {
        id: socialMedia.id,
        twitterUrl: socialMedia.twitterUrl,
        spotifyUrl: socialMedia.spotifyUrl,
        user: socialMedia.user
      }
    });
  }

  //TODOl getSocialMediaById
  public async getSocialMediaById(id: number, socialMediaId: number) {
    const socialMedia: UserSocialMedia = await userApi.getSocialMediaById(id, socialMediaId) as any; //todo: as any medio feo
    return {
      id: socialMedia.id,
      twitterUrl: socialMedia.twitterUrl,
      spotifyUrl: socialMedia.spotifyUrl,
      user: socialMedia.user
    }
  }

  public async updateUserSocialMedia(id: number, twitterUrl?: string, spotifyUrl?: string) { 

    //TODO: EN EL MODEL UpdateUserSocialMediaInput DEBEN ESTAR COMO OPCIONAL LOS CAMPOS
    const twUrl = twitterUrl ? twitterUrl : "";
    const spUrl = spotifyUrl ? spotifyUrl : "";
    return await userApi.updateUserSocialMedia(id, {twitterUrl: twUrl , spotifyUrl: spUrl} );
  }
}
