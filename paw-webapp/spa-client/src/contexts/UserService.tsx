import { createContext, useContext } from "react";
import useAxiosPrivate from "../hooks/useAxiosPrivate";
import UserService from "../services/UserService";
import UserApiTest from "../api/UserApiTest";

const UserServiceContext = createContext<UserService>(null!);

export const useUserService = () => useContext(UserServiceContext);

export const UserServiceProvider = ( { children }: { children: React.ReactNode }) => {
    const axiosPrivate = useAxiosPrivate();

    const userApi = new UserApiTest(axiosPrivate);
    const userService = new UserService(userApi);

    return (
        <UserServiceContext.Provider value={userService} >
            {children}
        </UserServiceContext.Provider>
    )
}