import { paths } from "../common/constants";
// import { checkError } from "../scripts/ErrorChecker";
import { User } from "../models";
// import { setCookie } from "../scripts/cookies";
import api from "../api/api";

export class LoginService {
  public async login(username: string, password: string) {
    const credentials = username + ":" + password;
    const hash = btoa(credentials);

    try {
      const response = await api.get(paths.USERS, {
        headers: {
          'Authorization': "Basic " + hash,
          'Accept': 'application/vnd.user-list.v1+json'
        }
      });
      console.log("🚀 ~ file: LoginService.ts:19 ~ LoginService ~ login ~ response", response)
      
      localStorage.setItem("jwt", response.headers["x-jwt"] as string);
      sessionStorage.setItem("jwt", response.headers["x-jwt"] as string);
      return response.headers;
    } catch (error) {
      console.log("🚀 ~ file: LoginService.ts:25 ~ LoginService ~ login ~ error", error)
    }
    
  }
}
