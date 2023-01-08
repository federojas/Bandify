import { UserApi } from "../api";
import { User, UserCreateInput } from "../api/types/User";

export class UserService {
  public async createNewUser(user: UserCreateInput) {
    return await UserApi.createNewUser(user);
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
      const user = await UserApi.getUserById(id);
      console.log("🚀 ~ file: UserService.ts:24 ~ UserService ~ getUserById ~ user", user)
      
      return user;
    } catch (error) {
      console.log(error);
    }
    // return await UserApi.getUserByEmail(email);
  }
}
