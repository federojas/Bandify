import { paths } from "../common/constants";
// import { checkError } from "../scripts/ErrorChecker";
import { ErrorResponse, Result, UserModel } from "../types";
// import { setCookie } from "../scripts/cookies";
import api from "../api/api";

export class LoginService {
  public async login(username: string, password: string) {
    const credentials = username + ":" + password;
    const hash = btoa(credentials);

    api
      .get(paths.USERS, {
        headers: {
          'Authorization': "Basic " + hash,
          'Accept': 'application/vnd.user-list.v1+json'
        }
      })
      .then((response) => {
        console.log(
          "🚀 ~ file: LoginService.ts:19 ~ LoginService ~ .then ~ response",
          response
        );
        localStorage.setItem("userJWT", response.headers["x-jwt"] as any);
        sessionStorage.setItem("userJWT", response.headers["x-jwt"] as any);
        return true;
      })
      .catch((error) => {
        console.log(
          "🚀 ~ file: LoginService.ts:25 ~ LoginService ~ login ~ error",
          error
        );
        return false;
      });
  }
}
