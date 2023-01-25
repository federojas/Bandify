import {roleApi} from "../api";
import {Role} from "../models";

export class RoleService {
    public async getRoles(): Promise<Role[] | undefined>{
        try {
            const response = await roleApi.getRoles();
            return response.map(r => { const role: Role = {id: r.id, name: r.roleName}; return role });
        } catch (error) {
            console.log("error roles");
        }
    }


    public async getRoleById(id : number): Promise<Role | undefined> {
        try {
            const current = await roleApi.getRoleById(id);
            return {id:current.id, name:current.roleName} as Role;
        } catch (error) {
            console.log("error roles");
        }
    }
}