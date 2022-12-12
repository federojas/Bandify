import api from './api';

const RoleApi = (() => {
    const endpoint = '/roles';

    interface Params {
        auditionId?: number;
        userId?: number;
    }

    const getRoles = (params : Params | undefined) => {
        if (!params) {
            return api.get(endpoint);
        }

        return api.get(endpoint, {params: {
            audition: params.auditionId, 
            user: params.userId
        }});
    }

    const getRoleById = (id: number) => {
        return api.get(`${endpoint}/${id}`);
    }

    return {
        getRoles,
        getRoleById
    }
}
)();

export default RoleApi;