import { UserApi } from "../api";
import { UserCreateInput } from "../api/types/User";

class UserService {
    public static async createNewUser(user: UserCreateInput) {
        console.log("🚀 ~ file: UserService.ts:6 ~ UserService ~ createNewUser ~ user", user)
        
        return await UserApi.createNewUser(user);
    } 
}

export default UserService;