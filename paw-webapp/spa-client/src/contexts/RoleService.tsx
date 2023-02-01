import { createContext, useContext } from "react";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import RoleServiceTest from "../services/RoleServiceTest";
import RoleApiTest from "../api/RoleApiTest";

const RoleServiceContext = createContext<RoleServiceTest>(null!);

export const useRoleService = () => useContext(RoleServiceContext);

export const RoleServiceProvider = ( { children }: { children: React.ReactNode }) => {
    const axiosPrivate = useAxiosPrivate();

    const roleApi = new RoleApiTest(axiosPrivate);
    const roleService = new RoleServiceTest(roleApi);

    return (
        <RoleServiceContext.Provider value={roleService} >
            {children}
        </RoleServiceContext.Provider>
    )
}