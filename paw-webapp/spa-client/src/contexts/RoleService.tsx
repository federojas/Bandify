import { createContext, useContext } from "react";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import RoleService from "../services/RoleService";
import RoleApiTest from "../api/RoleApi";

const RoleServiceContext = createContext<RoleService>(null!);

export const useRoleService = () => useContext(RoleServiceContext);

export const RoleServiceProvider = ( { children }: { children: React.ReactNode }) => {
    const axiosPrivate = useAxiosPrivate();

    const roleApi = new RoleApiTest(axiosPrivate);
    const roleService = new RoleService(roleApi);

    return (
        <RoleServiceContext.Provider value={roleService} >
            {children}
        </RoleServiceContext.Provider>
    )
}