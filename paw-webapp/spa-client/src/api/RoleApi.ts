import api from "./api";
import Role from "./types/Role";

const RoleApi = (() => {
  const endpoint = "/roles";

  interface Params {
    auditionId?: number;
    userId?: number;
  }

  const getRoles = (params: Params = {}) => {
    return api
      .get(endpoint, {
        params: {
          audition: params.auditionId,
          user: params.userId,
        },
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

  const getRoleById = (id: number) => {
    return api.get(`${endpoint}/${id}`).then((response) => {
        const data = response.data;
        const role: Role = {
            id: data.id,
            roleName: data.roleName,
            self: data.self,
        };
    
        return Promise.resolve(role);
    });
  };

  return {
    getRoles,
    getRoleById,
  };
})();

export default RoleApi;
