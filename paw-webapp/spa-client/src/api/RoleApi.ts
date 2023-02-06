import { AxiosInstance } from "axios";
import Role from "./types/Role";



interface Params {
    auditionId?: number;
    userId?: number;
    membershipId?: number;
}

class RoleApi {

    private axiosPrivate: AxiosInstance;

    constructor(axiosPrivate: AxiosInstance) {
        this.axiosPrivate = axiosPrivate;
    }
    
    private endpoint: string = "/roles";

    private config = {
        headers: {
            'Accept': 'application/vnd.role-list.v1+json'
        }
    }

    public getRoles = async (params: Params = {}) => {
        return this.axiosPrivate
        .get(this.endpoint, {
            params: {
              audition: params.auditionId,
              user: params.userId,
              membership: params.auditionId,
            }, ...this.config
          })
          .then((response) => {
            const data = response.data;
            const roles: Role[] = Array.isArray(data)
              ? data.map((role: any) => {
                  return {
                    id: role.id,
                    roleName: role.roleName,
                    self: role.self,
                  };
                })
              : [];
            return Promise.resolve(roles);
          });
    };

    public getRoleById = async (id: number) => {
        // Call the API to get the Role data
        return this.axiosPrivate.get(`${this.endpoint}/${id}`).then((response) => {
            const data = response.data;
            const role: Role = {
                id: data.id,
                roleName: data.roleName,
                self: data.self,
            };
        
            return Promise.resolve(role);
        });
      };
    
};

export default RoleApi;
