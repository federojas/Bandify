import { createContext, useContext } from "react";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import UserServiceTest from "../services/UserServiceTest";
import UserApiTest from "../api/UserApiTest";

const UserServiceContext = createContext<UserServiceTest>(null!);

export const useUserService = () => useContext(UserServiceContext);

export const UserServiceProvider = ( { children }: { children: React.ReactNode }) => {
    const axiosPrivate = useAxiosPrivate();

    const userApi = new UserApiTest(axiosPrivate);
    const userService = new UserServiceTest(userApi);

    return (
        <UserServiceContext.Provider value={userService} >
            {children}
        </UserServiceContext.Provider>
    )
}