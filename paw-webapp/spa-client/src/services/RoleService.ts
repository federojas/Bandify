import {roleApi} from "../api";
import {Role} from "../models";
import ApiResult from "../api/types/ApiResult";
import {ErrorService} from "./ErrorService";

export class RoleService {
    public async getRoles(): Promise<ApiResult<Role[]>>{
        try {
            const response = await roleApi.getRoles();
            return new ApiResult(
                response.map(r => { const role: Role = {id: r.id, name: r.roleName}; return role }),
                false,
                null as any);
        } catch (error: any) {
            return ErrorService.returnApiError(error);
        }
    }


    public async getRoleById(id : number):  Promise<ApiResult<Role>> {
        try {
            const current = await roleApi.getRoleById(id);
            return new ApiResult( {id:current.id, name:current.roleName} as Role, false, null as any);
        } catch (error: any) {
            return ErrorService.returnApiError(error);
        }
    }
}