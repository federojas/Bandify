import { paths } from "../common/constants";
// import { checkError } from "../scripts/ErrorChecker";
// import { setCookie } from "../scripts/cookies";
import api from "../api/api";
import {ErrorService} from "./ErrorService";
import ApiResult from "../api/types/ApiResult";
import { AxiosResponse } from "axios";

export class LoginService {
  public async login(username: string, password: string, rememberMe: boolean): Promise<ApiResult<AxiosResponse>> {
    const credentials = username + ":" + password;
    const hash = btoa(credentials);

    try {
      const response = await api.get(paths.USERS, {
        headers: {
          'Authorization': "Basic " + hash,
          'Accept': 'application/vnd.user-list.v1+json'
        }
      });

      if (rememberMe) localStorage.setItem("jwt", response.headers["x-jwt"] as string);
      sessionStorage.setItem("jwt", response.headers["x-jwt"] as string);
      return new ApiResult(response, false, null as any);
    } catch (error) {
        return ErrorService.returnApiError(error);
    }
  }
}
