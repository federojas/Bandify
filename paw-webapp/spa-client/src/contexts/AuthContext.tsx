import { useState } from "react";
import React from "react";
import jwtDecode, { JwtPayload } from "jwt-decode";
import api from "../api/api";
import UserModel from "../types/UserModel";
import { loginService } from "../services";

type CustomJwtPayload = JwtPayload;

export interface AuthContextValue {
  isAuthenticated: boolean;
  logout: () => void;
  login: (username: string, password: string) => Promise<void>;
  jwt?: string;
  username?: string;
  role?: string;
}

const AuthContext = React.createContext<AuthContextValue>({
  isAuthenticated: false,
  logout: () => {},
  login: async () => {},
});

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
  const jwtInLocalStorage = localStorage.hasOwnProperty("jwt");
  const jwtInSessionStorage = sessionStorage.hasOwnProperty("jwt");
  const [isAuthenticated, setIsAuthenticated] = useState<boolean>(
    jwtInLocalStorage || jwtInSessionStorage
  );
  const [jwt, setJwt] = useState<string>();

  const login = async (username: string, password: string) => {
    try {
      const res = await loginService.login(username, password);
      if (res) {
        setJwt(res["x-jwt"]);
        setIsAuthenticated(true);
        api.defaults.headers.common["Authorization"] = `Bearer ${jwt}`;
      }
    } catch (error) {
      console.log(error);
    }
  };

  const logout = () => {
    localStorage.removeItem("jwt");
    sessionStorage.removeItem("jwt");
    setIsAuthenticated(false);
    setJwt(undefined);
  };

  return (
    <AuthContext.Provider value={{ isAuthenticated, logout, login, jwt }}>
      {children}
    </AuthContext.Provider>
  );

  // const isInLocalStorage = localStorage.hasOwnProperty("userJWT");
  // const [isLoggedIn, setLoggedIn] = useState(isInLocalStorage || sessionStorage.hasOwnProperty("userJWT"));
  // const token = isInLocalStorage ? (localStorage.getItem("userJWT") as string) : (sessionStorage.getItem("userJWT") as string)
  // const [authKey, setAuthKey] = useState(token);
  // api.defaults.headers.common['Authorization'] = `Bearer ${(localStorage.getItem("userJWT") as string) ||(sessionStorage.getItem("userJWT") as string) || ''}`;
  // const [username, setUsername] = useState<string>(() => {
  //     try {
  //         return jwtDecode<CustomJwtPayload>(token).sub;
  //     } catch (error) {
  //         if (isLoggedIn) {
  //             console.log(error);
  //         }
  //     }
  // });
  // // const [role, setRole] = useState(() => {
  // //     try {
  // //         return jwtDecode<CustomJwtPayload>(token).roles;
  // //     } catch (error) {
  // //         if (isLoggedIn) {
  // //             console.log(error);
  // //         }
  // //     }
  // // });
  // const logoutHandler = () => {
  //     localStorage.removeItem("userJWT");
  //     sessionStorage.removeItem("userJWT");
  //     setLoggedIn(false);
  //     setAuthKey('');
  //     // setUsername('');
  //     // setRole('');
  // }
  // const loginHandler = (authKey: string, username: string) => {
  //     console.log("🚀 ~ ENTRE ACA file: AuthContext.tsx:81 ~ loginHandler ~ authKey", authKey)
  //     setAuthKey(authKey);
  //     // setUsername(username);
  //     // setRole(jwtDecode<CustomJwtPayload>(authKey).authorization)
  //     setLoggedIn(true);
  //     api.defaults.headers.common['Authorization'] = `Bearer ${authKey}`;
  // }
  // return <AuthContext.Provider
  //     value={{
  //         isLoggedIn: isLoggedIn,
  //         onLogout: logoutHandler,
  //         onLogin: loginHandler,
  //         authKey: 'authKey',
  //         username: 'username',
  //     }}>{children}</AuthContext.Provider>
};

export default AuthContext;
