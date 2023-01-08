import { useState } from "react";
import React from "react";
import jwtDecode, { JwtPayload } from "jwt-decode";
import api from "../api/api";
import UserModel from "../types/UserModel";
import { loginService } from "../services";

type CustomJwtPayload = JwtPayload & { roles: string; userUrl: string };

export interface AuthContextValue {
  isAuthenticated: boolean;
  logout: () => void;
  login: (username: string, password: string) => Promise<void>;
  jwt?: string | undefined;
  email?: string | undefined;
  role?: string | undefined;
  userId?: number | undefined;
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
  const token = jwtInLocalStorage
    ? (localStorage.getItem("jwt") as string)
    : (sessionStorage.getItem("jwt") as string);
  const [jwt, setJwt] = useState<string | undefined>(token);
  const [email, setEmail] = useState<string | undefined>();
  //   jwtDecode<CustomJwtPayload>(token).sub
  // );
  const [role, setRole] = useState<string | undefined>()
  //   jwtDecode<CustomJwtPayload>(token).roles
  // );
  // const id = jwtDecode<CustomJwtPayload>(token).userUrl.split("/").pop();
  const [userId, setUserId] = useState<number | undefined>()
  // () => {
  //   if (id !== undefined) {
  //     return parseInt(id);
  //   }
  // });

  // if (isAuthenticated) {
  //   const token = jwtInLocalStorage
  //     ? (localStorage.getItem("jwt") as string)
  //     : (sessionStorage.getItem("jwt") as string);
  //   api.defaults.headers.common["Authorization"] = `Bearer ${token}`;
  //   setJwt(token);
  // setEmail(jwtDecode<CustomJwtPayload>(token).sub);
  // setRole(jwtDecode<CustomJwtPayload>(token).roles);
  // const id = jwtDecode<CustomJwtPayload>(token)
  //   .userUrl.split("/")
  //   .pop();
  // if (id) setUserId(parseInt(id));
  // }

  const login = async (username: string, password: string) => {
    try {
      const res = await loginService.login(username, password);
      if (res && res["x-jwt"]) {
        setJwt(res["x-jwt"]);
        setIsAuthenticated(true);
        setEmail(jwtDecode<CustomJwtPayload>(res["x-jwt"]).sub);
        setRole(jwtDecode<CustomJwtPayload>(res["x-jwt"]).roles);
        const id = jwtDecode<CustomJwtPayload>(res["x-jwt"])
          .userUrl.split("/")
          .pop();
        if (id) setUserId(parseInt(id));
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
    setEmail(undefined);
    setRole(undefined);
    setUserId(undefined);
  };

  return (
    <AuthContext.Provider
      value={{ isAuthenticated, logout, login, jwt, email, role, userId }}
    >
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
