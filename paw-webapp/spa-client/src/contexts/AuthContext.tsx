import {useState} from "react";
import React from "react";
import jwtDecode, {JwtPayload} from "jwt-decode";
import api from "../api/api";
import UserModel from "../types/UserModel";

type CustomJwtPayload = JwtPayload;

interface AuthContextType {
    isLoggedIn: boolean;
    onLogout: (callback: VoidFunction) => void;
    onLogin: (
        authKey: string,
        username: string | undefined,
    ) => void;
    authKey: string;
    username: string;
}

const AuthContext = React.createContext<AuthContextType>(null!);

export const AuthContextProvider = ({children} : {children: React.ReactNode}) => {
    const isInLocalStorage = localStorage.hasOwnProperty("userJWT");
    const [isLoggedIn, setLoggedIn] = useState(isInLocalStorage || sessionStorage.hasOwnProperty("userJWT"));
    const token = isInLocalStorage ? (localStorage.getItem("userJWT") as string) : (sessionStorage.getItem("userJWT") as string)
    const [authKey, setAuthKey] = useState(token);
    api.defaults.headers.common['Authorization'] = `Bearer ${(localStorage.getItem("userJWT") as string) ||(sessionStorage.getItem("userJWT") as string) || ''}`;


    const [username, setUsername] = useState<string>(() => {
        try {
            return jwtDecode<CustomJwtPayload>(token).sub;
        } catch (error) {
            if (isLoggedIn) {
                console.log(error);
            }
        }
    });

    // const [role, setRole] = useState(() => {
    //     try {
    //         return jwtDecode<CustomJwtPayload>(token).roles;
    //     } catch (error) {
    //         if (isLoggedIn) {
    //             console.log(error);
    //         }
    //     }
    // });


    const logoutHandler = () => {
        localStorage.removeItem("userJWT");
        sessionStorage.removeItem("userJWT");
        setLoggedIn(false);
        setAuthKey('');
        setUsername('');
        setRole('');
    }


    const loginHandler = (authKey: string, username: string) => {
        setAuthKey(authKey);
        // setUsername(username);
        // setRole(jwtDecode<CustomJwtPayload>(authKey).authorization)
        setLoggedIn(true);
        api.defaults.headers.common['Authorization'] = `Bearer ${authKey}`;
    }

    return <AuthContext.Provider
        value={{
            isLoggedIn: isLoggedIn,
            onLogout: logoutHandler,
            onLogin: loginHandler,
            authKey: authKey,
            username: username,
        }}>{children}</AuthContext.Provider>
}

export default AuthContext;