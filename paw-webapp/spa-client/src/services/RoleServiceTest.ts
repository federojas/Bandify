import RoleApiTest from "../api/RoleApiTest";
import ApiResult from "../api/types/ApiResult";
import { Role } from "../models";
import {ErrorService} from "./ErrorService";

export default class RoleService {

    private RoleApi: RoleApiTest;

    constructor(RoleApi: RoleApiTest) {
        this.RoleApi = RoleApi;
    }
    public async getRoles(): Promise<ApiResult<Role[]>> {
        try {
            const response = await this.RoleApi.getRoles();
            return new ApiResult(
                response.map(r => { const role: Role = {id: r.id, name: r.roleName}; return role }),
                false,
                null as any);
        } catch (error: any) {
            return ErrorService.returnApiError(error);
        }
    }


    public async getRoleById(id: number): Promise<ApiResult<Role>> {
        try {
            const current = await this.RoleApi.getRoleById(id);
            return new ApiResult( {id:current.id, name:current.roleName} as Role, false, null as any);
        } catch (error: any) {
            return ErrorService.returnApiError(error);
        }
    }
}