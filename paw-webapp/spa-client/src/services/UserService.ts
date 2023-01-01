import { UserApi } from "../api";
import { UserCreateInput } from "../api/types/User";

class UserService {
    public static async createNewUser(user: UserCreateInput) {
        console.log("🚀 ~ file: UserService.ts:6 ~ UserService ~ createNewUser ~ user", user)
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
}

export default UserService;