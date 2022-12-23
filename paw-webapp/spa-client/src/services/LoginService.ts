import { paths } from "../common/constants";
// import { checkError } from "../scripts/ErrorChecker";
import { ErrorResponse, Result, UserModel } from "../types";
// import { setCookie } from "../scripts/cookies";
import api from "../api/api"

export class LoginService {

  public async login(
    username: string,
    password: string
  ): Promise<Result<UserModel> | null> {
    const credentials = username + ":" + password;
    const hash = btoa(credentials);

    // api
    //   .get(paths.USERS, {
    //     headers: {
    //       Authorization: "Basic " + hash,
    //     },
    //   })
    //   .then((response) => {
    //     console.log(response);
    //     localStorage.setItem("userJWT", response.headers["x-jwt"] as any);
    //     sessionStorage.setItem("userJWT", response.headers["x-jwt"] as any);
    //   })
    //   .catch((error) => {
    //     console.error(error, "AA");
    //   });

    
    return null;
  }
}
